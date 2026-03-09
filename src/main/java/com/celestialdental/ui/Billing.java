package com.celestialdental.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Billing extends JFrame {
    JTable billingTable;
    DefaultTableModel tableModel;

    public Billing() {
        setTitle("Billing");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        setLocationRelativeTo(null);

        String[] columns = {"Invoice ID", "Patient Name", "Amount", "Status", "Payment Method"};
        Object[][] data = {
                {"1001", "Jean Fay", "Ksh. 2000", "Paid", "MPesa"},
                {"1002", "Martin Rich", "Ksh. 1500", "Pending", "Insurance"},
                {"1003", "Linet Givens", "Ksh. 3000", "Paid", "Cash"},
                {"1004", "Alvin Wambui", "Ksh. 1000", "Pending", "MPesa"},
                {"1005", "Elizabeth Mia", "Ksh. 2500", "Paid", "Insurance"}
        };
        tableModel = new DefaultTableModel(data, columns);
        billingTable = new JTable(tableModel);

        billingTable.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        billingTable.setRowHeight(35);
        billingTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));

        JScrollPane tableScrollPane = new JScrollPane(billingTable);
        add(tableScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15));

        JButton btnPay = createStyledButton("Process Payment", new Color(40, 167, 69));  // Green
        JButton btnView = createStyledButton("View Invoice", new Color(255, 140, 0));  // Orange

        buttonPanel.add(btnPay);
        buttonPanel.add(btnView);
        add(buttonPanel, BorderLayout.SOUTH);

        btnPay.addActionListener(e -> processPayment());
        btnView.addActionListener(e -> viewInvoice());

        setVisible(true);
    }
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(200, 50));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        return button;
    }
    private void processPayment() {
        int selectedRow = billingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an invoice to process payment.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JPanel panel = new JPanel(new GridLayout(2, 2, 15, 15));

        String[] paymentMethods = {"MPesa", "Cash", "Insurance", "Card"};
        JComboBox<String> paymentMethodCombo = new JComboBox<>(paymentMethods);
        paymentMethodCombo.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        panel.add(createStyledLabel("Select Payment Method:"));
        panel.add(paymentMethodCombo);

        int confirm = JOptionPane.showConfirmDialog(this, panel, "Confirm Payment", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.setValueAt("Paid", selectedRow, 3);
            tableModel.setValueAt(paymentMethodCombo.getSelectedItem().toString(), selectedRow, 4);
            JOptionPane.showMessageDialog(this, "Payment processed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void viewInvoice() {
        int selectedRow = billingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an invoice to view.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String invoiceDetails = "Invoice ID: " + tableModel.getValueAt(selectedRow, 0) +
                "\nPatient Name: " + tableModel.getValueAt(selectedRow, 1) +
                "\nAmount: " + tableModel.getValueAt(selectedRow, 2) +
                "\nStatus: " + tableModel.getValueAt(selectedRow, 3) +
                "\nPayment Method: " + tableModel.getValueAt(selectedRow, 4);

        JTextArea invoiceTextArea = new JTextArea(invoiceDetails);
        invoiceTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        invoiceTextArea.setEditable(false);
        invoiceTextArea.setLineWrap(true);
        invoiceTextArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(invoiceTextArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        JOptionPane.showMessageDialog(this, scrollPane, "Invoice Details", JOptionPane.INFORMATION_MESSAGE);
    }
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text, JLabel.RIGHT);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        return label;
    }
    public static void main(String[] args) {
        new Billing();
    }
}

