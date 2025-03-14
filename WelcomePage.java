package JobPortal;
import javax.swing.*;
import java.awt.*;
public class WelcomePage extends JFrame {
    public WelcomePage() {
        setTitle("Welcome to the Job Portal");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(232, 232, 232));

        JLabel titleLabel = new JLabel("Welcome to the Job Portal", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(128, 0, 0));
        add(titleLabel, BorderLayout.NORTH);

        JTextArea welcomeMessage = new JTextArea("Find your dream job easily with our user-friendly portal.\n"
                + "Browse job listings, view detailed information, and apply for jobs that match your skills!");
        welcomeMessage.setFont(new Font("Arial", Font.PLAIN, 18));
        welcomeMessage.setForeground(new Color(128, 0, 0));
        welcomeMessage.setBackground(new Color(232, 232, 232));
        welcomeMessage.setWrapStyleWord(true);
        welcomeMessage.setLineWrap(true);
        welcomeMessage.setEditable(false);
        welcomeMessage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(welcomeMessage, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(new Color(232, 232, 232)); // Soft light gray background

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(249, 243, 227)); // Light blue for button
        loginButton.setForeground(new Color(128, 0, 0)); // Deep maroon text
        loginButton.setFont(new Font("Arial", Font.PLAIN, 18));
        loginButton.addActionListener(e -> {
            new LoginPage().setVisible(true);
            dispose();
        });
        JButton signupButton = new JButton("Sign Up");
        signupButton.setBackground(new Color(249, 243, 227));
        signupButton.setForeground(new Color(128, 0, 0));
        signupButton.setFont(new Font("Arial", Font.PLAIN, 18));
        signupButton.addActionListener(e -> {
            new SignupPage().setVisible(true);
            dispose();
        });
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
    public static void main(String[] args) {
        new WelcomePage();
    }
}
