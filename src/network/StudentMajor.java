package network;

import simpledb.jdbc.network.NetworkDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentMajor {
    public static void main(String[] args) {
        NetworkDataSource netds = new NetworkDataSource();
        String qry = "select SName, DName, GradYear "
                + "from DEPT, STUDENT "
                + "where MajorId = DId";

//      String url = "jdbc:simpledb://localhost";
        try (Connection conn = netds.getConnection("localhost", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(qry)) {

            System.out.println("GradYear\tName\tMajor");
            while (rs.next()) {


//                String sname = rs.getString("SName");
               System.out.println(rs.getString(1));
//                String dname = rs.getString("DName");
//                System.out.println(sname + "\t" + dname);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

