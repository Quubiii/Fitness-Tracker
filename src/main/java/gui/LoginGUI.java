package main.java.gui;

import main.java.gui.coachgui.CoachGUI;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LoginGUI {
    public static String loggedInUserUUID = null; // For users
    public static String loggedInCoachID = null; // For coaches

    private Map<String, UserCredential> loginData; // User credentials
    private Map<String, String> coachData;        // Coach credentials

    public LoginGUI() {
        loadLoginData("src/main/resources/usersLoginData.txt");
        loadCoachData("src/main/resources/coachesLogin.txt");

        JFrame frame = new JFrame("Fitness Tracker - Login");
        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("<html><div style='text-align: center;'>WELCOME TO FITNESS TRACKER</div></html>");
        welcomeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(113, 54, 143));

        JLabel userLabel = new JLabel("Login:");
        JTextField userField = new JTextField(20);
        userField.setFont(new Font("Comic Sans MS", Font.BOLD, 16));

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setForeground(new Color(113, 54, 143));
        loginButton.setFont(new Font("Comic Sans MS", Font.BOLD, 14));

        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);

        userLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        userLabel.setForeground(new Color(113, 54, 143));
        passLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        passLabel.setForeground(new Color(113, 54, 143));

        JLabel registerLabel = new JLabel("<html><div style='text-align: center;'>"
                + "<u>Don't have an account? Register here</u></div></html>");
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        registerLabel.setForeground(new Color(113, 54, 143));

        // Align elements
        welcomeLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        userLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        userField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        passLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        passField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        messageLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        registerLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        userField.setMaximumSize(new Dimension(200, 30));
        passField.setMaximumSize(new Dimension(200, 30));

        panel.setBackground(new Color(230, 220, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.add(Box.createVerticalGlue());
        panel.add(welcomeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(userLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(userField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(passLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(passField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(messageLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(loginButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(registerLabel);
        panel.add(Box.createVerticalGlue());

        frame.add(panel, BorderLayout.CENTER);

        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Login button action listener
        loginButton.addActionListener(e -> {
            String login = userField.getText();
            String password = new String(passField.getPassword());

            if (validateCoachLogin(login, password)) {
                // If coach login is valid
                messageLabel.setText("Coach login successful!");
                messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
                messageLabel.setForeground(Color.GREEN);

                loggedInCoachID = login; // Store logged-in coach ID
                frame.dispose();
                new CoachGUI();
            } else if (validateUserLogin(login, password)) {
                // If user login is valid
                messageLabel.setText("Login successful!");
                messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
                messageLabel.setForeground(Color.GREEN);

                loggedInUserUUID = loginData.get(login).uuid; // Store user UUID
                frame.dispose();
                new MainGUI();
            } else {
                // Invalid credentials
                messageLabel.setText("Invalid login or password.");
                messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
                messageLabel.setForeground(Color.RED);
            }
        });

        // Register label action listener
        registerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                frame.dispose();
                new RegisterGUI();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                registerLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    /**
     * Load user login data from file.
     */
    private void loadLoginData(String filePath) {
        loginData = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String uuid = parts[0];
                    String login = parts[1];
                    String password = parts[2];
                    loginData.put(login, new UserCredential(uuid, password));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the user login file: " + e.getMessage());
        }
    }

    /**
     * Load coach login data from file.
     */
    private void loadCoachData(String filePath) {
        coachData = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String login = parts[0];
                    String password = parts[1];
                    coachData.put(login, password);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the coach login file: " + e.getMessage());
        }
    }

    /**
     * Validate user login.
     */
    private boolean validateUserLogin(String login, String password) {
        if (!loginData.containsKey(login)) {
            return false;
        }
        UserCredential cred = loginData.get(login);
        return cred.password.equals(password);
    }

    /**
     * Validate coach login.
     */
    private boolean validateCoachLogin(String login, String password) {
        return coachData.containsKey(login) && coachData.get(login).equals(password);
    }

    /**
     * Simple data model for user credentials.
     */
    private static class UserCredential {
        String uuid;
        String password;

        public UserCredential(String uuid, String password) {
            this.uuid = uuid;
            this.password = password;
        }
    }
}
