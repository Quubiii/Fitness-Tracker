package main.java.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The {@code MainGUI} class creates the main user interface for the Fitness Tracker application.
 * It displays a welcome message to the logged-in user and provides navigation to other sections
 * such as Profile, Our Coaches, and Workouts. The class also calculates and displays the duration
 * since the user's account creation.
 */
public class MainGUI {

    private String userName;               // Stores the name of the logged-in user
    private String dateOfAccountCreation;  // Stores the account creation date of the user

    /**
     * Constructs the {@code MainGUI} and initializes the main interface.
     * It loads the logged-in user's data, sets up the main frame, and configures navigation buttons.
     */
    public MainGUI() {
        // Load data for the currently logged-in user using their UUID from LoginGUI
        String currentUserUUID = LoginGUI.loggedInUserUUID;
        loadUserDataFromFile("src/main/resources/usersData.txt", currentUserUUID);

        // Create the main application frame
        JFrame frame = new JFrame("Fitness Tracker - Main");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setLayout(new BorderLayout());

        // ----------------
        // TOP PANEL (Navigation)
        // ----------------
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        Color darkPurple = new Color(113, 54, 143); // Define a dark purple color for styling
        topPanel.setBackground(darkPurple);

        // Create navigation buttons
        JButton mainSiteButton = new JButton("MAIN");
        JButton profileButton = new JButton("PROFILE");
        JButton coachesButton = new JButton("OUR COACHES");
        JButton workoutsButton = new JButton("WORKOUTS");

        // Configure button fonts and colors
        mainSiteButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        mainSiteButton.setForeground(darkPurple);
        profileButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        profileButton.setForeground(darkPurple);
        coachesButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        coachesButton.setForeground(darkPurple);
        workoutsButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        workoutsButton.setForeground(darkPurple);

        // Add buttons to the top navigation panel
        topPanel.add(mainSiteButton);
        topPanel.add(profileButton);
        topPanel.add(coachesButton);
        topPanel.add(workoutsButton);

        // Create a popup menu for the "PROFILE" button
        JPopupMenu profileMenu = new JPopupMenu();
        JMenuItem aboutMe = new JMenuItem("About Me");
        aboutMe.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        aboutMe.setBackground(new Color(230, 220, 250));
        aboutMe.setForeground(darkPurple);

        JMenuItem workoutStats = new JMenuItem("Workout Stats");
        workoutStats.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        workoutStats.setBackground(new Color(230, 220, 250));
        workoutStats.setForeground(darkPurple);

        JMenuItem weightHistory = new JMenuItem("Weight History");
        weightHistory.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        weightHistory.setBackground(new Color(230, 220, 250));
        weightHistory.setForeground(darkPurple);

        JMenuItem logOut = new JMenuItem("Log Out");
        logOut.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        logOut.setBackground(new Color(230, 220, 250));
        logOut.setForeground(darkPurple);

        // Add menu items to the profile menu
        profileMenu.add(aboutMe);
        profileMenu.add(workoutStats);
        profileMenu.add(weightHistory);
        profileMenu.add(logOut);

        // Action listeners for profile menu items
        aboutMe.addActionListener(e -> {
            frame.dispose();    // Close the current frame
            new AboutMeGUI();   // Open the AboutMeGUI
        });

        workoutStats.addActionListener(e -> {
            frame.dispose();        // Close the current frame
            new WorkoutStatsGUI();  // Open the WorkoutStatsGUI
        });

        weightHistory.addActionListener(e -> {
            frame.dispose();          // Close the current frame
            new WeightHistoryGUI();   // Open the WeightHistoryGUI
        });

        logOut.addActionListener(e -> {
            frame.dispose();    // Close the current frame
            new LoginGUI();     // Open the LoginGUI
        });

        // Show the profile menu when the "PROFILE" button is hovered over
        profileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                profileMenu.show(profileButton, 0, profileButton.getHeight());
            }
        });

        // ----------------
        // Navigation Button Listeners
        // ----------------

        // MAIN button actions
        mainSiteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();    // Close the current frame
                new MainGUI();      // Reload the MainGUI
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mainSiteButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mainSiteButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Revert cursor
            }
        });

        // OUR COACHES button actions
        coachesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();          // Close the current frame
                new OurCoachesGUI();      // Open the OurCoachesGUI
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                coachesButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand
            }

            @Override
            public void mouseExited(MouseEvent e) {
                coachesButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Revert cursor
            }
        });

        // WORKOUTS button actions
        workoutsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();      // Close the current frame
                new WorkoutsGUI();    // Open the WorkoutsGUI
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                workoutsButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand
            }

            @Override
            public void mouseExited(MouseEvent e) {
                workoutsButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Revert cursor
            }
        });

        // Add the top navigation panel to the frame
        frame.add(topPanel, BorderLayout.NORTH);

        // ----------------
        // Center Panel (Welcome Message)
        // ----------------
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(230, 220, 250)); // Light purple background

        // Placeholder texts if user data is missing
        String nameText = (userName != null && !userName.isEmpty()) ? userName : "Guest";
        String dateText = (dateOfAccountCreation != null && !dateOfAccountCreation.isEmpty())
                ? dateOfAccountCreation
                : "some time";

        // Calculate the duration since account creation
        String whichIsLine = "";  // To hold the duration string
        try {
            if (dateOfAccountCreation != null && !dateOfAccountCreation.isEmpty()) {
                // Assume the date format is "yyyy-MM-dd HH:mm:ss"
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime creationDateTime = LocalDateTime.parse(dateOfAccountCreation, formatter);
                LocalDateTime now = LocalDateTime.now();

                // Calculate the difference
                Duration diff = Duration.between(creationDateTime, now);
                long totalMinutes = diff.toMinutes();
                long days = totalMinutes / (24 * 60);
                long hours = (totalMinutes % (24 * 60)) / 60;
                long minutes = totalMinutes % 60;

                // Build the duration string
                whichIsLine = "<br>Which is " + days + " day(s) " + hours + " hour(s) " + minutes + " minute(s)";
            }
        } catch (Exception ex) {
            // Handle parsing errors
            System.err.println("Cannot parse dateOfAccountCreation: " + dateOfAccountCreation);
        }

        // HTML formatted welcome message
        String welcomeHtml = "<html>"
                + "<div style='text-align: center;'>"
                + "<h2 style='font-family: Comic Sans MS; font-size: 40px; color: #71368F;'>"
                + "Welcome back <b>" + nameText + "</b>!<br>"
                + "</h2>"
                + "<h2 style='font-family: Comic Sans MS; font-size: 26px; color: #71368F;'>"
                + "You are using our app since <b>" + dateText
                + whichIsLine
                + " ALREADY! \uD83D\uDE31💪🔥"
                + "<br>Look at THAT determination gurll! 😎👏"
                + "<br><br>"
                + "Remember, every time is a great time to exercise<br>~As someone wise probably said once...<br>SLAAY ;* 💃🕺"
                + "</h2>"
                + "</div>"
                + "</html>";

        // Create and configure the welcome label
        JLabel welcomeLabel = new JLabel(welcomeHtml, SwingConstants.CENTER);

        // Add the welcome label to the center panel
        centerPanel.add(welcomeLabel, BorderLayout.CENTER);

        // ----------------
        // Add panels to the main frame
        // ----------------
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);
    }

    /**
     * Loads the logged-in user's data from the specified file based on their UUID.
     *
     * @param filePath The path to the usersData.txt file.
     * @param userUUID The UUID of the logged-in user.
     */
    private void loadUserDataFromFile(String filePath, String userUUID) {
        // If there is no logged-in user, skip loading data
        if (userUUID == null) {
            return;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("File " + filePath + " does not exist!");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            // Read each line and parse user data
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) continue; // Skip empty lines
                String[] parts = line.split(";");
                if (parts.length < 8) continue; // Ensure the line has enough parts

                String uuidFromFile = parts[0];
                if (uuidFromFile.equals(userUUID)) {
                    this.userName = parts[1];
                    // parts[2] = weight
                    // parts[3] = height
                    // parts[4] = age
                    // parts[5] = gender
                    this.dateOfAccountCreation = parts[6];
                    // parts[7] = dateOfCurrentWeight
                    break; // Exit loop after finding the user
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if an IO error occurs
        }
    }
}
