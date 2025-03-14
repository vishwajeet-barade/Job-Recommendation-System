package JobPortal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginPage extends JFrame {
    private JTextField userField;
    private JPasswordField passField;

    public LoginPage() {
        setTitle("Login");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);


        getContentPane().setBackground(new Color(0xE8E8E8));

        JLabel titleLabel = new JLabel("Login", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0x800000));
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(new Color(0x800000));
        userField = new JTextField(20);
        userField.setForeground(Color.BLACK);
        userField.setBackground(new Color(232, 232, 232));
        userField.setBorder(BorderFactory.createLineBorder(new Color(0x800000)));

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(userLabel, gbc);

        gbc.gridx = 1;
        add(userField, gbc);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(new Color(0x800000));
        passField = new JPasswordField(20);
        passField.setForeground(Color.BLACK);
        passField.setBackground(new Color(232, 232, 232));
        passField.setBorder(BorderFactory.createLineBorder(new Color(0x800000)));

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passLabel, gbc);

        gbc.gridx = 1;
        add(passField, gbc);

        JButton loginButton = new JButton("Login");
        styleLoginButton(loginButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                try (Connection conn = DBConnection.getConnection()) {
                    String query = "SELECT * FROM Users WHERE username=? AND password=?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Login successful!");
                        new DashboardPage(username).setVisible(true);  // Open Dashboard
                        dispose();  // Close login window
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        JLabel newUserLabel = new JLabel("Don't have an account? Signup here!");
        newUserLabel.setForeground(new Color(0x800000));  // Gold color for the text
        newUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        newUserLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        newUserLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        newUserLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new SignupPage().setVisible(true);
                dispose();
            }
        });

        gbc.gridy = 4;  // Set link at row 4
        add(newUserLabel, gbc);

        // Make the window visible
        setVisible(true);
    }

    // Method to style the Login button with page text color and hover effect
    private void styleLoginButton(JButton button) {
        button.setBackground(Color.WHITE);  // White background
        button.setForeground(new Color(0x800000));  // Deep maroon text color
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBorder(BorderFactory.createLineBorder(new Color(0x800000), 2));  // Maroon border
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(250, 50));  // Consistent button size

        // Hover Effect for button
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0x800000));  // Background to maroon
                button.setForeground(Color.WHITE);  // Text to white
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);  // Background back to white
                button.setForeground(new Color(0x800000));  // Text back to maroon
            }
        });
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}



