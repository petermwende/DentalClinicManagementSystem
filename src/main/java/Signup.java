import javax.swing.*;
import java.awt.*;
import java.sql.*;
import com.celestialdental.database.DatabaseConnection;

public class Signup {
    private final JFrame frame;
    private final JTextField txtFullname, txtUsername;
    private final JPasswordField txtPassword;
    private final JComboBox<String> cmbRole;
    private final JButton btnRegister;

    public Signup() {
        frame = new JFrame("User Signup");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 2));

        JLabel lblFullname = new JLabel("Full Name:");
        JLabel lblUsername = new JLabel("Username:");
        JLabel lblPassword = new JLabel("Password:");
        JLabel lblRole = new JLabel("Role:");

        txtFullname = new JTextField();
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        cmbRole = new JComboBox<>(new String[]{"Admin", "Dentist", "Receptionist"});
        btnRegister = new JButton("Register");

        frame.add(lblFullname);
        frame.add(txtFullname);
        frame.add(lblUsername);
        frame.add(txtUsername);
        frame.add(lblPassword);
        frame.add(txtPassword);
        frame.add(lblRole);
        frame.add(cmbRole);
        frame.add(new JLabel(""));
        frame.add(btnRegister);

        btnRegister.addActionListener(e -> registerUser());

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private void registerUser() {
        String fullname = txtFullname.getText();
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        String role = (String) cmbRole.getSelectedItem();

        if (fullname.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "INSERT INTO users (fullname, username, password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, fullname);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, role);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(frame, "User Registered Successfully.");
            frame.dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Registration Failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Signup();
    }
}


