package com.celestialdental.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/celestial_dental?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Milmile099!";
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://petermwendePC:3306/celestial_dental?useSSL=false&serverTimezone=UTC", "root", "");
            System.out.println("Connected successfully.");
            con.close();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver Not Found." + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database connection error." + e.getMessage());
        }
       return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public static ResultSet getAppointment() throws SQLException {
            Connection conn = getConnection();
            String query = "SELECT * FROM appointments";
            PreparedStatement stmt = conn.prepareStatement(query);
            return stmt.executeQuery();
        }
    }
