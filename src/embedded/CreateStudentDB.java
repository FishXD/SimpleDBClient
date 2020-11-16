package embedded;
import java.sql.*;

import simpledb.jdbc.embedded.EmbeddedDriver;
/**
 * 	运行此类之前需要让SimpleDBClients依赖于SimpleDBEngine
 *    并且工作目录必须是D:\CodeApps\ideaCodeRepositories\SimpleDBClients
 *    idea默认目录工作目录是Derbyproject
 *
 *
 *    创建并填充其它客户端类使用的学生数据库
 * 	因此它必须是第一个运行的客户端类
 *
 *
 * 	如果客户机要在与服务器不同的机器上运行，则必须修改其源代码
 * 	一边将本地主机替换为服务器的域名(或IP地址)
 *
 *
 * 	SimpleDB实现了sql和jdbc中的一个小子集，
 * 	并且为了简单起见施加了sql标准中没有的限制。
 *
 *
 * 	这些限制是：
 * 		SimpleDB中的查询仅由select-from-where子句组成
 * 		其中select子句包含字段名列表(没有as关键字)
 * 		from子句包含表名列表(没有范围变量)
 * 		where子句是可选的
 *
 * 		唯一的布尔运算符是and
 * 		唯一的比较运算符是equality
 *
 * 		与标准sql不同，没有其他比较运算符，布尔运算符，算术运算符，
 * 		内置函数，也没有括号
 * 		因此不支持嵌套查询，聚合和计算值
 *
 * 		可以创建视图，但是视图最多可以100个字符
 *
 * 		因为没有范围变量，也没有重命名，
 * 		所以查询中的所有字段名称都必须是分离的
 *
 * 		由于没有group by语句和order by语句
 * 		因此不支持分组和排序
 *
 * 	其他限制：
 * 		不支持select子句中的“*”缩写
 * 		没有空值null
 *
 * 		from子句中没有显式连接或外部连接
 *
 * 		不支持union和except关键字
 *
 * 		insert语句只接受显式值，不接受查询
 *
 * 		Update语句在set子句中只能有一个赋值
 *
 *
 *
 *
 *
 *
 * @author bookAuthor
 * @date xxx/xx/xx
 *
 * */
public class CreateStudentDB {
   public static void main(String[] args) {
      Driver d = new EmbeddedDriver();
      String url = "jdbc:simpledb:studentdb";

      try (Connection conn = d.connect(url, null);
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
         for (int i=0; i<studvals.length; i++) {
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
         for (int i=0; i<deptvals.length; i++) {
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
         for (int i=0; i<coursevals.length; i++) {
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
         for (int i=0; i<sectvals.length; i++) {
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
         for (int i=0; i<enrollvals.length; i++) {
            stmt.executeUpdate(s + enrollvals[i]);
         }
         System.out.println("ENROLL records inserted.");
      }
      catch(SQLException e) {
         e.printStackTrace();
      }
   }
}
