package main.java.gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.UUID;

/**
 * The {@code RegisterGUI} class creates a graphical user interface that allows new users
 * to register for the Fitness Tracker application. It handles user input for creating
 * a login and password, validates the input, and initiates the account creation process.
 * Upon successful registration, it directs the user to provide additional personal data.
 */
public class RegisterGUI {
    private static HashMap<String, String> loginData; // Stores existing user login credentials

    /**
     * Constructs the {@code RegisterGUI} and initializes the registration interface.
     * It sets up the GUI components, handles user interactions, and manages the registration logic.
     */
    public RegisterGUI() {
        // Load existing login data from the specified file
        loadLoginData("src/main/resources/usersLoginData.txt");

        // Create the main frame for the registration GUI
        JFrame frame = new JFrame("Fitness Tracker - Register");
        JPanel panel = new JPanel();

        // Set the layout manager to arrange components vertically
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create and configure the welcome label
        JLabel welcomeLabel = new JLabel("<html><div style='text-align: center;'>CREATE YOUR FITNESS TRACKER ACCOUNT</div></html>");
        welcomeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(113, 54, 143));

        // Create and configure the login label and text field
        JLabel userLabel = new JLabel("Create Login:");
        JTextField userField = new JTextField(20);
        userField.setFont(new Font("Comic Sans MS", Font.BOLD, 16));

        // Create and configure the password label and password field
        JLabel passLabel = new JLabel("Create Password:");
        JPasswordField passField = new JPasswordField(20);

        // Create and configure the register button
        JButton registerButton = new JButton("REGISTER");
        registerButton.setForeground(new Color(113, 54, 143));
        registerButton.setFont(new Font("Comic Sans MS", Font.BOLD, 14));

        // Create a label to display messages to the user
        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);

        // Create and configure the "Back to login" label with underlined text
        JLabel backToLoginLabel = new JLabel("<html><div style='text-align: center;'><u>Back to login</u></div></html>");
        backToLoginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        backToLoginLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        backToLoginLabel.setForeground(new Color(113, 54, 143));

        // Set fonts and colors for labels
        userLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        userLabel.setForeground(new Color(113, 54, 143));
        passLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        passLabel.setForeground(new Color(113, 54, 143));

        // Align all components to the center of the panel
        welcomeLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        userLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        userField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        passLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        passField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        registerButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        messageLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        backToLoginLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        // Set maximum size for text fields to ensure uniform appearance
        userField.setMaximumSize(new Dimension(200, 30));
        passField.setMaximumSize(new Dimension(200, 30));

        // Configure the panel's background color and border for aesthetics
        panel.setBackground(new Color(230, 220, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Add components to the panel with spacing using rigid areas
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
        panel.add(registerButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(backToLoginLabel);
        panel.add(Box.createVerticalGlue());

        // Add the panel to the frame's center region
        frame.add(panel, BorderLayout.CENTER);

        // Configure frame properties
        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Add action listener to handle register button clicks
        registerButton.addActionListener(e -> {
            String login = userField.getText(); // Retrieve the entered login
            String password = new String(passField.getPassword()); // Retrieve the entered password

            // Validate input fields
            if (login.isEmpty() || password.isEmpty()) {
                // Display error message if any field is empty
                messageLabel.setText("Fields cannot be empty.");
                messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
                messageLabel.setForeground(Color.RED);
            } else if (login.contains(";") || password.contains(";")) {
                // Prevent use of semicolon in login or password
                messageLabel.setText("Login or password cannot contain ';'.");
                messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
                messageLabel.setForeground(Color.RED);
            } else if (loginData.containsKey(login)) {
                // Check if the login already exists
                messageLabel.setText("Login already exists.");
                messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
                messageLabel.setForeground(Color.RED);
            } else if (login.contains(" ") || password.contains(" ")) {
                // Prevent use of spaces in login or password
                messageLabel.setText("Login or password cannot contain spaces.");
                messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
                messageLabel.setForeground(Color.RED);
            } else {
                // If all validations pass, proceed with registration
                frame.dispose(); // Close the current registration window
                new AskForDataGUI(login, password); // Open the data input GUI
            }
        });

        // Add mouse listener to change cursor when hovering over the register button
        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                registerButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Revert cursor
            }
        });

        // Add mouse listener to handle interactions with the "Back to login" label
        backToLoginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                frame.dispose(); // Close the current registration window
                new LoginGUI();  // Open the login GUI
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                backToLoginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                backToLoginLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Revert cursor
            }
        });
    }

    /**
     * Loads existing user login data from the specified file.
     * Each line in the file should follow the format: uniqueID;login;password
     *
     * @param filePath The path to the usersLoginData.txt file.
     */
    private void loadLoginData(String filePath) {
        loginData = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line and parse the login credentials
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    // parts[0] = uniqueID, parts[1] = login, parts[2] = password
                    loginData.put(parts[1], parts[2]);
                }
            }
        } catch (IOException e) {
            // Print error message if the file cannot be read
            System.err.println("Error reading the login file: " + e.getMessage());
        }
    }

    /**
     * Adds a new user to the usersLoginData.txt file and returns the generated UUID.
     *
     * @param login    The login username for the new user.
     * @param password The password for the new user.
     * @return A {@code String} representing the generated UUID for the new user.
     */
    public static String addNewUser(String login, String password) {
        String filePath = "src/main/resources/usersLoginData.txt";
        String uniqueID = UUID.randomUUID().toString(); // Generate a unique UUID

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            // Write the new user's data in the format: uniqueID;login;password
            writer.write(uniqueID + ";" + login + ";" + password);
            writer.newLine();
        } catch (IOException e) {
            // Print error message if the file cannot be written to
            System.err.println("Error writing to the login file: " + e.getMessage());
        }

        // Add the new user's login and password to the in-memory HashMap
        loginData.put(login, password);

        // Return the generated UUID
        return uniqueID;
    }
}
