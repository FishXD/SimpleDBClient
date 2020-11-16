package network;

import java.util.Scanner;

/**
 * 调用CheckAllTables类，使用其构造函数来实现改变标准输出流到文件
 * @author sanling
 * @date 2020/11/14
 */
public class WriteDBDataToFile {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your tableName(please use capital) and destination file:");
        String tableName = sc.next();
        String destinationFileName = sc.next();

        sc.close();
        // 调用CheckAllTables类，使用其构造函数来实现改变标准输出流到文件
        CheckAllTables checkAllTable = new CheckAllTables(tableName, destinationFileName);
    }

}
