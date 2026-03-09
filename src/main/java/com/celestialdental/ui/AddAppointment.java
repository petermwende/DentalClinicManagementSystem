package com.celestialdental.ui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.celestialdental.database.DatabaseConnection;

public class AddAppointment {
    private JFrame frame;
    private final JTextField txtPatientName, txtDate, txtTime;
    private final JComboBox<String> cmbStatus;
    private final JButton btnSave, btnCancel;

    public AddAppointment() {
        frame = new JFrame("New Appointment");
        frame.setLayout(new GridLayout(5, 2, 10, 10));

        frame.add(new JLabel("Patient Name:"));
        txtPatientName = new JTextField();
        frame.add(txtPatientName);

        frame.add(new JLabel("Appointment Date (YYYY-MM-DD):"));
        txtDate = new JTextField();
        frame.add(txtDate);

        frame.add(new JLabel("Appointment Time (HH:MM:SS):"));
        txtTime = new JTextField();
        frame.add(txtTime);

        frame.add(new JLabel("Status:"));
        cmbStatus = new JComboBox<>(new String[]{"Scheduled", "Completed", "Cancelled"});
        frame.add(cmbStatus);

        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");
        frame.add(btnSave);
        frame.add(btnCancel);

        btnSave.addActionListener(e -> saveAppointment());
        btnCancel.addActionListener(e -> frame.dispose());

        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private void saveAppointment() {
        String name = txtPatientName.getText();
        String date = txtDate.getText();
        String time = txtTime.getText();
        String status = (String) cmbStatus.getSelectedItem();

        if (name.isEmpty() || date.isEmpty() || time.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "INSERT INTO appointments (patient_name, appointment_date, appointment_time, status) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, date);
            pstmt.setString(3, time);
            pstmt.setString(4, status);
            pstmt.executeUpdate();
            
            JOptionPane.showMessageDialog(frame, "Appointment Saved Successfully!");
            frame.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error Saving Appointment!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new AddAppointment();
    }
}

