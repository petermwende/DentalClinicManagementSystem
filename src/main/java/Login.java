import javax.swing.*;
import java.awt.*;
import java.sql.*;
import com.celestialdental.database.DatabaseConnection;
import com.celestialdental.ui.Dashboard;


public class Login {
    private final JFrame frame;
    private final JTextField txtUsername;
    private final JPasswordField txtPassword;
    private final JButton btnLogin, btnSignup;

    public Login() {
        frame = new JFrame("User Login");
        frame.setSize(400, 250);
        frame.setLayout(new GridLayout(4, 2));

        JLabel lblUsername = new JLabel("Username:");
        JLabel lblPassword = new JLabel("Password:");
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        btnLogin = new JButton("Login");
        btnSignup = new JButton("Signup");

        btnLogin.addActionListener(e -> authenticateUser());
        btnSignup.addActionListener(e -> new Signup());

        frame.add(lblUsername);
        frame.add(txtUsername);
        frame.add(lblPassword);
        frame.add(txtPassword);
        frame.add(new JLabel(""));
        frame.add(btnLogin);
        frame.add(new JLabel(""));
        frame.add(btnSignup);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void authenticateUser() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                JOptionPane.showMessageDialog(frame, "Login Successful! Welcome, " + role);
                frame.dispose();

              switch (role) {
                case "Admin" -> {
                    Dashboard dashboard = new Dashboard(username);
                    dashboard.setVisible(true);
                    }
                case "Dentist" -> JOptionPane.showMessageDialog(frame, "Dentist Dashboard");
                case "Customer Service Representative" -> JOptionPane.showMessageDialog(frame, "Customer Service Rep Dashboard");
                default -> JOptionPane.showMessageDialog(frame, "Unknown Role");
            }

            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Username or Password!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
       new Login();
        
    }
}

