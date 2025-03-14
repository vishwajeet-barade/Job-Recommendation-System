package JobPortal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupPage extends JFrame {
    private JTextField userField, nameField, emailField, phoneField;
    private JPasswordField passField;
    private JComboBox<String> genderBox;

    public SignupPage() {
        setTitle("Signup");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(232, 232, 232));

        JLabel titleLabel = new JLabel("Signup", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(128, 0, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(232, 232, 232));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        userField = new JTextField(20);
        passField = new JPasswordField(20);
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);
        genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});

        userField.setForeground(new Color(128, 0, 0));
        passField.setForeground(new Color(128, 0, 0));
        nameField.setForeground(new Color(128, 0, 0));
        emailField.setForeground(new Color(128, 0, 0));
        phoneField.setForeground(new Color(128, 0, 0));

        userField.setBackground(new Color(249, 243, 227));
        passField.setBackground(new Color(249, 243, 227));
        nameField.setBackground(new Color(249, 243, 227));
        emailField.setBackground(new Color(249, 243, 227));
        phoneField.setBackground(new Color(249, 243, 227));

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(createLabel("Username:"), gbc);
        gbc.gridx = 1;
        formPanel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(createLabel("Password:"), gbc);
        gbc.gridx = 1;
        formPanel.add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(createLabel("Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(createLabel("Email:"), gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(createLabel("Phone:"), gbc);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(createLabel("Gender:"), gbc);
        gbc.gridx = 1;
        formPanel.add(genderBox, gbc);

        JButton signupButton = new JButton("Signup");
        styleButton(signupButton);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        formPanel.add(signupButton, gbc);

        JLabel alreadyHaveAccountLabel = new JLabel("Already have an account? Login");
        alreadyHaveAccountLabel.setForeground(new Color(128, 0, 0));
        alreadyHaveAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        formPanel.add(alreadyHaveAccountLabel, gbc);

        add(formPanel, BorderLayout.CENTER);

        signupButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String gender = genderBox.getSelectedItem().toString();

            try (Connection conn = DBConnection.getConnection()) {
                String query = "INSERT INTO Users (username, password, name, email, phone_no, gender) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, name);
                stmt.setString(4, email);
                stmt.setString(5, phone);
                stmt.setString(6, gender);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Signup successful!");

                new DashboardPage(username).setVisible(true);
                dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        alreadyHaveAccountLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new LoginPage().setVisible(true);
                dispose();
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                alreadyHaveAccountLabel.setText("<html><u>Already have an account? Login</u></html>");
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                alreadyHaveAccountLabel.setText("Already have an account? Login");
            }
        });

        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(128, 0, 0));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBorder(BorderFactory.createLineBorder(new Color(128, 0, 0), 2));
        button.setPreferredSize(new Dimension(250, 40));
        button.setBorder(BorderFactory.createCompoundBorder(
                button.getBorder(),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(java.awt.Graphics g, javax.swing.JComponent c) {
                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(button.getBackground());
                g2d.fillRoundRect(0, 0, button.getWidth(), button.getHeight(), 20, 20);
                super.paint(g2d, c);
                g2d.dispose();
            }
        });

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
                button.setForeground(new Color(128, 0, 0));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(128, 0, 0));
                button.setForeground(Color.WHITE);
            }
        });
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(128, 0, 0));
        return label;
    }

    public static void main(String[] args) {
        new SignupPage();
    }
}
