package com.celestialdental.ui;

import com.celestialdental.database.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginForm {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login - Celestial Dental");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout()); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); 

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 20)); 
        JTextField userField = new JTextField(20); 
        userField.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JPasswordField passField = new JPasswordField(20);
        passField.setFont(new Font("Arial", Font.PLAIN, 18));

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));
        loginButton.setPreferredSize(new Dimension(200, 50)); 

        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(userLabel, gbc);

        gbc.gridx = 1;
        frame.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(passLabel, gbc);

        gbc.gridx = 1;
        frame.add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; 
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(loginButton, gbc);

        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            String role = authenticateUser(username, password);

            if (role != null) {
                JOptionPane.showMessageDialog(frame, "Login successful.");
                frame.dispose();
                
                switch (role) {
                    case "admin" -> {
                        Dashboard dashboard = new Dashboard(username);
                        dashboard.setVisible(true);
                    }
                    case "dentist" -> JOptionPane.showMessageDialog(frame, "Dentist Dashboard");
                    case "customer service representative" -> JOptionPane.showMessageDialog(frame, "Customer Service Rep Dashboard");
                    default -> JOptionPane.showMessageDialog(frame, "Unknown Role");
                }  
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private static String authenticateUser(String username, String password) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT role FROM users WHERE username=? AND password=?")) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                System.out.println("Fetched Role: " + role);  
                return role;
            } else {
                System.out.println("No matching user found.");
                return null;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}