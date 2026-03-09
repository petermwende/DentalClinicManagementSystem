package com.celestialdental.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Dashboard extends JFrame {
    private String username;

    public Dashboard(String username) {
        this.username = username;

        setTitle("DASHBOARD - " + username.toUpperCase());
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(5, 1, 10, 10)); 
        sidebar.setBackground(new Color(70, 130, 180));
        sidebar.setPreferredSize(new Dimension(250, getHeight()));

        JButton btnPatients = createStyledButton("MANAGE PATIENTS", new Color(30, 144, 255));
        JButton btnAppointments = createStyledButton("APPOINTMENTS", new Color(30, 144, 255));
        JButton btnBilling = createStyledButton("BILLING", new Color(30, 144, 255));
        JButton btnReports = createStyledButton("REPORTS", new Color(30, 144, 255));
        JButton btnLogout = createStyledButton("LOGOUT", new Color(220, 20, 60)); 

        sidebar.add(btnPatients);
        sidebar.add(btnAppointments);
        sidebar.add(btnBilling);
        sidebar.add(btnReports);
        sidebar.add(btnLogout);

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("WELCOME, " + username.toUpperCase(), JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(30, 30, 30));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 2, 20, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        centerPanel.add(btnPatients);
        centerPanel.add(btnAppointments);
        centerPanel.add(btnBilling);
        centerPanel.add(btnReports);

        mainContent.add(welcomeLabel, BorderLayout.NORTH);
        mainContent.add(centerPanel, BorderLayout.CENTER);

        add(sidebar, BorderLayout.WEST);
        add(mainContent, BorderLayout.CENTER);

        setVisible(true);

        btnPatients.addActionListener(e -> new ManagePatients());
        btnAppointments.addActionListener(e -> new Appointments());
        btnBilling.addActionListener(e -> new Billing());
        btnReports.addActionListener(e -> new Reports());
        btnLogout.addActionListener(e -> {
            dispose();
            new LoginForm().main(null);
        });
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 20)); 
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker()); 
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor); 
            }
        });

        return button;
    }
}