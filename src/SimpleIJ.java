import org.apache.derby.jdbc.ClientDataSource;
import org.apache.derby.jdbc.ClientDriver;
import org.jetbrains.annotations.NotNull;
import simpledb.jdbc.embedded.EmbeddedDriver;
import simpledb.jdbc.network.NetworkDriver;

import java.sql.*;
import java.util.Scanner;

/*
  此类如果本地连接就不需要服务器运行，但idea会更改默认的运行配置
  正确的运行配置:
  主要类：SimpleIJ
  工作目录：D:\CodeApps\ideaCodeRepositories\SimpleDBClients
  使用模块的类路径：SimpleDBClients
  <p>
  之后输入  jdbc:simpledb:studentdb  可进行连接
  <p>
  如果使用服务器那么就需要SimpleDB Server先运行
  正确的运行配置：
  主要类：simpledb.server.StartServer
  工作目录：D:\CodeApps\ideaCodeRepositories\SimpleDBEngine
  使用模块的类路径：SimpleDBEngine
  <p>
  之后输入  jdbc:simpledb://localhost  可进行连接
  <p>
  <p>
  命令解释器(前端)
  <p>
  <p>
  由于使用了“ResultSet”和“ResultSetMetaData”方法，
  所以在概念上比较困难的任务是很容易编码的，
  而将数据对齐的琐碎任务则占了大部分的编码工作。
  <p>
  解释的是sql语句，返回的是查询出的内容或者是update影响了多少条记录
  <p>
  1. 选择连接方式
  1. 录入一行输入字符串
  2. 是否含有"//"，有就是网络连接，没有就是本地连接
  2. 通过已经连接的网络/本地驱动来输入语句
  1. 用到Connection对象和Statement对象
  1. Connection对象获取到查询的格式
  2. Statement对象负责执行sql语句
  3. 他们都在try后面的（）中，可以自动关闭
  2. 使用while (sc.hasNextLine())来不断接收命令
  1. 使用String cmd = sc.nextLine().trim()去除前后空格
  2. 使用if（cmd.startsWith("****")）来判断执行的命令
  1. exit  break
  2. select  doQuery(stmt, cmd);主要任务是为输出的值确定适当的间距
  3. 其他 默认执行doUpdate(stmt, cmd);如果不是正确的语句也没关系，会被catch捕获
  3. 如果遇到错误，该错误会被catch捕获
  4. 关闭字符输入流
  <p>
  命令解释器(前端)
  <p>
  <p>
  由于使用了“ResultSet”和“ResultSetMetaData”方法，
  所以在概念上比较困难的任务是很容易编码的，
  而将数据对齐的琐碎任务则占了大部分的编码工作。
  <p>
  解释的是sql语句，返回的是查询出的内容或者是update影响了多少条记录
  <p>
  1. 选择连接方式
  1. 录入一行输入字符串
  2. 是否含有"//"，有就是网络连接，没有就是本地连接
  2. 通过已经连接的网络/本地驱动来输入语句
  1. 用到Connection对象和Statement对象
  1. Connection对象获取到查询的格式
  2. Statement对象负责执行sql语句
  3. 他们都在try后面的（）中，可以自动关闭
  2. 使用while (sc.hasNextLine())来不断接收命令
  1. 使用String cmd = sc.nextLine().trim()去除前后空格
  2. 使用if（cmd.startsWith("****")）来判断执行的命令
  1. exit  break
  2. select  doQuery(stmt, cmd);主要任务是为输出的值确定适当的间距
  3. 其他 默认执行doUpdate(stmt, cmd);如果不是正确的语句也没关系，会被catch捕获
  3. 如果遇到错误，该错误会被catch捕获
  4. 关闭字符输入流
 */

/*
  	命令解释器(前端)


  	由于使用了“ResultSet”和“ResultSetMetaData”方法，
  	所以在概念上比较困难的任务是很容易编码的，
  	而将数据对齐的琐碎任务则占了大部分的编码工作。

  	解释的是sql语句，返回的是查询出的内容或者是update影响了多少条记录

 1. 选择连接方式
 1. 录入一行输入字符串
 2. 是否含有"//"，有就是网络连接，没有就是本地连接
 2. 通过已经连接的网络/本地驱动来输入语句
 1. 用到Connection对象和Statement对象
 1. Connection对象获取到查询的格式
 2. Statement对象负责执行sql语句
 3. 他们都在try后面的（）中，可以自动关闭
 2. 使用while (sc.hasNextLine())来不断接收命令
 1. 使用String cmd = sc.nextLine().trim()去除前后空格
 2. 使用if（cmd.startsWith("****")）来判断执行的命令
 1. exit  break
 2. select  doQuery(stmt, cmd);主要任务是为输出的值确定适当的间距
 3. 其他 默认执行doUpdate(stmt, cmd);如果不是正确的语句也没关系，会被catch捕获
 3. 如果遇到错误，该错误会被catch捕获
 4. 关闭字符输入流

  */

/**
 * @author bookAuthor
 * @date 2020/11/7
 * @updateAuthor sanling
 * @update 2020/11/15
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class SimpleIJ {
    public static void main(String[] args) {
        ClientDataSource clientDS = new ClientDataSource();
        clientDS.setServerName("localhost");
        clientDS.setDatabaseName("studentdb");

        Scanner sc = new Scanner(System.in);

        try ( Connection conn = clientDS.getConnection();
              Statement stmt = conn.createStatement()) {
            System.out.print("\nSQL> ");
            String cmd = "";
            // Scanner-> boolean hasNextLine()如果扫描仪的输入中有下一行，则返回true。
            while (sc.hasNextLine()) {
                // process one line of input
                // Scanner-> String nextLine()将此扫描仪推进到当前行并返回当前这一行的输入
                // String-> boolean trim()返回一个字符串，其值为此字符串，并删除任何前导和尾随空格。
                // .replace("\n","")
                cmd += sc.next().trim() + " ";

                if (cmd.startsWith("exit")) {
                    break;
                }
                if (cmd.contains(";")) {
                    cmd = cmd.substring(0, cmd.indexOf(";"));

                    if (cmd.startsWith("select")) {
                        // System.out.println("这是将要执行的sql语句:" + cmd + "\n");
                        doQuery(stmt, cmd);
                    } else {
                        doUpdate(stmt, cmd);
                    }
                    cmd = "";
                    System.out.print("\nSQL> ");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 关闭输入流
        sc.close();
    }

    /**
     * 执行输出语句，通过打印错误消息并返回来捕获异常
     * 这个错误处理策略允许主循环继续接受语句(因为错误已经被处理了，返回的是一个String)，直到用户输入“exit”命令
     * 主要任务是为输出的值确定适当的间距
     * @param stmt
     * @param cmd
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
                        if (rs.wasNull()) {
                            System.out.format(fmt + "s", "NULL");
                        }
                        System.out.format(fmt + "d", ival);
                    } else {
                        String sval = rs.getString(fldname);
                        if (rs.wasNull()) {
                            System.out.format(fmt + "s", "NULL");
                        }
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