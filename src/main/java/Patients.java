import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import com.celestialdental.database.DatabaseConnection;

public class Patients {
    private final JFrame frame;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JTextField txtName, txtAge, txtContact, txtSearch;
    private final JButton btnAdd, btnEdit, btnDelete, btnSearch;

    public Patients() {
        frame = new JFrame("Patient Management");
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout());

        JPanel panelTop = new JPanel();
        txtSearch = new JTextField(15);
        btnSearch = new JButton("Search");
        btnSearch.addActionListener(e -> searchPatients(txtSearch.getText()));
        panelTop.add(new JLabel("Search:"));
        panelTop.add(txtSearch);
        panelTop.add(btnSearch);
        frame.add(panelTop, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Contact"}, 0);
        table = new JTable(tableModel);
        loadPatients();
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panelBottom = new JPanel(new GridLayout(2, 4, 5, 5));
        txtName = new JTextField();
        txtAge = new JTextField();
        txtContact = new JTextField();
        btnAdd = new JButton("Add Patient");
        btnEdit = new JButton("Edit Patient");
        btnDelete = new JButton("Delete Patient");

        btnAdd.addActionListener(e -> addPatient());
        btnEdit.addActionListener(e -> editPatient());
        btnDelete.addActionListener(e -> deletePatient());

        panelBottom.add(new JLabel("Name:"));
        panelBottom.add(txtName);
        panelBottom.add(new JLabel("Age:"));
        panelBottom.add(txtAge);
        panelBottom.add(new JLabel("Contact:"));
        panelBottom.add(txtContact);
        panelBottom.add(btnAdd);
        panelBottom.add(btnEdit);
        panelBottom.add(btnDelete);

        frame.add(panelBottom, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void loadPatients() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT id, name, age, contact FROM patients";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            tableModel.setRowCount(0);

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("contact")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error loading patients: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addPatient() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "INSERT INTO patients (name, age, contact) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, txtName.getText());
            pstmt.setInt(2, Integer.parseInt(txtAge.getText()));
            pstmt.setString(3, txtContact.getText());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Patient Added.");
            loadPatients(); 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error adding patient: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editPatient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Select a patient to edit.");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "UPDATE patients SET name=?, age=?, contact=? WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, txtName.getText());
            pstmt.setInt(2, Integer.parseInt(txtAge.getText()));
            pstmt.setString(3, txtContact.getText());
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Patient Updated.");
            loadPatients(); 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error updating patient: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletePatient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Select a patient to delete.");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "DELETE FROM patients WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Patient Deleted.");
            loadPatients(); 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error deleting patient: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchPatients(String name) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT id, name, age, contact FROM patients WHERE name LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();

            tableModel.setRowCount(0);

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("contact")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error searching patients: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Patients();
    }
}


