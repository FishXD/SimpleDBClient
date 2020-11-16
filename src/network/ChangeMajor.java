package network;

import simpledb.jdbc.network.NetworkDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ChangeMajor {
    public static void main(String[] args) {
        NetworkDataSource netds = new NetworkDataSource();
//      String url = "jdbc:simpledb://localhost";

        try (Connection conn = netds.getConnection("localhost", "");
             Statement stmt = conn.createStatement()) {
            String cmd = "update STUDENT set MajorId=30 "
                    + "where SName = 'amy'";
            stmt.executeUpdate(cmd);
            System.out.println("Amy is now a drama major.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
