package JobPortal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;

public class JobListPage extends JFrame {
    private JTable jobTable;
    private JScrollPane scrollPane;
    private DefaultTableModel model;
    private String username;

    public JobListPage(String username) {
        this.username = username;

        setTitle("All Jobs");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(232, 232, 232));

        JLabel titleLabel = new JLabel("List of Available Jobs", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(128, 0, 0));
        add(titleLabel, BorderLayout.NORTH);

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

            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (isRowSelected(row)) {
                    c.setBackground(new Color(173, 216, 230));
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(new Color(249, 243, 227));
                    c.setForeground(new Color(128, 0, 0));
                }
                return c;
            }
        };

        jobTable.setBackground(new Color(249, 243, 227));
        jobTable.setForeground(new Color(128, 0, 0));
        jobTable.setGridColor(new Color(232, 232, 232));
        jobTable.setSelectionBackground(new Color(173, 216, 230));
        jobTable.setSelectionForeground(new Color(0, 0, 0));

        JTableHeader tableHeader = jobTable.getTableHeader();
        tableHeader.setBackground(new Color(128, 0, 0));
        tableHeader.setForeground(new Color(255, 255, 255));
        tableHeader.setBorder(BorderFactory.createLineBorder(new Color(232, 232, 232)));

        scrollPane = new JScrollPane(jobTable);
        scrollPane.setBackground(new Color(232, 232, 232));
        scrollPane.getViewport().setBackground(new Color(232, 232, 232));
        add(scrollPane, BorderLayout.CENTER);

        fetchAllJobs();

        JButton backButton = new JButton("Back to Dashboard");
        backButton.setBackground(new Color(128, 0, 0));
        backButton.setForeground(new Color(173, 216, 230));
        backButton.addActionListener(e -> {
            new DashboardPage(username).setVisible(true);
            dispose();
        });
        add(backButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void fetchAllJobs() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Jobs";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            model.setRowCount(0);

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
                JOptionPane.showMessageDialog(this, "No jobs available at the moment.", "No Jobs", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching job data.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new JobListPage("user1");
    }
}
