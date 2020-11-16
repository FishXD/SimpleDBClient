package network;

import simpledb.jdbc.network.NetworkDataSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.*;
import java.util.Scanner;
/*
 *    想要创建数据库首先需要运行Derby Server
 *     参数 ：
 *        主类： org.apache.derby.drda.NetworkServerControl、
 *        程序参数： start -h localhost
 *        工作目录： D:\CodeApps\ideaCodeRepositories\DerbyProject
 *
 * */
/*
 * 		1. 查询student表的所有记录（完成）
 * 		2.制作一个菜单，将查询功能封装（完成）
 * 		3.此处可以动态获取查询到的字符串的最长长度，从而完美打印表格(未完成)
 * 		4.需要把amy的majorid从30改成university database上的20(未完成)
 * */

/**
 * checkAllTable Class
 *
 * @author sanling
 * @date 2020/11/7
 */
public class CheckAllTables {
    /**
     * 由SimpleDBClients.src.network.InsertDataFromFIleToDB的控制台输入来判断是否调用
     * 此构造方法原理为将标准输出流指向文件名，实现将表格数据输出到文件的目的
     * @param tableName 表名
     * @param destinationFileName   目的文件名
     */
    public CheckAllTables(String tableName, String destinationFileName) {
        File file;
        // 标准输出流不再指向控制台，指向“destinationFileName”文件
        try {
            PrintStream fs = new PrintStream(new FileOutputStream(destinationFileName, true));
            System.setOut(fs);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        checkTable(tableName);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Enter a tableName name: ");
            String tableName = sc.next();
            if ("exit".equals(tableName)) {
                System.out.println("good bye!");
                break;
            } else {
                checkTable(tableName);
            }
        }
        sc.close();
    }

    private static void checkTable(String tableName) {
        // TODO Auto-generated method stub
        // PreparedStatement为占位符?的两边自动加上引号' '，这样会使得SQL语句不可执行
        // PreparedStatement只能用来为可以加引号’的参数（如参数值）设置动态参数，
        // 即用?占位，不可用于表名、字段名等。
        String sql = null;
        if ("STUDENT".equals(tableName)) {
            sql = "select SId, SName, MajorId, GradYear from STUDENT";
        } else if ("DEPT".equals(tableName)) {
            sql = "select DId, DName from DEPT ";
        } else if ("COURSE".equals(tableName)) {
            sql = "select CId, Title, DeptId from COURSE";
        } else if ("SECTION".equals(tableName)) {
            sql = "select SectId, CourseId, Prof, YearOffered from SECTION";
        } else if ("ENROLL".equals(tableName)) {
            sql = "select EId, StudentId, SectionId, Grade from ENROLL";
        } else {
            System.out.println("输入的表名有误!");
        }

        NetworkDataSource netds = new NetworkDataSource();

        try (Connection conn = netds.getConnection("localhost", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {



            // System.out.println("Here are the " + tableName + " all of record");
            // 此处不能使用tableName == "student"来比较
            // 字符串的值比较需要使用tableName.equals("student")
            // 知识点： String不是基本数据类型，而是一个类
            // String存储在常量池
            if ("STUDENT".equals(tableName)) {
                checkStudent(rs);

            } else if ("DEPT".equals(tableName)) {
                checkDept(rs);

            } else if ("COURSE".equals(tableName)) {
                checkCourse(rs);

            } else if ("ENROLL".equals(tableName)) {
                checkEnroll(rs);

            } else if ("SECTION".equals(tableName)) {
                checkSection(rs);

            } else {
                System.out.println("nothing run");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void checkSection(ResultSet rs) {
        try {
            System.out.println("SectId\tCourseId\tProf\tYearOffered");
            while (rs.next()) {
                int sectid = rs.getInt("sectid");

                int courseid = rs.getInt("courseid");
                String prof = rs.getString("prof");
                String yearoffered = rs.getString("yearoffered");
                // 此处43 32 einstein 2017出现错位
                System.out.println(sectid + "\t" + courseid + "\t\t" + prof + "\t" + yearoffered);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void checkEnroll(ResultSet rs) {
        try {
            System.out.println("EId\tStudentId\tSectionId\tGrade");
            while (rs.next()) {
                int eid = rs.getInt("eid");
                int studentid = rs.getInt("studentid");
                int sectionid = rs.getInt("sectionid");
                String grade = rs.getString("grade");
                System.out.println(eid + "\t" + studentid + "\t\t" + sectionid + "\t\t" + grade);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void checkCourse(ResultSet rs) {
        try {
            System.out.println("CId\tTitle\t\tDeptId");
            while (rs.next()) {
                int cid = rs.getInt("cid");
                String title = rs.getString("title");
                String deptid = rs.getString("deptid");
                // 此处打印42 algebra 20出现错位
                // 52 acting 30
                System.out.println(cid + "\t" + title + "\t\t" + deptid);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void checkDept(ResultSet rs) {
        try {
            System.out.println("DId\tDName");
            while (rs.next()) {
                int did = rs.getInt("did");
                String dname = rs.getString("dname");
                System.out.println(did + "\t" + dname);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void checkStudent(ResultSet rs) {
        try {
            System.out.println("SId\tSName\tGradYear\tMajorId");

            while (rs.next()) {
                int sid = rs.getInt("sid");
                System.out.println(rs.wasNull());
                String sname = rs.getString("sname");
                int gradyear = rs.getInt("gradyear");
                int majorid = rs.getInt("majorid");
                System.out.println(sid + "\t" + sname + "\t" + gradyear + "\t\t" + majorid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO Auto-generated method stub

    }
}

