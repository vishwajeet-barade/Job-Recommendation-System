package JobPortal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;

public class JobSearchResultPage extends JFrame {
    private JTable jobTable;
    private JScrollPane scrollPane;
    private DefaultTableModel model;
    private String searchText;
    private String username;

    public JobSearchResultPage(String searchText, String username) {
        this.searchText = searchText;
        this.username = username;

        setTitle("Job Search Results");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(232, 232, 232));
        model = new DefaultTableModel();
        model.addColumn("Job ID");
        model.addColumn("Job Name");
        model.addColumn("Company");
        model.addColumn("Location");
        model.addColumn("Skills Required");
        model.addColumn("Salary");
        model.addColumn("Job Type");

        jobTable = new JTable(model) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jobTable.setBackground(new Color(249, 243, 227));
        jobTable.setForeground(new Color(128, 0, 0));
        jobTable.setGridColor(Color.WHITE);
        jobTable.setSelectionBackground(new Color(128, 0, 0));
        jobTable.setSelectionForeground(Color.WHITE);

        JTableHeader tableHeader = jobTable.getTableHeader();
        tableHeader.setBackground(new Color(128, 0, 0));
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        scrollPane = new JScrollPane(jobTable);
        scrollPane.setBackground(new Color(232, 232, 232));
        scrollPane.getViewport().setBackground(new Color(232, 232, 232));

        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Dashboard");
        backButton.setBackground(new Color(128, 0, 0));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> {
            new DashboardPage(username).setVisible(true);
            dispose();
        });
        add(backButton, BorderLayout.SOUTH);

        fetchFilteredJobs();

        setVisible(true);
    }

    private void fetchFilteredJobs() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Jobs WHERE job_name LIKE ? OR location LIKE ? OR skill1 LIKE ? OR skill2 LIKE ? OR skill3 LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            String searchPattern = "%" + searchText + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            stmt.setString(4, searchPattern);
            stmt.setString(5, searchPattern);

            ResultSet rs = stmt.executeQuery();

            // Clear existing rows before adding new search results
            model.setRowCount(0);

            // Loop through the result set and add rows to the table
            while (rs.next()) {
                int jobID = rs.getInt("job_id");
                String jobName = rs.getString("job_name");
                String companyName = rs.getString("company_name");
                String location = rs.getString("location");
                String skill1 = rs.getString("skill1");
                String skill2 = rs.getString("skill2");
                String skill3 = rs.getString("skill3");
                double salary = rs.getDouble("salary");
                String jobType = rs.getString("job_type");

                model.addRow(new Object[]{jobID, jobName, companyName, location, skill1 + ", " + skill2 + ", " + skill3, salary, jobType});
            }

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No jobs found for your search.", "No Results", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error during search.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new JobSearchResultPage("Software", "user123").setVisible(true);  // Example search text and username
    }
}
