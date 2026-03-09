package com.celestialdental.ui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.celestialdental.database.DatabaseConnection;

public class EditAppointment {
    private JFrame frame;
    private final JTextField txtPatientName, txtDate, txtTime;
    private final JComboBox<String> cmbStatus;
    private final JButton btnSave, btnCancel;
    private final int appointmentId;

    public EditAppointment(int id, String name, String date, String time, String status) {
        appointmentId = id;
        frame = new JFrame("Edit Appointment");
        frame.setLayout(new GridLayout(5, 2, 10, 10));

        frame.add(new JLabel("Patient Name:"));
        txtPatientName = new JTextField(name);
        frame.add(txtPatientName);

        frame.add(new JLabel("Appointment Date (YYYY-MM-DD):"));
        txtDate = new JTextField(date);
        frame.add(txtDate);

        frame.add(new JLabel("Appointment Time (HH:MM:SS):"));
        txtTime = new JTextField(time);
        frame.add(txtTime);

        frame.add(new JLabel("Status:"));
        cmbStatus = new JComboBox<>(new String[]{"Scheduled", "Completed", "Cancelled"});
        cmbStatus.setSelectedItem(status);
        frame.add(cmbStatus);

        btnSave = new JButton("Save Changes");
        btnCancel = new JButton("Cancel");
        frame.add(btnSave);
        frame.add(btnCancel);

        btnSave.addActionListener(e -> updateAppointment());
        btnCancel.addActionListener(e -> frame.dispose());

        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private void updateAppointment() {
        String name = txtPatientName.getText();
        String date = txtDate.getText();
        String time = txtTime.getText();
        String status = (String) cmbStatus.getSelectedItem();

        if (name.isEmpty() || date.isEmpty() || time.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "UPDATE appointments SET patient_name = ?, appointment_date = ?, appointment_time = ?, status = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, date);
            pstmt.setString(3, time);
            pstmt.setString(4, status);
            pstmt.setInt(5, appointmentId);
            pstmt.executeUpdate();
            
            JOptionPane.showMessageDialog(frame, "Appointment Updated Successfully.");
            frame.dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error Updating Appointment: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(frame, "Error Updating Appointment.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new EditAppointment(1, "Jane John", "2025-04-20", "12:00:00", "Scheduled");
    }
}

