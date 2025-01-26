package main.java.gui;

import main.java.gui.coachgui.CoachGUI;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code LoginGUI} class creates a graphical user interface for users and coaches to log into the Fitness Tracker application.
 * It handles the authentication process by validating user and coach credentials against stored data.
 * Upon successful login, it redirects users to the main application or coaches to their respective dashboards.
 */
public class LoginGUI {
    public static String loggedInUserUUID = null; // Stores the UUID of the logged-in user
    public static String loggedInCoachID = null;  // Stores the ID of the logged-in coach

    private Map<String, UserCredential> loginData; // Stores user login credentials
    private Map<String, String> coachData;         // Stores coach login credentials

    /**
     * Constructs the {@code LoginGUI} and initializes the login interface.
     *
     * @param login    The login username of the user.
     * @param password The password of the user.
     */
    public LoginGUI() {
        // Load user and coach login data from respective files
        loadLoginData("src/main/resources/usersLoginData.txt");
        loadCoachData("src/main/resources/coachesLogin.txt");

        // Create the main frame for the login GUI
        JFrame frame = new JFrame("Fitness Tracker - Login");
        JPanel panel = new JPanel();

        // Set layout manager for the panel
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create and configure the welcome label
        JLabel welcomeLabel = new JLabel("<html><div style='text-align: center;'>WELCOME TO FITNESS TRACKER</div></html>");
        welcomeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(113, 54, 143));

        // Create and configure login fields
        JLabel userLabel = new JLabel("Login:");
        JTextField userField = new JTextField(20);
        userField.setFont(new Font("Comic Sans MS", Font.BOLD, 16));

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);

        // Create and configure the login button
        JButton loginButton = new JButton("LOGIN");
        loginButton.setForeground(new Color(113, 54, 143));
        loginButton.setFont(new Font("Comic Sans MS", Font.BOLD, 14));

        // Create a label to display messages to the user
        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);

        // Configure fonts and colors for labels
        userLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        userLabel.setForeground(new Color(113, 54, 143));
        passLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        passLabel.setForeground(new Color(113, 54, 143));

        // Create and configure the register label with underlined text
        JLabel registerLabel = new JLabel("<html><div style='text-align: center;'>"
                + "<u>Don't have an account? Register here</u></div></html>");
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        registerLabel.setForeground(new Color(113, 54, 143));

        // Align all components to the center of the panel
        welcomeLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        userLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        userField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        passLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        passField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        messageLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        registerLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        // Set maximum size for text fields to ensure uniform appearance
        userField.setMaximumSize(new Dimension(200, 30));
        passField.setMaximumSize(new Dimension(200, 30));

        // Configure the panel's background color and border
        panel.setBackground(new Color(230, 220, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Add components to the panel with spacing
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

        // Add the panel to the frame
        frame.add(panel, BorderLayout.CENTER);

        // Configure frame properties
        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Add action listener to the login button
        loginButton.addActionListener(e -> {
            String login = userField.getText();
            String password = new String(passField.getPassword());

            // Validate coach login first
            if (validateCoachLogin(login, password)) {
                // If coach login is successful
                messageLabel.setText("Coach login successful!");
                messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
                messageLabel.setForeground(Color.GREEN);

                loggedInCoachID = login; // Store the logged-in coach ID
                frame.dispose();         // Close the current frame
                new CoachGUI();          // Open the coach's GUI
            } else if (validateUserLogin(login, password)) {
                // If user login is successful
                messageLabel.setText("Login successful!");
                messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
                messageLabel.setForeground(Color.GREEN);

                loggedInUserUUID = loginData.get(login).uuid; // Store the user's UUID
                frame.dispose();                            // Close the current frame
                new MainGUI();                             // Open the main application GUI
            } else {
                // If login credentials are invalid
                messageLabel.setText("Invalid login or password.");
                messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
                messageLabel.setForeground(Color.RED);
            }
        });

        // Add mouse listener to the register label to handle clicks and cursor changes
        registerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                frame.dispose();   // Close the current frame
                new RegisterGUI(); // Open the registration GUI
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                registerLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Revert cursor
            }
        });
    }

    /**
     * Loads user login data from the specified file.
     *
     * @param filePath The path to the usersLoginData.txt file.
     */
    private void loadLoginData(String filePath) {
        loginData = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line and parse user credentials
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
     * Loads coach login data from the specified file.
     *
     * @param filePath The path to the coachesLogin.txt file.
     */
    private void loadCoachData(String filePath) {
        coachData = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line and parse coach credentials
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
     * Validates user login credentials.
     *
     * @param login    The login username entered by the user.
     * @param password The password entered by the user.
     * @return {@code true} if the credentials are valid; {@code false} otherwise.
     */
    private boolean validateUserLogin(String login, String password) {
        if (!loginData.containsKey(login)) {
            return false;
        }
        UserCredential cred = loginData.get(login);
        return cred.password.equals(password);
    }

    /**
     * Validates coach login credentials.
     *
     * @param login    The login username entered by the coach.
     * @param password The password entered by the coach.
     * @return {@code true} if the credentials are valid; {@code false} otherwise.
     */
    private boolean validateCoachLogin(String login, String password) {
        return coachData.containsKey(login) && coachData.get(login).equals(password);
    }

    /**
     * The {@code UserCredential} class represents a user's login credentials, including their UUID and password.
     */
    private static class UserCredential {
        String uuid;
        String password;

        /**
         * Constructs a {@code UserCredential} with the specified UUID and password.
         *
         * @param uuid     The UUID of the user.
         * @param password The password of the user.
         */
        public UserCredential(String uuid, String password) {
            this.uuid = uuid;
            this.password = password;
        }
    }
}
