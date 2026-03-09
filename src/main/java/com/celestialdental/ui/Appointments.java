package com.celestialdental.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class Appointments extends JFrame {
    JTable appointmentTable;
    DefaultTableModel tableModel;

    public Appointments() {
        setTitle("Appointments");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Patient Name", "Date", "Time", "Status", "Doctor", "Type", "Remarks"};
        Object[][] data = {
                {"1", "Jean Fay", "2025-03-02", "09:00 AM", "Confirmed", "Dr. Smith", "Checkup", "None"},
                {"2", "Martin Rich", "2025-03-25", "07:00 AM", "Confirmed", "Dr. Adams", "Cleaning", "Urgent"},
                {"3", "Linet Givens", "2025-04-20", "03:00 PM", "Pending", "Dr. Khan", "Surgery", "N/A"},
                {"4", "Alvin Wambui", "2025-04-10", "01:00 PM", "Pending", "Dr. Patel", "Consultation", "Follow-up"},
                {"5", "Elizabeth Mia", "2025-04-15", "09:00 AM", "Pending", "Dr. Carter", "Checkup", "None"}
        };

        tableModel = new DefaultTableModel(data, columns);
        appointmentTable = new JTable(tableModel);

        appointmentTable.setRowHeight(40);
        appointmentTable.setFont(new Font("Arial", Font.PLAIN, 18));

        JTableHeader tableHeader = appointmentTable.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 20));

        JScrollPane tableScrollPane = new JScrollPane(appointmentTable);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton btnSchedule = new JButton("Schedule");
        JButton btnEdit = new JButton("Edit");
        JButton btnCancel = new JButton("Cancel");

        styleButton(btnSchedule, new Color(34, 167, 240), Color.WHITE);
        styleButton(btnEdit, new Color(244, 208, 63), Color.BLACK);
        styleButton(btnCancel, new Color(231, 76, 60), Color.WHITE);

        buttonPanel.add(btnSchedule);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnCancel);

        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnSchedule.addActionListener(e -> scheduleAppointment());
        btnEdit.addActionListener(e -> editAppointment());
        btnCancel.addActionListener(e -> cancelAppointment());

        setVisible(true);
    }

    private void scheduleAppointment() {
        JPanel panel = createInputForm();

        int result = JOptionPane.showConfirmDialog(this, panel, "Schedule Appointment", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            tableModel.addRow(new Object[]{
                    getText(panel, 0),
                    getText(panel, 1),
                    getText(panel, 2),
                    getText(panel, 3),
                    getText(panel, 4),
                    getText(panel, 5),
                    getText(panel, 6),
                    getText(panel, 7)
            });
        }
    }

    private void editAppointment() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an appointment to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel panel = createInputForm(
                tableModel.getValueAt(selectedRow, 1).toString(),
                tableModel.getValueAt(selectedRow, 2).toString(),
                tableModel.getValueAt(selectedRow, 3).toString(),
                tableModel.getValueAt(selectedRow, 4).toString(),
                tableModel.getValueAt(selectedRow, 5).toString(),
                tableModel.getValueAt(selectedRow, 6).toString(),
                tableModel.getValueAt(selectedRow, 7).toString()
        );

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Appointment", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            for (int i = 1; i <= 7; i++) {
                tableModel.setValueAt(getText(panel, i - 1), selectedRow, i);
            }
        }
    }

    private void cancelAppointment() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an appointment to cancel.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel this appointment?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
        }
    }

    private JPanel createInputForm(String... values) {
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        String[] labels = {"Appointment ID:", "Patient Name:", "Date (YYYY-MM-DD):", "Time (HH:MM AM/PM):",
                "Status (Confirmed/Pending):", "Doctor Assigned:", "Appointment Type:", "Remarks:"};

        for (int i = 0; i < labels.length; i++) {
            panel.add(createStyledLabel(labels[i]));
            panel.add(createStyledTextField(values.length > i ? values[i] : ""));
        }

        return panel;
    }

    private JTextField createStyledTextField(String text) {
        JTextField textField = new JTextField(text);
        textField.setFont(new Font("Arial", Font.PLAIN, 18));
        textField.setPreferredSize(new Dimension(250, 40));
        return textField;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text, JLabel.RIGHT);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        return label;
    }

    private String getText(JPanel panel, int index) {
        return ((JTextField) panel.getComponent(index * 2 + 1)).getText().trim();
    }

    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setPreferredSize(new Dimension(150, 50));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
    }

    public static void main(String[] args) {
        new Appointments();
    }
}
