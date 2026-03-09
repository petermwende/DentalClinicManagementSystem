import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import com.celestialdental.database.DatabaseConnection;

public class Billing {
    private final JFrame frame;
    private final JTable table;
    private final JTextField txtPatientID, txtAmount;
    private final JButton btnAddBill, btnPayBill;

    public Billing() {
        frame = new JFrame("Billing System");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JPanel panelTop = new JPanel();
        txtPatientID = new JTextField(10);
        txtAmount = new JTextField(10);
        btnAddBill = new JButton("Add Bill");
        btnPayBill = new JButton("Make Payment");

        btnAddBill.addActionListener(e -> addBill());
        btnPayBill.addActionListener(e -> payBill());

        panelTop.add(new JLabel("Patient ID:"));
        panelTop.add(txtPatientID);
        panelTop.add(new JLabel("Amount:"));
        panelTop.add(txtAmount);
        panelTop.add(btnAddBill);
        panelTop.add(btnPayBill);
        frame.add(panelTop, BorderLayout.NORTH);

        table = new JTable();
        loadBills();
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void loadBills() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM billing";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{"ID", "Patient ID", "Amount", "Date"});
            
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("billing_date")
                });
            }

            table.setModel(model); 

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading bills: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addBill() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "INSERT INTO billing (patient_id, amount) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, Integer.parseInt(txtPatientID.getText()));
            pstmt.setDouble(2, Double.parseDouble(txtAmount.getText()));
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Bill Added Successfully.");
            loadBills(); 
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error adding bill: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void payBill() {
        JOptionPane.showMessageDialog(frame, "Payment Successful.");
    }

    public static void main(String[] args) {
        new Billing();
    }
}


