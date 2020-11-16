package network;

import simpledb.jdbc.network.NetworkDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 这里的问题有两个：
 * 一个是前面的embedded的问题：
 * 运行此类之前需要让SimpleDBClients依赖于SimpleDBEngine
 * 并且工作目录必须是D:\CodeApps\ideaCodeRepositories\SimpleDBClients
 * idea默认目录工作目录是Derbyproject
 * 第二个问题是需要先启动SimpleDB Server
 * 启动的时候SimpleDB Server 的默认目录工作目录也还是Derbyproject
 * 需要改写成SimpleDBEngine
 * 之后才可以成功
 *
 * @author sanling
 * @date 2020/11/11
 */
public class CreateStudentDB {
    public static void main(String[] args) {
        NetworkDataSource netds = new NetworkDataSource();

//      Driver d = new NetworkDriver();
        //String url = "jdbc:simpledb://localhost";

        try (Connection conn = netds.getConnection("localhost", "");
             Statement stmt = conn.createStatement()) {
            String s = "create table STUDENT(SId int, SName varchar(10), MajorId int, GradYear int)";
            stmt.executeUpdate(s);
            System.out.println("Table STUDENT created.");

            s = "insert into STUDENT(SId, SName, MajorId, GradYear) values ";
            String[] studvals = {"(1, 'joe', 10, 2021)",
                    "(2, 'amy', 20, 2020)",
                    "(3, 'max', 10, 2022)",
                    "(4, 'sue', 20, 2022)",
                    "(5, 'bob', 30, 2020)",
                    "(6, 'kim', 20, 2020)",
                    "(7, 'art', 30, 2021)",
                    "(8, 'pat', 20, 2019)",
                    "(9, 'lee', 10, 2021)"};
            for (int i = 0; i < studvals.length; i++) {
                stmt.executeUpdate(s + studvals[i]);
            }
            System.out.println("STUDENT records inserted.");

            s = "create table DEPT(DId int, DName varchar(8))";
            stmt.executeUpdate(s);
            System.out.println("Table DEPT created.");

            s = "insert into DEPT(DId, DName) values ";
            String[] deptvals = {"(10, 'compsci')",
                    "(20, 'math')",
                    "(30, 'drama')"};
            for (int i = 0; i < deptvals.length; i++) {
                stmt.executeUpdate(s + deptvals[i]);
            }
            System.out.println("DEPT records inserted.");

            s = "create table COURSE(CId int, Title varchar(20), DeptId int)";
            stmt.executeUpdate(s);
            System.out.println("Table COURSE created.");

            s = "insert into COURSE(CId, Title, DeptId) values ";
            String[] coursevals = {"(12, 'db systems', 10)",
                    "(22, 'compilers', 10)",
                    "(32, 'calculus', 20)",
                    "(42, 'algebra', 20)",
                    "(52, 'acting', 30)",
                    "(62, 'elocution', 30)"};
            for (int i = 0; i < coursevals.length; i++) {
                stmt.executeUpdate(s + coursevals[i]);
            }
            System.out.println("COURSE records inserted.");

            s = "create table SECTION(SectId int, CourseId int, Prof varchar(8), YearOffered int)";
            stmt.executeUpdate(s);
            System.out.println("Table SECTION created.");

            s = "insert into SECTION(SectId, CourseId, Prof, YearOffered) values ";
            String[] sectvals = {"(13, 12, 'turing', 2018)",
                    "(23, 12, 'turing', 2019)",
                    "(33, 32, 'newton', 2019)",
                    "(43, 32, 'einstein', 2017)",
                    "(53, 62, 'brando', 2018)"};
            for (int i = 0; i < sectvals.length; i++) {
                stmt.executeUpdate(s + sectvals[i]);
            }
            System.out.println("SECTION records inserted.");

            s = "create table ENROLL(EId int, StudentId int, SectionId int, Grade varchar(2))";
            stmt.executeUpdate(s);
            System.out.println("Table ENROLL created.");

            s = "insert into ENROLL(EId, StudentId, SectionId, Grade) values ";
            String[] enrollvals = {"(14, 1, 13, 'A')",
                    "(24, 1, 43, 'C' )",
                    "(34, 2, 43, 'B+')",
                    "(44, 4, 33, 'B' )",
                    "(54, 4, 53, 'A' )",
                    "(64, 6, 53, 'A' )"};
            for (int i = 0; i < enrollvals.length; i++) {
                stmt.executeUpdate(s + enrollvals[i]);
            }
            System.out.println("ENROLL records inserted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
