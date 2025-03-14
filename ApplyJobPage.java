package JobPortal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;

public class ApplyJobPage extends JFrame {
    private String username;
    private JTextField jobIdField;
    private JButton applyButton;
    private JButton viewApplicationsButton;
    private JTable jobTable;
    private JScrollPane tableScrollPane;

    public ApplyJobPage(String username) {
        this.username = username;

        setTitle("Apply for a Job");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(0xE8E8E8));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(0xE8E8E8));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel jobIdLabel = new JLabel("Enter Job ID:");
        jobIdLabel.setForeground(new Color(0x800000));
        jobIdField = new JTextField(12);
        jobIdField.setForeground(new Color(0x800000));
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(0x800000));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(new SearchButtonListener());
        searchButton.setFocusPainted(false);
        searchButton.setBorderPainted(false);
        searchButton.setPreferredSize(new Dimension(90, 30));

        searchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                searchButton.setBackground(Color.WHITE);
                searchButton.setForeground(new Color(0x800000));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                searchButton.setBackground(new Color(0x800000));
                searchButton.setForeground(Color.WHITE);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(jobIdLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(jobIdField, gbc);
        gbc.gridx = 2;
        mainPanel.add(searchButton, gbc);

        jobTable = new JTable();
        tableScrollPane = new JScrollPane(jobTable);
        tableScrollPane.setPreferredSize(new Dimension(450, 150));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        mainPanel.add(tableScrollPane, gbc);

        applyButton = new JButton("Apply");
        applyButton.setBackground(new Color(0x800000));
        applyButton.setForeground(Color.WHITE);
        applyButton.setEnabled(false);
        applyButton.setFocusPainted(false);
        applyButton.setPreferredSize(new Dimension(100, 35));

        applyButton.addActionListener(new ApplyButtonListener());
        applyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                applyButton.setBackground(Color.WHITE);
                applyButton.setForeground(new Color(0x800000));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                applyButton.setBackground(new Color(0x800000));
                applyButton.setForeground(Color.WHITE);
            }
        });

        viewApplicationsButton = new JButton("View Applications");
        viewApplicationsButton.setBackground(new Color(0x800000));
        viewApplicationsButton.setForeground(Color.WHITE);
        viewApplicationsButton.setFocusPainted(false);
        viewApplicationsButton.setPreferredSize(new Dimension(140, 35));
        viewApplicationsButton.addActionListener(e -> {
            new MyApplicationsPage(username).setVisible(true);
            dispose();
        });

        viewApplicationsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                viewApplicationsButton.setBackground(Color.WHITE);
                viewApplicationsButton.setForeground(new Color(0x800000));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                viewApplicationsButton.setBackground(new Color(0x800000));
                viewApplicationsButton.setForeground(Color.WHITE);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.setBackground(new Color(0xE8E8E8));
        buttonPanel.add(applyButton);
        buttonPanel.add(viewApplicationsButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        mainPanel.add(buttonPanel, gbc);

        JButton backButton = new JButton("Back to Dashboard");
        backButton.setBackground(new Color(0x800000));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(140, 35));
        backButton.addActionListener(e -> {
            new DashboardPage(username).setVisible(true);
            dispose();
        });

        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(Color.WHITE);
                backButton.setForeground(new Color(0x800000));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(0x800000));
                backButton.setForeground(Color.WHITE);
            }
        });

        add(mainPanel, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String jobIdText = jobIdField.getText();
            if (jobIdText.isEmpty()) {
                JOptionPane.showMessageDialog(ApplyJobPage.this, "Please enter a Job ID.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int jobId = Integer.parseInt(jobIdText);
                if (isJobExists(jobId)) {
                    displayJobDetails(jobId);
                } else {
                    JOptionPane.showMessageDialog(ApplyJobPage.this, "Invalid Job ID. Please enter a valid job ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ApplyJobPage.this, "Job ID must be a number.", "Input Error", JOptionPane.WARNING_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(ApplyJobPage.this, "Error fetching job details.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean isJobExists(int jobId) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jobportal", "root", "yash03");
        String query = "SELECT job_id FROM jobs WHERE job_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, jobId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } finally {
            conn.close();
        }
    }

    private void displayJobDetails(int jobId) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jobportal", "root", "yash03");
        String query = "SELECT job_id, job_name, company_name, location, skill1, skill2, skill3, salary FROM jobs WHERE job_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, jobId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String[] columns = {"Job ID", "Job Name", "Company", "Location", "Skill 1", "Skill 2", "Skill 3", "Salary"};
                Object[][] data = new Object[1][8];
                data[0][0] = rs.getInt("job_id");
                data[0][1] = rs.getString("job_name");
                data[0][2] = rs.getString("company_name");
                data[0][3] = rs.getString("location");
                data[0][4] = rs.getString("skill1");
                data[0][5] = rs.getString("skill2");
                data[0][6] = rs.getString("skill3");
                data[0][7] = rs.getDouble("salary");
                jobTable.setModel(new javax.swing.table.DefaultTableModel(data, columns));
                applyButton.setEnabled(true);
            }
        } finally {
            conn.close();
        }
    }

    private class ApplyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int jobId = Integer.parseInt(jobIdField.getText());
            try {
                if (!hasApplied(jobId)) {
                    applyForJob(jobId);
                    JOptionPane.showMessageDialog(ApplyJobPage.this, "Successfully applied for the job!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(ApplyJobPage.this, "You have already applied for this job.", "Application Error", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(ApplyJobPage.this, "Error applying for the job.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean hasApplied(int jobId) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jobportal", "root", "yash03");
        String query = "SELECT * FROM applications WHERE username = ? AND job_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setInt(2, jobId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } finally {
            conn.close();
        }
    }

    private void applyForJob(int jobId) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jobportal", "root", "yash03");
        String query = "INSERT INTO applications (username, job_id, applied_at) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setInt(2, jobId);
            stmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            stmt.executeUpdate();
        } finally {
            conn.close();
        }
    }
}
