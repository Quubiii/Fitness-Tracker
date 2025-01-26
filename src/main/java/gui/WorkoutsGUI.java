package main.java.gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.CategoryPlot;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The {@code WorkoutsGUI} class provides a graphical user interface for users to view available workout types
 * and register their workouts within the Fitness Tracker application. It displays images representing different
 * exercises and includes navigation buttons to other sections of the application. Users can register their workouts
 * by clicking the "REGISTER MY WORKOUT" button, which directs them to the {@code RegisterTrainingGUI}.
 */
public class WorkoutsGUI {

    /**
     * Constructs the {@code WorkoutsGUI} and initializes the workout interface.
     * It loads the user's weight data, sets up the main frame, configures navigation buttons,
     * and displays images representing different workout types.
     */
    public WorkoutsGUI() {
        String loggedInUUID = LoginGUI.loggedInUserUUID; // Retrieve the UUID of the currently logged-in user
        JFrame frame = new JFrame("Fitness Tracker - Workouts"); // Create the main application frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose frame on close
        frame.setSize(1000, 600); // Set frame size
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // ----------------
        // TOP PANEL (Navigation)
        // ----------------
        JPanel topPanel = new JPanel(); // Create the top navigation panel
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Set layout to FlowLayout aligned to the left
        Color darkPurple = new Color(113, 54, 143); // Define a dark purple color for styling
        topPanel.setBackground(darkPurple); // Set the background color of the top panel

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

        // ----------------
        // PROFILE MENU (Popup Menu for PROFILE Button)
        // ----------------
        JPopupMenu profileMenu = new JPopupMenu(); // Create a popup menu for the PROFILE button

        // Create menu items for the profile menu
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
            new WeightHistoryGUI();   // Reload the WeightHistoryGUI
        });

        logOut.addActionListener(e -> {
            frame.dispose();    // Close the current frame
            new LoginGUI();     // Open the LoginGUI
        });

        // Show the profile menu when the "PROFILE" button is hovered over
        profileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                profileMenu.show(profileButton, 0, profileButton.getHeight()); // Display the popup menu below the button
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
                mainSiteButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand icon on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mainSiteButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Revert cursor when not hovering
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
                coachesButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand icon on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                coachesButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Revert cursor when not hovering
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
                workoutsButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand icon on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                workoutsButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Revert cursor when not hovering
            }
        });

        // Add the top navigation panel to the frame
        frame.add(topPanel, BorderLayout.NORTH);

        // ----------------
        // CENTER PANEL (Workout Types Display)
        // ----------------
        JPanel centerPanel = new JPanel(); // Create the center panel to hold workout type information
        centerPanel.setBackground(new Color(230, 220, 250)); // Set background color to light purple
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS)); // Set layout to vertical BoxLayout
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the panel

        // Create and configure the title label
        JLabel titleLabel = new JLabel("<html><div style='text-align: center;'>WITH OUR APP, YOU CAN CURRENTLY TRACK 3 TYPES OF EXERCISES!</div></html>");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        titleLabel.setForeground(darkPurple);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment

        // Load and scale images for different workout types
        ImageIcon runningIcon = new ImageIcon("src/main/resources/running.png"); // Load running image
        ImageIcon scaledRunningIcon = new ImageIcon(runningIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH)); // Scale the running image

        ImageIcon cyclingIcon = new ImageIcon("src/main/resources/cycling.png"); // Load cycling image
        ImageIcon scaledCyclingIcon = new ImageIcon(cyclingIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH)); // Scale the cycling image

        ImageIcon ropeJumpingIcon = new ImageIcon("src/main/resources/rope_jumping.png"); // Load rope jumping image
        ImageIcon scaledRopeJumpingIcon = new ImageIcon(ropeJumpingIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH)); // Scale the rope jumping image

        // Create and configure labels with images and text for each workout type
        JLabel runningLabel = new JLabel("Running");
        runningLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        runningLabel.setForeground(darkPurple);
        runningLabel.setHorizontalTextPosition(SwingConstants.CENTER); // Center text horizontally
        runningLabel.setVerticalTextPosition(SwingConstants.BOTTOM);   // Position text below the image
        runningLabel.setIcon(scaledRunningIcon); // Set the scaled running image as the icon

        JLabel cyclingLabel = new JLabel("Cycling");
        cyclingLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        cyclingLabel.setForeground(darkPurple);
        cyclingLabel.setHorizontalTextPosition(SwingConstants.CENTER); // Center text horizontally
        cyclingLabel.setVerticalTextPosition(SwingConstants.BOTTOM);   // Position text below the image
        cyclingLabel.setIcon(scaledCyclingIcon); // Set the scaled cycling image as the icon

        JLabel ropeJumpingLabel = new JLabel("Rope Jumping");
        ropeJumpingLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        ropeJumpingLabel.setForeground(darkPurple);
        ropeJumpingLabel.setHorizontalTextPosition(SwingConstants.CENTER); // Center text horizontally
        ropeJumpingLabel.setVerticalTextPosition(SwingConstants.BOTTOM);   // Position text below the image
        ropeJumpingLabel.setIcon(scaledRopeJumpingIcon); // Set the scaled rope jumping image as the icon

        // Create a panel to hold the workout type images and labels
        JPanel imagesPanel = new JPanel();
        imagesPanel.setBackground(new Color(230, 220, 250)); // Match the background color
        imagesPanel.setLayout(new GridLayout(1, 3, 20, 20)); // 1 row, 3 columns with 20px gaps
        imagesPanel.add(runningLabel);        // Add running label to the panel
        imagesPanel.add(cyclingLabel);        // Add cycling label to the panel
        imagesPanel.add(ropeJumpingLabel);    // Add rope jumping label to the panel

        // Create and configure the "REGISTER MY WORKOUT" button
        JButton registerButton = new JButton("REGISTER MY WORKOUT");
        registerButton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        registerButton.setForeground(darkPurple);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment

        // Add mouse listeners to change the cursor and handle button clicks
        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand icon on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                registerButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Revert cursor when not hovering
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                frame.dispose(); // Close the current frame
                new RegisterTrainingGUI(getUserWeight(loggedInUUID)); // Open the RegisterTrainingGUI with the user's weight
            }
        });

        // Add components to the center panel with spacing
        centerPanel.add(titleLabel); // Add the title label
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add vertical spacing
        centerPanel.add(imagesPanel); // Add the images panel
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add vertical spacing
        centerPanel.add(registerButton); // Add the register button

        // Set the frame's layout and add panels
        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);    // Add the top navigation panel
        frame.add(centerPanel, BorderLayout.CENTER); // Add the center panel with workout types

        frame.setVisible(true); // Make the frame visible
    }

    /**
     * Retrieves the user's weight from the usersData.txt file based on their UUID.
     *
     * @param uuid The UUID of the currently logged-in user.
     * @return The weight of the user as a {@code double}. Returns 0.0 if not found or on error.
     */
    private double getUserWeight(String uuid) {
        String filePath = "src/main/resources/usersData.txt"; // Path to the user data file

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line to find the matching UUID
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 3 && parts[0].equals(uuid)) {
                    return Double.parseDouble(parts[2]); // Weight is the third element
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the user data file: " + e.getMessage()); // Print error if file reading fails
        } catch (NumberFormatException e) {
            System.err.println("Invalid weight format in file."); // Print error if weight parsing fails
        }

        // Default value if weight is not found
        return 0.0;
    }
}
