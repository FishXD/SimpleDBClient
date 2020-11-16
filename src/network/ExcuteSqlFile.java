package network;

import org.jetbrains.annotations.NotNull;
import simpledb.jdbc.network.NetworkDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

/**
 * @author sanling
 * @date 2020/11/12
 */
public class ExcuteSqlFile {

    public static void main(String[] args) {
        NetworkDataSource netds = new NetworkDataSource();
        try (Connection conn = netds.getConnection("localhost", null);
             Statement stmt = conn.createStatement()) {
            doSqlForFile(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取默认的文件sqlFile.txt
     */
    public static void doSqlForFile(Statement stmt) {
        FileReader reader;
        BufferedReader br = null;
        try {
            reader = new FileReader("sqlFile.txt");
            br = new BufferedReader(reader);

            String cmd;
            while ((cmd = br.readLine()) != null) {
                System.out.println("正在执行:" + cmd);
                if (cmd.contains(";")) {
                    cmd = cmd.substring(0, cmd.indexOf(";"));

                    if (cmd.startsWith("select")) {
                        doQuery(stmt, cmd);
                    } else {
                        doUpdate(stmt, cmd);
                    }
                    System.out.println("--------------------------------------");
                    System.out.print("\nSQL> ");
                }
            }
            System.out.println("执行完毕！");
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
     * 执行输出语句，通过打印错误消息并返回来捕获异常
     * 这个错误处理策略允许主循环继续接受语句(因为错误已经被处理了，返回的是一个String)，直到用户输入“exit”命令
     * 主要任务是为输出的值确定适当的间距
     */
    private static void doQuery(Statement stmt, String cmd) {
        try (ResultSet rs = stmt.executeQuery(cmd)) {
            ResultSetMetaData md = rs.getMetaData();
            int numcols = md.getColumnCount();
            int totalwidth = 0;

            // print header
            // 此处的下标是从1开始
            for (int i = 1; i <= numcols; i++) {
                String fldname = md.getColumnName(i);

                // 调用getColumnDisplaySize返回每个字段的空间需求
                // 代码使用这些数字构造一个格式字符串，该字符串允许字段值正确对齐。
                int width = md.getColumnDisplaySize(i);
                totalwidth += width;
                String fmt = "%" + width + "s";

                // 使用指定的格式控制字符串fmt和参数将格式化的字符串fldname写入此输出流。
                // 这里的"%widths"表示string, 又对齐, 输出宽度为width
                System.out.format(fmt, fldname);
            }
            System.out.println();
            // 打印了head，现在打印head和records的分界线
            for (int i = 0; i < totalwidth; i++) {
                System.out.print("-");
            }
            System.out.println();

            // print records
            while (rs.next()) {
                for (int i = 1; i <= numcols; i++) {
                    String fldname = md.getColumnName(i);

                    // 这里与head有不同
                    // 这里需要确定打印的值的类型(是int还是string)
                    int fldtype = md.getColumnType(i);
                    // 这里与head不同
                    // 先加了"%"是因为后面打印时需要的格式字符串不同
                    String fmt = "%" + md.getColumnDisplaySize(i);
                    if (fldtype == Types.INTEGER) {
                        int ival = rs.getInt(fldname);
                        System.out.format(fmt + "d", ival);
                    } else {
                        String sval = rs.getString(fldname);
                        System.out.format(fmt + "s", sval);
                    }
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    private static void doUpdate(@NotNull Statement stmt, String cmd) {
        try {
            int howmany = stmt.executeUpdate(cmd);
            System.out.println(howmany + " records processed");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }
}

