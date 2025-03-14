package JobPortal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ProfilePage extends JFrame {
    private String username;
    private JTextField phoneField, emailField, skill1Field, skill2Field, skill3Field;
    private JButton saveButton;

    public ProfilePage(String username) {
        this.username = username;

        setTitle("Profile");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        getContentPane().setBackground(new Color(0xE8E8E8));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setForeground(new Color(0x800000));
        add(phoneLabel, gbc);
        phoneField = new JTextField();
        phoneField.setForeground(new Color(0x800000));
        gbc.gridx = 1;
        add(phoneField, gbc);

        // Email Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(new Color(0x800000));
        add(emailLabel, gbc);
        emailField = new JTextField();
        emailField.setForeground(new Color(0x800000));
        gbc.gridx = 1;
        add(emailField, gbc);

        // Skill 1
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel skill1Label = new JLabel("Skill 1:");
        skill1Label.setForeground(new Color(0x800000));
        add(skill1Label, gbc);
        skill1Field = new JTextField();
        skill1Field.setForeground(new Color(0x800000));
        gbc.gridx = 1;
        add(skill1Field, gbc);

        // Skill 2
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel skill2Label = new JLabel("Skill 2:");
        skill2Label.setForeground(new Color(0x800000));
        add(skill2Label, gbc);
        skill2Field = new JTextField();
        skill2Field.setForeground(new Color(0x800000));
        gbc.gridx = 1;
        add(skill2Field, gbc);

        // Skill 3
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel skill3Label = new JLabel("Skill 3:");
        skill3Label.setForeground(new Color(0x800000));
        add(skill3Label, gbc);
        skill3Field = new JTextField();
        skill3Field.setForeground(new Color(0x800000));
        gbc.gridx = 1;
        add(skill3Field, gbc);

        // Save Button
        saveButton = new JButton("Save");
        saveButton.setBackground(new Color(0x800000));
        saveButton.setForeground(Color.WHITE);  // White text
        saveButton.setFocusPainted(false);
        saveButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        saveButton.setBorder(BorderFactory.createLineBorder(new Color(0x800000)));
        saveButton.setOpaque(true);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        add(saveButton, gbc);

        // Change button colors on hover
        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                saveButton.setBackground(Color.WHITE);  // Change to white when hovered
                saveButton.setForeground(new Color(0x800000));  // Change text to deep maroon
                saveButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));  // Border becomes white
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                saveButton.setBackground(new Color(0x800000));  // Revert to original maroon
                saveButton.setForeground(Color.WHITE);  // Revert text to white
                saveButton.setBorder(BorderFactory.createLineBorder(new Color(0x800000)));  // Revert border
            }
        });


        JButton backButton = new JButton("Back to Dashboard");
        backButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        backButton.setBackground(new Color(0x800000)); // Maroon background
        backButton.setForeground(Color.WHITE); // White text color
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createLineBorder(new Color(0x800000))); // Maroon border
        backButton.addActionListener(e -> {

            new DashboardPage(username).setVisible(true);
            dispose();
        });

//        JButton backButton = new JButton("Back to Dashboard");
//        backButton.setBackground(new Color(0x800000));  // Deep maroon background
//        backButton.setForeground(Color.WHITE);  // White text
//        backButton.setFocusPainted(false);
//        backButton.setFont(new Font("Tahoma", Font.BOLD, 14));
//        backButton.setBorder(BorderFactory.createLineBorder(new Color(0x800000)));
        gbc.gridy = 6;
        add(backButton, gbc);
        fetchProfileData(username);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProfileData(username);
            }
        });
    }

    private void fetchProfileData(String username) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT phone_no, email, skill1, skill2, skill3 FROM Users WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                phoneField.setText(rs.getString("phone_no"));
                emailField.setText(rs.getString("email"));
                skill1Field.setText(rs.getString("skill1"));
                skill2Field.setText(rs.getString("skill2"));
                skill3Field.setText(rs.getString("skill3"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching profile data.");
        }
    }

    private void updateProfileData(String username) {
        String newPhone = phoneField.getText();
        String newEmail = emailField.getText();
        String newSkill1 = skill1Field.getText();
        String newSkill2 = skill2Field.getText();
        String newSkill3 = skill3Field.getText();

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE Users SET phone_no = ?, email = ?, skill1 = ?, skill2 = ?, skill3 = ? WHERE username = ?");
            stmt.setString(1, newPhone);
            stmt.setString(2, newEmail);
            stmt.setString(3, newSkill1);
            stmt.setString(4, newSkill2);
            stmt.setString(5, newSkill3);
            stmt.setString(6, username);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Profile updated successfully!");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating profile.");
        }
    }

    public static void main(String[] args) {
        new ProfilePage("user123").setVisible(true);
    }
}

