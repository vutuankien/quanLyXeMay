package org.example.quanlyxemay;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectDB {
    public static Connection connectionDB() {
        Connection conn = null;
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection
            String url = "jdbc:mysql://localhost:3306/mydatabase";
            String username = "root";
            String password = "kien392004";

            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return conn;

    }
}
