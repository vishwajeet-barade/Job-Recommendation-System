package JobPortal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class DashboardPage extends JFrame {
    private String username;
    private JTextField searchField;
    private JButton searchButton;
    private JTable jobListingsTable;

    public DashboardPage(String username) {
        this.username = username;
        setTitle("Dashboard");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(0x2D2D2D));

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        searchPanel.setBackground(new Color(0x2D2D2D));
        JLabel searchLabel = new JLabel("Search Jobs: ");
        searchLabel.setForeground(new Color(0xF4F4F4));
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.setBackground(new Color(0x9B3D3D));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createLineBorder(new Color(0xC49A6C), 2));

        searchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                searchButton.setBackground(new Color(0xB17F48));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                searchButton.setBackground(new Color(0x9B3D3D));
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchJobs();
            }
        });

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        mainPanel.add(searchPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        JPanel jobListingsPanel = new JPanel(new BorderLayout());
        jobListingsPanel.setBackground(new Color(0x2D2D2D));

        JLabel jobListingsLabel = new JLabel("Recommended Job Listings", JLabel.CENTER);
        jobListingsLabel.setForeground(new Color(0xF4F4F4));
        jobListingsPanel.add(jobListingsLabel, BorderLayout.NORTH);

        String[] columnNames = {"Job ID", "Job Name", "Company Name", "Location", "Skills", "Salary", "Apply By"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        jobListingsTable = new JTable(model);
        jobListingsTable.setBackground(new Color(0xF5F5F5));
        jobListingsTable.setForeground(new Color(0x800000));
        JScrollPane scrollPane = new JScrollPane(jobListingsTable);
        jobListingsPanel.add(scrollPane, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.5;
        gbc.weighty = 1;
        contentPanel.add(jobListingsPanel, gbc);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(5, 1, 10, 10));
        sidebar.setPreferredSize(new Dimension(90, 0));

        JButton allJobsButton = createSidebarButton("All Jobs");
        allJobsButton.addActionListener(e -> {
            new JobListPage(username).setVisible(true);
            dispose();
        });

        JButton myApplicationsButton = createSidebarButton("My Applications");
        myApplicationsButton.addActionListener(e -> {
            new MyApplicationsPage(username).setVisible(true);
            dispose();
        });

        JButton applyButton = createSidebarButton("Apply");
        applyButton.addActionListener(e -> {
            new ApplyJobPage(username).setVisible(true);
            dispose();
        });

        JButton profileButton = createSidebarButton("Profile");
        profileButton.addActionListener(e -> {
            new ProfilePage(username).setVisible(true);
            dispose();
        });

        JButton logoutButton = createSidebarButton("Logout");
        logoutButton.addActionListener(e -> {
            new LoginPage().setVisible(true);
            dispose();
        });

        sidebar.add(allJobsButton);
        sidebar.add(myApplicationsButton);
        sidebar.add(applyButton);
        sidebar.add(profileButton);
        sidebar.add(logoutButton);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 1;
        contentPanel.add(sidebar, gbc);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);

        loadJobListings();
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0x9B3D3D));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(new Color(0xC49A6C), 2));
        button.setPreferredSize(new Dimension(100, 30));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0xB17F48));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0x9B3D3D));
            }
        });
        return button;
    }

    private void searchJobs() {
        String searchText = searchField.getText();
        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search term.", "Search Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        new JobSearchResultPage(searchText, username).setVisible(true);
        dispose();
    }

    private void loadJobListings() {
        String[] userSkills = {"Java", "Spring Boot", "SQL"};

        String query = "SELECT job_id, job_name, company_name, location, skill1, skill2, skill3, salary, apply_by FROM Jobs";

        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            DefaultTableModel model = (DefaultTableModel) jobListingsTable.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                String skill1 = rs.getString("skill1");
                String skill2 = rs.getString("skill2");
                String skill3 = rs.getString("skill3");

                for (String userSkill : userSkills) {
                    if (skill1.contains(userSkill) || skill2.contains(userSkill) || skill3.contains(userSkill)) {
                        Object[] row = {
                                rs.getInt("job_id"),
                                rs.getString("job_name"),
                                rs.getString("company_name"),
                                rs.getString("location"),
                                skill1 + ", " + skill2 + ", " + skill3,
                                rs.getInt("salary"),
                                rs.getString("apply_by")
                        };
                        model.addRow(row);
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
