package JobPortal;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class MyApplicationsPage extends JFrame {
    private String username;

    public MyApplicationsPage(String username) {
        this.username = username;

        setTitle("My Applications");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(232, 232, 232));

        JLabel titleLabel = new JLabel("My Applications", JLabel.CENTER);
        titleLabel.setForeground(new Color(128, 0, 0));
        add(titleLabel, BorderLayout.NORTH);

        String[] columns = {"Job ID", "Job Name", "Applied On", "Company"};
        Object[][] data = fetchAppliedJobs();

        if (data.length == 0) {
            JOptionPane.showMessageDialog(this, "No applications found.", "No Data", JOptionPane.INFORMATION_MESSAGE);
        }

        JTable applicationsTable = new JTable(data, columns);
        applicationsTable.setFillsViewportHeight(true);

        applicationsTable.setBackground(new Color(249, 243, 227));
        applicationsTable.setForeground(new Color(128, 0, 0));
        applicationsTable.getTableHeader().setBackground(new Color(128, 0, 0));
        applicationsTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(applicationsTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Dashboard");
        backButton.setBackground(new Color(128, 0, 0));
        backButton.setForeground(Color.WHITE); // White text
        backButton.addActionListener(e -> {
            new DashboardPage(username).setVisible(true);
            dispose();
        });
        add(backButton, BorderLayout.SOUTH);
    }

    private Object[][] fetchAppliedJobs() {
        Object[][] data = new Object[0][0];
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jobportal", "root", "yash03")) {
            System.out.println("Username used for query: " + username);

            String query = "SELECT a.job_id, j.job_name, a.application_date, j.company_name " +
                    "FROM applications a " +
                    "JOIN jobs j ON a.job_id = j.job_id " +
                    "WHERE a.username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);

                System.out.println("Executing query: " + query);
                System.out.println("With username: " + username);

                ResultSet rs = stmt.executeQuery();

                int rowCount = 0;
                while (rs.next()) {
                    rowCount++;
                }

                data = new Object[rowCount][4];
                int i = 0;
                rs = stmt.executeQuery();
                while (rs.next()) {
                    data[i][0] = rs.getInt("job_id");
                    data[i][1] = rs.getString("job_name");
                    data[i][2] = rs.getDate("application_date");
                    data[i][3] = rs.getString("company_name");
                    i++;
                }


                if (rowCount == 0) {
                    System.out.println("No data found for the user: " + username);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error fetching data from the database: " + ex.getMessage());
        }
        return data;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MyApplicationsPage("yash03").setVisible(true));
    }
}
