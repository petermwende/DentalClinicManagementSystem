package com.celestialdental.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManagePatients extends JFrame {
    private JTable patientTable;
    private DefaultTableModel tableModel;

    public ManagePatients() {
        setTitle("Manage Patients");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        String[] columns = {"ID", "Name", "Age", "Contact", "Address", "Next of Kin"};
        Object[][] data = {
                {"1", "Jean Fay", "24", "0790000000", "Nairobi, Kenya", "James Fay"},
                {"2", "Martin Rich", "25", "0756000000", "Mombasa, Kenya", "Anna Rich"},
                {"3", "Linet Givens", "21", "0721000000", "Kisumu, Kenya", "David Givens"},
                {"4", "Alvin Wambui", "31", "0767000000", "Nakuru, Kenya", "Michael Wambui"},
                {"5", "Elizabeth Mia", "35", "0746000000", "Eldoret, Kenya", "Grace Mia"}
        };

        tableModel = new DefaultTableModel(data, columns);
        patientTable = new JTable(tableModel);

        patientTable.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        patientTable.setRowHeight(35);
        patientTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));

        JScrollPane tableScrollPane = new JScrollPane(patientTable);
        add(tableScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15));

        JButton btnAdd = createStyledButton("Add Patient", new Color(173, 216, 230));  
        JButton btnEdit = createStyledButton("Edit Patient", new Color(255, 193, 7));  
        JButton btnDelete = createStyledButton("Delete Patient", new Color(220, 53, 69));  

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        add(buttonPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addPatient());
        btnEdit.addActionListener(e -> editPatient());
        btnDelete.addActionListener(e -> deletePatient());

        setVisible(true);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(200, 50));
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        return button;
    }

    private void addPatient() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 15, 15));

        JTextField idField = createStyledTextField();
        JTextField nameField = createStyledTextField();
        JTextField ageField = createStyledTextField();
        JTextField contactField = createStyledTextField();
        JTextField addressField = createStyledTextField();
        JTextField nextOfKinField = createStyledTextField();

        panel.add(createStyledLabel("Patient ID:"));
        panel.add(idField);
        panel.add(createStyledLabel("Name:"));
        panel.add(nameField);
        panel.add(createStyledLabel("Age:"));
        panel.add(ageField);
        panel.add(createStyledLabel("Contact:"));
        panel.add(contactField);
        panel.add(createStyledLabel("Address:"));
        panel.add(addressField);
        panel.add(createStyledLabel("Next of Kin:"));
        panel.add(nextOfKinField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Add Patient", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            tableModel.addRow(new Object[]{
                    idField.getText().trim(),
                    nameField.getText().trim(),
                    ageField.getText().trim(),
                    contactField.getText().trim(),
                    addressField.getText().trim(),
                    nextOfKinField.getText().trim()
            });
        }
    }

    private void editPatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a patient to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new GridLayout(5, 2, 15, 15));

        JTextField nameField = createStyledTextField(tableModel.getValueAt(selectedRow, 1).toString());
        JTextField ageField = createStyledTextField(tableModel.getValueAt(selectedRow, 2).toString());
        JTextField contactField = createStyledTextField(tableModel.getValueAt(selectedRow, 3).toString());
        JTextField addressField = createStyledTextField(tableModel.getValueAt(selectedRow, 4).toString());
        JTextField nextOfKinField = createStyledTextField(tableModel.getValueAt(selectedRow, 5).toString());

        panel.add(createStyledLabel("Edit Name:"));
        panel.add(nameField);
        panel.add(createStyledLabel("Edit Age:"));
        panel.add(ageField);
        panel.add(createStyledLabel("Edit Contact:"));
        panel.add(contactField);
        panel.add(createStyledLabel("Edit Address:"));
        panel.add(addressField);
        panel.add(createStyledLabel("Edit Next of Kin:"));
        panel.add(nextOfKinField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Edit Patient", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            tableModel.setValueAt(nameField.getText().trim(), selectedRow, 1);
            tableModel.setValueAt(ageField.getText().trim(), selectedRow, 2);
            tableModel.setValueAt(contactField.getText().trim(), selectedRow, 3);
            tableModel.setValueAt(addressField.getText().trim(), selectedRow, 4);
            tableModel.setValueAt(nextOfKinField.getText().trim(), selectedRow, 5);
        }
    }

    private void deletePatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a patient to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this patient?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
        }
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        textField.setPreferredSize(new Dimension(250, 40));
        return textField;
    }

    private JTextField createStyledTextField(String text) {
        JTextField textField = new JTextField(text);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        textField.setPreferredSize(new Dimension(250, 40));
        return textField;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text, JLabel.RIGHT);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        return label;
    }

    public static void main(String[] args) {
        new ManagePatients();
    }
}