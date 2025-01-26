package main.java.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code OurCoachesGUI} class creates a graphical user interface that displays information
 * about the coaches available in the Fitness Tracker application. It provides options for users
 * to request training sessions with the coaches. The class handles user interactions and manages
 * the logic for sending training session requests while ensuring users are not already students
 * of the selected coach.
 */
public class OurCoachesGUI {

    /**
     * Constructs the {@code OurCoachesGUI} and initializes the user interface components.
     * It loads the logged-in user's data, sets up the main frame, and configures navigation buttons
     * and coach profiles.
     */
    public OurCoachesGUI() {
        // Retrieve the UUID of the currently logged-in user from LoginGUI
        String currentUserUUID = LoginGUI.loggedInUserUUID;

        // Create the main application frame
        JFrame frame = new JFrame("Fitness Tracker - Our Coaches");
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

        // ----------------
        // PROFILE MENU (Popup Menu for PROFILE Button)
        // ----------------
        JPopupMenu profileMenu = new JPopupMenu();

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
                new OurCoachesGUI();      // Reload the OurCoachesGUI
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
        // Center Panel (Coaches Display)
        // ----------------
        JPanel centerPanel = new JPanel(new GridLayout(1, 2)); // Two columns: Left for José, Right for Jorge
        centerPanel.setBackground(new Color(230, 220, 250));

        // Left Panel - José Alcántar
        JPanel leftPanel = createCoachPanel(
                "José Alcántar",
                "src/main/resources/josee.jpg",
                "c1",
                "Jose knows exactly what he does. He coaches people for 15 years now and is a professional team sports competitor!",
                currentUserUUID,
                frame
        );

        // Right Panel - Jorge Echevarría
        JPanel rightPanel = createCoachPanel(
                "Jorge Echevarría",
                "src/main/resources/jorge.jpeg",
                "c2",
                "Jorge is passionate about fitness and well-being. He specializes in strength training and personalized coaching plans.",
                currentUserUUID,
                frame
        );

        // Add both coach panels to the center panel
        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);

        // Add the center panel to the main frame
        frame.add(centerPanel, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);
    }

    /**
     * Creates a JPanel representing a coach's profile, including their image, name,
     * description, and a button to request a training session.
     *
     * @param coachName          The name of the coach.
     * @param imagePath          The file path to the coach's image.
     * @param coachID            The unique identifier for the coach.
     * @param coachDescription   A brief description of the coach.
     * @param currentUserUUID    The UUID of the currently logged-in user.
     * @param frame              The main application frame.
     * @return A configured JPanel representing the coach's profile.
     */
    private JPanel createCoachPanel(String coachName, String imagePath, String coachID,
                                    String coachDescription, String currentUserUUID, JFrame frame) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(230, 220, 250)); // Light purple background
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical layout
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the panel

        // Create and configure the coach's image label
        JLabel coachImage = createScaledImageLabel(imagePath, 250, 250);
        coachImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create and configure the coach's name label with HTML formatting for styling
        JLabel coachLabel = new JLabel("<html><div style='text-align: center;'>"
                + "<h2 style='font-family: Comic Sans MS; font-size: 22px; color: #71368F;'>"
                + coachName
                + "</h2></div></html>", SwingConstants.CENTER);
        coachLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create and configure the coach's description label with HTML formatting
        JLabel coachDescriptionLabel = new JLabel("<html><div style='text-align: center;'>"
                + "<p style='font-family: Comic Sans MS; color: #5D3B90;'>"
                + coachDescription
                + "</p></div></html>", SwingConstants.CENTER);
        coachDescriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create and configure the request training session button
        JButton requestButton = new JButton("Request Training Session");
        requestButton.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        requestButton.setForeground(new Color(113, 54, 143));
        requestButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add an action listener to handle button clicks
        requestButton.addActionListener(e -> handleRequestClick(coachID, currentUserUUID, frame));

        // Add components to the coach panel with spacing
        panel.add(Box.createVerticalGlue());
        panel.add(coachImage);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(coachLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(coachDescriptionLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(requestButton);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    /**
     * Handles the logic when the "Request Training Session" button is clicked.
     * It ensures that the user is not already a student of the coach and that
     * the user has not previously sent a request to the same coach. If validations
     * pass, it records the request in the appropriate file.
     *
     * @param coachID           The unique identifier of the coach.
     * @param currentUserUUID   The UUID of the currently logged-in user.
     * @param frame             The main application frame.
     */
    private void handleRequestClick(String coachID, String currentUserUUID, JFrame frame) {
        // File path for accepted coach-student relationships
        String acceptedFilePath = "src/main/resources/coachesStudents.txt";
        boolean userIsAlreadyStudent = false;

        // Check if the user is already a student of the coach
        try (BufferedReader br = new BufferedReader(new FileReader(acceptedFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(coachID + ";")) {
                    if (line.contains(currentUserUUID)) {
                        userIsAlreadyStudent = true;
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // File does not exist – assume no students yet
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading accepted file: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        if (userIsAlreadyStudent) {
            // Inform the user that they are already a student of this coach
            JOptionPane.showMessageDialog(frame,
                    "You are already a student of this coach – you cannot request again!");
            return;
        }

        // File path for pending coach training session requests
        String requestsFilePath = "src/main/resources/coachesRequests.txt";
        List<String> lines = new ArrayList<>();
        boolean userAlreadyRequested = false;

        // Read existing requests to check if the user has already sent a request
        try (BufferedReader br = new BufferedReader(new FileReader(requestsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(coachID + ";")) {
                    if (line.contains(currentUserUUID)) {
                        userAlreadyRequested = true;
                    } else {
                        line += ";" + currentUserUUID; // Append the user's UUID to the existing line
                    }
                }
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            // If the file does not exist, it will be created later
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading requests file: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        if (userAlreadyRequested) {
            // Inform the user that they have already sent a request to this coach
            JOptionPane.showMessageDialog(frame,
                    "You have already sent a request to this coach!");
            return;
        }

        // Check if there is an existing line for the coach in the requests file
        boolean coachLineFound = false;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.startsWith(coachID + ";")) {
                coachLineFound = true;
                lines.set(i, line); // Already appended the user's UUID if not present
                break;
            }
        }
        if (!coachLineFound) {
            // If no existing line for the coach, add a new line with the user's UUID
            lines.add(coachID + ";" + currentUserUUID);
        }

        // Write the updated requests back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(requestsFilePath))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error writing requests file: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        // Inform the user that the request has been sent successfully
        JOptionPane.showMessageDialog(frame,
                "Request sent to the coach successfully!");
    }

    /**
     * Creates a JLabel containing a scaled image from the specified file path.
     *
     * @param imagePath The file path to the image.
     * @param width     The desired width of the image.
     * @param height    The desired height of the image.
     * @return A JLabel containing the scaled image.
     */
    private JLabel createScaledImageLabel(String imagePath, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(imagePath); // Load the original image
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH); // Scale the image smoothly
        return new JLabel(new ImageIcon(scaledImage)); // Return the scaled image in a JLabel
    }
}
