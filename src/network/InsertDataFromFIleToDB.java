package network;

import simpledb.jdbc.network.NetworkDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * @author sanling
 * @date 2020/11/13
 */
public class InsertDataFromFIleToDB {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter FileName and TableName(please use capital):");
        // 输入文件名
        String fileName = sc.next();
        // 输入文件中的表名
        String tableName = sc.next();
        sc.close();
        NetworkDataSource netDS = new NetworkDataSource();
        try (Connection conn = netDS.getConnection("localhost", "");
             Statement stmt = conn.createStatement()) {
            insertDataToDB(fileName, tableName, stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertDataToDB(String fileName, String tableName, Statement stmt) {

        FileReader reader;
        BufferedReader br = null;
        try {
            reader = new FileReader(fileName);
            br = new BufferedReader(reader);

            // 此处必须读取一次,将文件表头读取
            String fieldName = br.readLine();

            // sql执行语句必须是一个String变量
            // "insert into STUDENT(SId, SName, MajorId, GradYear) values (1, 'joe', 10, 2021)"
            // cmd = sql + datas
            String cmd = "";
            String sql = "";
            String datas = "";

            if ("STUDENT".equals(tableName)) {
                sql = "insert into STUDENT(SId, SName, MajorId, GradYear) values ";
            } else if ("DEPT".equals(tableName)) {
                sql = "insert into DEPT(DId, DName) values ";
            } else if ("COURSE".equals(tableName)) {
                sql = "insert into COURSE(CId, Title, DeptId) values ";
            } else if ("SECTION".equals(tableName)) {
                sql = "insert into SECTION(SectId, CourseId, Prof, YearOffered) values ";
            } else if ("ENROLL".equals(tableName)) {
                sql = "insert into ENROLL(EId, StudentId, SectionId, Grade) values ";
            } else {
                System.out.println("输入的表名有误!");
            }

            // 读一行执行一行
            readAndExcute(cmd, sql, datas, br, stmt);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * @param cmd   cmd = sql + datas;
     * @param sql   需要从控制台输入
     * @param datas 需要从文件中读取并格式化为可执行的样式
     * @param br    BufferedReader输入缓冲流对象，整行读必须使用
     * @param stmt  执行sql需要此Statement对象
     * @throws IOException 异常处理，处理的是br.readLine()
     */
    private static void readAndExcute(String cmd, String sql, String datas, BufferedReader br, Statement stmt) throws IOException {
        while ((datas = br.readLine()) != null) {
            datas = datas.replaceAll("\t", ",");
            int startIndex = 0;
            for (int i = 0; i < datas.length(); i++) {
                // 找到第一个出现的字母的下标
                if (datas.charAt(i) >= 'a' && datas.charAt(i) <= 'z') {
                    startIndex = i;
                    //System.out.println("字母字段开始位置startIndex:" + startIndex);
                    break;
                }
            }
            int endIndex = datas.indexOf(",", startIndex);
            //System.out.println("字母字段结束位置endIndex:" + endIndex);
            // 如果是最后一个属性字段
            if (endIndex == -1) {
                // 这里可以将datas变为StringStringBuffer,使用append方法连接更加专业,但是为了可读性就不做改动
                datas = datas.substring(0, startIndex) + "'"
                        + datas.substring(startIndex) + "'";
            } else {
                // 如果在string字段后还有其他字段
                datas = datas.substring(0, startIndex) + "'"
                        + datas.substring(startIndex, endIndex)
                        + "'" + datas.substring(endIndex);
            }

            cmd = sql + "(" + datas + ");";

            System.out.println("正在执行:" + cmd);
            try {
                stmt.executeUpdate(cmd);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("执行完毕！");
    }
}
