package main.java.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * The {@code AboutMeGUI} class creates a graphical user interface
 * that displays the user's personal information and allows editing
 * of certain details. It includes a BMI indicator and navigation
 * to other sections of the application.
 */
public class AboutMeGUI {

    private final BMIIndicator bmiIndicator;
    public final JLabel bmiLabel;
    public final JLabel bmiCategoryLabel;

    private String userUUID;
    public String userName;
    public double userWeight;
    public double userHeight;
    public int userAge;
    public String userGender;
    public String dateOfAccountCreation;
    public String dateOfCurrentWeight;

    /**
     * Constructs the {@code AboutMeGUI} and initializes the GUI components.
     * It loads user data, sets up the main frame, and configures all panels and actions.
     */
    public AboutMeGUI() {
        // Retrieve the UUID of the logged-in user
        userUUID = LoginGUI.loggedInUserUUID;

        // Load user data from the specified file
        loadUserDataFromFile("src/main/resources/usersData.txt");

        // Create the main application frame
        JFrame frame = new JFrame("Fitness Tracker - About Me");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setLayout(new BorderLayout());

        // Top Panel with navigation buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        Color darkPurple = new Color(113, 54, 143);
        topPanel.setBackground(darkPurple);

        // Navigation buttons
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

        // Add buttons to the top panel
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

        // Action listeners for menu items
        aboutMe.addActionListener(e -> {
            frame.dispose();
            new AboutMeGUI();
        });

        workoutStats.addActionListener(e -> {
            frame.dispose();
            new WorkoutStatsGUI();
        });

        weightHistory.addActionListener(e -> {
            frame.dispose();
            new WeightHistoryGUI();
        });

        logOut.addActionListener(e -> {
            frame.dispose();
            new LoginGUI();
        });

        // Show the profile menu when the "PROFILE" button is hovered over
        profileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                profileMenu.show(profileButton, 0, profileButton.getHeight());
            }
        });

        // Mouse listeners for the "MAIN" button
        mainSiteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new MainGUI();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mainSiteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mainSiteButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        // Mouse listeners for the "OUR COACHES" button
        coachesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Go to Our Coaches...");
                frame.dispose();
                new OurCoachesGUI();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                coachesButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                coachesButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        // Mouse listeners for the "WORKOUTS" button
        workoutsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new WorkoutsGUI();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                workoutsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                workoutsButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        // Add the top panel to the frame
        frame.add(topPanel, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(230, 220, 250));

        // Left Panel with user data
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(230, 220, 250));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Heading with user's name
        String headingText = "Hey " + (userName != null ? userName : "") + "! Your current information:";
        JLabel yourDataLabel = new JLabel(headingText);
        yourDataLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        Color dataColor = new Color(113, 54, 143);
        yourDataLabel.setForeground(dataColor);

        leftPanel.add(yourDataLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Labels displaying user information
        JLabel uuidLabel = new JLabel("UUID: " + (userUUID != null ? userUUID : "-"));
        JLabel nameLabel = new JLabel("Name: " + (userName != null ? userName : "-"));
        JLabel weightLabel = new JLabel("Weight: " + userWeight);
        JLabel heightLabel = new JLabel("Height: " + userHeight);
        JLabel ageLabel = new JLabel("Age: " + userAge);
        JLabel genderLabel = new JLabel("Gender: " + (userGender != null ? userGender : "-"));
        JLabel accountDateLabel = new JLabel("Date of account creation: "
                + (dateOfAccountCreation != null ? dateOfAccountCreation : "-"));
        JLabel currentWeightDateLabel = new JLabel("Date of current weight: "
                + (dateOfCurrentWeight != null ? dateOfCurrentWeight : "-"));

        // Configure label fonts and colors
        Font dataFont = new Font("Comic Sans MS", Font.BOLD, 20);

        uuidLabel.setFont(dataFont);
        nameLabel.setFont(dataFont);
        weightLabel.setFont(dataFont);
        heightLabel.setFont(dataFont);
        ageLabel.setFont(dataFont);
        genderLabel.setFont(dataFont);
        accountDateLabel.setFont(dataFont);
        currentWeightDateLabel.setFont(dataFont);

        uuidLabel.setForeground(dataColor);
        nameLabel.setForeground(dataColor);
        weightLabel.setForeground(dataColor);
        heightLabel.setForeground(dataColor);
        ageLabel.setForeground(dataColor);
        genderLabel.setForeground(dataColor);
        accountDateLabel.setForeground(dataColor);
        currentWeightDateLabel.setForeground(dataColor);

        // Add labels to the left panel
        leftPanel.add(uuidLabel);
        leftPanel.add(nameLabel);
        leftPanel.add(weightLabel);
        leftPanel.add(heightLabel);
        leftPanel.add(ageLabel);
        leftPanel.add(genderLabel);
        leftPanel.add(accountDateLabel);
        leftPanel.add(currentWeightDateLabel);

        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Button to edit user information
        JButton editButton = new JButton("EDIT SOME INFORMATION");
        editButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        editButton.setForeground(darkPurple);
        editButton.setBackground(new Color(230, 220, 250));
        editButton.setFocusPainted(false);

        editButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(editButton);

        // Action listener for the edit button
        editButton.addActionListener(e -> openEditDialog(frame, nameLabel, weightLabel, heightLabel, currentWeightDateLabel));

        // Add the left panel to the center panel
        centerPanel.add(leftPanel, BorderLayout.WEST);

        // Right Panel with BMI indicator
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(230, 220, 250));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        bmiLabel = new JLabel("Your BMI: -");
        bmiLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        bmiLabel.setForeground(dataColor);
        bmiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        rightPanel.add(bmiLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        bmiCategoryLabel = new JLabel("");
        bmiCategoryLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        bmiCategoryLabel.setForeground(dataColor);
        bmiCategoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(bmiCategoryLabel);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        bmiIndicator = new BMIIndicator();
        bmiIndicator.setPreferredSize(new Dimension(320, 320));
        bmiIndicator.setAlignmentX(Component.CENTER_ALIGNMENT);

        rightPanel.add(bmiIndicator);

        // Add the right panel to the center panel
        centerPanel.add(rightPanel, BorderLayout.EAST);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        // Calculate and set BMI if height is available
        if (userHeight > 0) {
            double heightInMeters = userHeight / 100.0;
            double calculatedBMI = userWeight / Math.pow(heightInMeters, 2);
            setBMI(calculatedBMI);
        }
    }

    /**
     * Opens a dialog that allows the user to edit their information.
     *
     * @param parentFrame              The main application frame.
     * @param nameLabel                The JLabel displaying the user's name.
     * @param weightLabel              The JLabel displaying the user's weight.
     * @param heightLabel              The JLabel displaying the user's height.
     * @param currentWeightDateLabel   The JLabel displaying the date of the current weight.
     */
    private void openEditDialog(JFrame parentFrame,
                                JLabel nameLabel,
                                JLabel weightLabel,
                                JLabel heightLabel,
                                JLabel currentWeightDateLabel) {

        // Create a modal dialog for editing information
        JDialog dialog = new JDialog(parentFrame, "Edit Information", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
        dialog.getContentPane().setBackground(new Color(230, 220, 250));

        // Text fields for user input
        JTextField nameField = new JTextField(userName);
        JTextField weightField = new JTextField(String.valueOf(userWeight));
        JTextField heightField_ = new JTextField(String.valueOf(userHeight));

        // Labels for the text fields
        JLabel nameLbl = new JLabel("Name:");
        JLabel weightLbl = new JLabel("Weight:");
        JLabel heightLbl_ = new JLabel("Height:");

        // Configure label fonts and colors
        Font lblFont = new Font("Comic Sans MS", Font.BOLD, 16);
        Color lblColor = new Color(113, 54, 143);
        nameLbl.setFont(lblFont);
        nameLbl.setForeground(lblColor);
        weightLbl.setFont(lblFont);
        weightLbl.setForeground(lblColor);
        heightLbl_.setFont(lblFont);
        heightLbl_.setForeground(lblColor);

        // Set maximum sizes for text fields
        nameField.setMaximumSize(new Dimension(200, 30));
        weightField.setMaximumSize(new Dimension(200, 30));
        heightField_.setMaximumSize(new Dimension(200, 30));

        // Buttons for saving or canceling edits
        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        saveButton.setForeground(lblColor);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        cancelButton.setForeground(lblColor);

        // Panel containing the text fields and labels
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        fieldPanel.setBackground(new Color(230, 220, 250));
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        fieldPanel.add(nameLbl);
        fieldPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldPanel.add(nameField);
        fieldPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        fieldPanel.add(weightLbl);
        fieldPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldPanel.add(weightField);
        fieldPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        fieldPanel.add(heightLbl_);
        fieldPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldPanel.add(heightField_);

        // Panel containing the save and cancel buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(230, 220, 250));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Add panels to the dialog
        dialog.add(fieldPanel);
        dialog.add(buttonPanel);

        // Action listener for the "Save" button
        saveButton.addActionListener(e -> {
            String newName = nameField.getText().trim();
            double newWeight;
            double newHeight;
            try {
                newWeight = Double.parseDouble(weightField.getText().trim());
            } catch (NumberFormatException ex) {
                newWeight = userWeight;
            }
            try {
                newHeight = Double.parseDouble(heightField_.getText().trim());
            } catch (NumberFormatException ex) {
                newHeight = userHeight;
            }

            // Update the date of the current weight
            String newWeightDate = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // 1) Update the usersData.txt file
            updateUserDataInFile(
                    userUUID,
                    newName,
                    newWeight,
                    newHeight,
                    userAge,
                    userGender,
                    dateOfAccountCreation,
                    newWeightDate
            );

            // 2) Update the in-memory data
            userName = newName;
            userWeight = newWeight;
            userHeight = newHeight;
            dateOfCurrentWeight = newWeightDate;

            // 3) Refresh the labels in the GUI
            nameLabel.setText("Name: " + userName);
            weightLabel.setText("Weight: " + userWeight);
            heightLabel.setText("Height: " + userHeight);
            currentWeightDateLabel.setText("Date of current weight: " + dateOfCurrentWeight);

            // 4) Recalculate and set BMI
            if (userHeight > 0) {
                double heightInMeters = userHeight / 100.0;
                double newBMI = userWeight / Math.pow(heightInMeters, 2);
                setBMI(newBMI);
            }

            // 5) Update the weightsData.txt file
            updateWeightsDataInFile(userUUID, newWeightDate, newWeight);

            // Close the dialog
            dialog.dispose();
        });

        // Action listener for the "Cancel" button
        cancelButton.addActionListener(e -> dialog.dispose());

        // Make the dialog visible
        dialog.setVisible(true);
    }

    /**
     * Updates the user data in the {@code usersData.txt} file.
     *
     * @param uuid                 The UUID of the user.
     * @param newName              The new name of the user.
     * @param newWeight            The new weight of the user.
     * @param newHeight            The new height of the user.
     * @param age                  The age of the user.
     * @param gender               The gender of the user.
     * @param accountCreationDate  The account creation date of the user.
     * @param weightDate           The date of the current weight.
     */
    public void updateUserDataInFile(String uuid, String newName, double newWeight, double newHeight,
                                     int age, String gender, String accountCreationDate,
                                     String weightDate) {

        File file = new File("src/main/resources/usersData.txt");
        if (!file.exists()) {
            System.err.println("File not found: " + file.getAbsolutePath());
            return;
        }

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    sb.append("\n");
                    continue;
                }
                String[] parts = line.split(";");
                if (parts.length < 8) {
                    sb.append(line).append("\n");
                    continue;
                }

                String uuidFromFile = parts[0];
                if (uuidFromFile.equals(uuid)) {
                    // Overwrite the user data line
                    String newLine = uuid + ";" + newName + ";" + newWeight + ";" +
                            newHeight + ";" + age + ";" + gender + ";" +
                            accountCreationDate + ";" + weightDate;
                    sb.append(newLine).append("\n");
                    found = true;
                } else {
                    sb.append(line).append("\n");
                }
            }

            if (!found) {
                System.out.println("UUID not found in file; nothing updated.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write the updated content back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            bw.write(sb.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sets the BMI value and updates the corresponding labels and indicators.
     *
     * @param userBMI The calculated BMI of the user.
     */
    public void setBMI(double userBMI) {
        bmiLabel.setText(String.format("Your BMI: %.1f", userBMI));
        bmiIndicator.setBMI(userBMI);
        bmiIndicator.repaint();

        // Determine BMI category and set the label text and color accordingly
        if (userBMI < 18.5) {
            bmiCategoryLabel.setText("Underweight");
            bmiCategoryLabel.setForeground(new Color(173, 216, 230)); // Blue
        } else if (userBMI < 25.0) {
            bmiCategoryLabel.setText("Normal weight");
            bmiCategoryLabel.setForeground(new Color(144, 238, 144)); // Green
        } else if (userBMI < 30.0) {
            bmiCategoryLabel.setText("Overweight");
            bmiCategoryLabel.setForeground(new Color(255, 218, 185)); // Orange
        } else {
            bmiCategoryLabel.setText("Obesity");
            bmiCategoryLabel.setForeground(new Color(250, 128, 114)); // Red
        }
    }

    /**
     * Loads user data from the specified file.
     *
     * @param fileName The path to the file containing user data.
     */
    public void loadUserDataFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.err.println("File " + fileName + " does not exist!");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) continue;
                String[] parts = line.split(";");
                if (parts.length < 8) continue;

                String uuidFromFile = parts[0];
                if (uuidFromFile.equals(userUUID)) {
                    userName = parts[1];
                    try {
                        userWeight = Double.parseDouble(parts[2]);
                    } catch (NumberFormatException e) {
                        userWeight = 0.0;
                    }
                    try {
                        userHeight = Double.parseDouble(parts[3]);
                    } catch (NumberFormatException e) {
                        userHeight = 0.0;
                    }
                    try {
                        userAge = Integer.parseInt(parts[4]);
                    } catch (NumberFormatException e) {
                        userAge = 0;
                    }
                    userGender = parts[5];
                    dateOfAccountCreation = parts[6];
                    dateOfCurrentWeight = parts[7];
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the weights data in the {@code weightsData.txt} file for the user.
     *
     * @param uuid      The UUID of the user.
     * @param newDate   The date of the weight update.
     * @param newWeight The new weight of the user.
     */
    public void updateWeightsDataInFile(String uuid, String newDate, double newWeight) {
        File file = new File("src/main/resources/weightsData.txt");
        boolean fileExists = file.exists();
        StringBuilder sb = new StringBuilder();
        boolean foundUserLine = false;

        // If the file does not exist, create a new one
        if (!fileExists) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Process each line to update or add weight data
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                sb.append(line).append("\n");
                continue;
            }
            String[] parts = line.split(";");
            if (parts.length < 2) {
                sb.append(line).append("\n");
                continue;
            }

            String uuidFromFile = parts[0];
            if (uuidFromFile.equals(uuid)) {
                // Found the user's line
                foundUserLine = true;
                // Check if the date is already recorded
                boolean dateAlreadySaved = false;
                StringBuilder userLineBuilder = new StringBuilder();
                userLineBuilder.append(uuid);

                for (int i = 1; i < parts.length; i++) {
                    String entry = parts[i];
                    if (entry.startsWith(newDate + "~")) {
                        dateAlreadySaved = true;
                    }
                    userLineBuilder.append(";").append(entry);
                }
                // If the date is not already saved, append it
                if (!dateAlreadySaved) {
                    userLineBuilder.append(";").append(newDate).append("~").append(newWeight);
                }
                sb.append(userLineBuilder.toString()).append("\n");
            } else {
                sb.append(line).append("\n");
            }
        }

        // If the user's line was not found, add a new entry
        if (!foundUserLine) {
            String newLine = uuid + ";" + newDate + "~" + newWeight;
            sb.append(newLine).append("\n");
        }

        // Write the updated content back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            bw.write(sb.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * The {@code BMIIndicator} class is an inner class that graphically represents the BMI indicator.
     * It displays a semicircular gauge with different color segments indicating BMI categories
     * and a pointer showing the user's current BMI.
     */
    private static class BMIIndicator extends JPanel {
        private double userBMI = 0;

        /**
         * Sets the BMI value to be displayed.
         *
         * @param userBMI The BMI value of the user.
         */
        public void setBMI(double userBMI) {
            this.userBMI = userBMI;
        }

        /**
         * Overrides the {@code paintComponent} method to draw the BMI indicator.
         *
         * @param g The Graphics object used for drawing.
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Fill background
            g2d.setColor(new Color(230, 220, 250));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            int width = getWidth();
            int height = getHeight();

            int radius = (int) (Math.min(width, height) / 2.5);

            // Margin from the right edge
            int marginRight = 50;

            // Calculate the center position of the semicircle
            int centerX = width - radius - marginRight;
            int centerY = height / 2;

            int startAngle = 0;
            int arcAngle = 180;

            // Draw segments representing BMI categories
            // Red segment (Obesity)
            g2d.setColor(new Color(250, 128, 114));
            g2d.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2,
                    startAngle, arcAngle / 4);

            // Orange segment (Overweight)
            g2d.setColor(new Color(255, 218, 185));
            g2d.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2,
                    startAngle + arcAngle / 4, arcAngle / 4);

            // Green segment (Normal weight)
            g2d.setColor(new Color(144, 238, 144));
            g2d.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2,
                    startAngle + arcAngle / 2, arcAngle / 4);

            // Blue segment (Underweight)
            g2d.setColor(new Color(173, 216, 230));
            g2d.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2,
                    startAngle + 3 * arcAngle / 4, arcAngle / 4);

            // Draw the pointer indicating the user's BMI
            g2d.setColor(Color.BLACK);
            double angle;
            if (userBMI < 18.5) {
                angle = 180 - ((userBMI / 18.5) * 45);
            } else if (userBMI < 25.0) {
                angle = 135 - (((userBMI - 18.5) / (25.0 - 18.5)) * 45);
            } else if (userBMI < 30.0) {
                angle = 90 - (((userBMI - 25.0) / (30.0 - 25.0)) * 45);
            } else {
                angle = 45 - (((userBMI - 30.0) / (40.0 - 30.0)) * 45);
                if (angle < 0) angle = 0;
            }

            double rad = Math.toRadians(angle);
            int pointerX = (int) (centerX + Math.cos(rad) * radius * 0.9);
            int pointerY = (int) (centerY - Math.sin(rad) * radius * 0.9);

            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(centerX, centerY, pointerX, pointerY);

            // Draw the outline of the semicircle and the base line
            g2d.setStroke(new BasicStroke(3));
            g2d.drawArc(centerX - radius, centerY - radius, radius * 2, radius * 2,
                    startAngle, arcAngle);
            g2d.drawLine(centerX - radius, centerY, centerX + radius, centerY);
        }
    }
}
