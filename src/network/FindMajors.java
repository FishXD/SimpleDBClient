package network;

import simpledb.jdbc.network.NetworkDataSource;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Vector;

/**
 * @author sanling
 * @date 2020/11/12
 */
public class FindMajors {
    public static void main(String[] args) {
        ModelDemo myModel = new ModelDemo();
        JTable table = new JTable(myModel);
        JScrollPane scrollpane = new JScrollPane(table);
        JFrame jf = new JFrame();
        JScrollPane jsp = new JScrollPane(table);
        jf.add(jsp);
        jf.setBounds(100, 100, 500, 500);
        jf.setVisible(true);
    }
}

class ModelDemo extends AbstractTableModel {
    /**
     * 用来存放表格数据的线性表
     */
    public Vector tableData;

    /**
     * 表格的 列标题
     */
    public Vector tableTitle;

    /**
     * 注意构造函数是第一个执行的，用于初始化 tableData，tableTitle
     */
    public ModelDemo() {
        //先new 一下
        tableData = new Vector();
        tableTitle = new Vector();

        //这里我们假设当前的表格是 3x3的
        //先初始化 列标题,有3列
        tableTitle.add("sname");
        tableTitle.add("gradyear");

        System.out.print("Enter a department name: ");
        Scanner sc = new Scanner(System.in);
        String major = sc.next();
        sc.close();

        String qry = "select sname, gradyear "
                + "from student, dept "
                + "where did = majorid "
                + "and dname = '" + major + "'";
        NetworkDataSource netds = new NetworkDataSource();
        try (Connection conn = netds.getConnection("localhost", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(qry)) {
            while (rs.next()) {
                String[] lines = {rs.getString("sname"), String.valueOf(rs.getInt("gradyear"))};
                tableData.add(lines);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        //这里是告知表格应该有多少行，我们返回tableData上挂的String数组个数
        return tableData.size();
    }

    @Override
    public int getColumnCount() {
        //告知列数，用标题数组的大小,这样表格就是tableData.size()行，tableTitle.size()列了
        return tableTitle.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        //获取了表格的大小，当然还要获取数据，根据坐标直接返回对应的数据
        //小心 都是从 0开始的，小心下标越界 的问题
        //我们之前是将 String[]挂到了线性表上，所以要先获取到String[]
        //
        //获取每一行对应的String[]数组
        String[] lineTemp = (String[]) this.tableData.get(rowIndex);
        //提取出对 应的数据
        return lineTemp[columnIndex];

        //如果我们是将 线性表Vector挂到了Vector上要注意每次我们获取的是 一行线性表
        //例如
        //return ((Vector)tableData.get(rowIndex)).get(columnIndex);
    }

    @Override
    public String getColumnName(int columnIndex) {
        String[] columnName = {"Sname", "GradYear"};
        return columnName[columnIndex];
    }

}