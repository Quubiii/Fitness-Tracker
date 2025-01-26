package main.java.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.UUID;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import main.java.DateLabelFormatter;
import main.java.*;

/**
 * The {@code RegisterTrainingGUI} class provides a graphical user interface for users to register their training sessions
 * within the Fitness Tracker application. It allows users to select the type of training, specify the date, duration,
 * distance (if applicable), and intensity. The class calculates the calories burned based on the provided inputs and
 * saves the training data to a file.
 */
public class RegisterTrainingGUI {
    // Constants representing the Metabolic Equivalent of Task (MET) values for different training types
    private static final double MET_RUNNING = 7.0;
    private static final double MET_CYCLING = 6.0;
    private static final double MET_ROPE_JUMPING = 12.0;

    private double userWeight; // Stores the weight of the user
    String loggedInUUID = LoginGUI.loggedInUserUUID; // Retrieves the UUID of the currently logged-in user

    /**
     * Constructs the {@code RegisterTrainingGUI} and initializes the training registration interface.
     *
     * @param userWeight The weight of the user, used for calculating calories burned.
     */
    public RegisterTrainingGUI(double userWeight) {
        this.userWeight = userWeight;

        // Create the main frame for the training registration GUI
        JFrame frame = new JFrame("Fitness Tracker - Register Training");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and configure the main panel with vertical BoxLayout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(230, 220, 250)); // Light purple background
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // Padding around the panel

        // Create and configure the welcome label
        JLabel welcomeLabel = new JLabel("REGISTER YOUR TRAINING", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(113, 54, 143));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment

        // Create and configure the training type label and combo box
        JLabel typeLabel = createCenteredLabel("Select Training Type:");
        JComboBox<String> trainingTypeComboBox = new JComboBox<>(new String[]{"Running", "Cycling", "Rope Jumping"});
        trainingTypeComboBox.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        trainingTypeComboBox.setMaximumSize(new Dimension(250, 30));
        trainingTypeComboBox.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment

        // Create and configure the training date label and date picker
        JLabel dateLabel = createCenteredLabel("Select Training Date:");

        UtilDateModel dateModel = new UtilDateModel(); // Model for the date picker
        Properties dateProperties = new Properties();
        dateProperties.put("text.today", "Today");
        dateProperties.put("text.month", "Month");
        dateProperties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, dateProperties);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.getJFormattedTextField().setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        datePicker.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
        datePicker.setMaximumSize(new Dimension(250, 30)); // Set maximum size

        // Create and configure the training duration label and text field
        JLabel durationLabel = createCenteredLabel("Training Duration (minutes):");
        JTextField durationField = createTextField();

        // Create and configure the covered distance label and text field
        JLabel distanceLabel = createCenteredLabel("Covered Distance (km):");
        JTextField distanceField = createTextField();
        distanceField.setEnabled(false); // Disable by default

        // Create and configure the intensity label and combo box
        JLabel intensityLabel = createCenteredLabel("Select Intensity:");
        JComboBox<String> intensityComboBox = new JComboBox<>(new String[]{"Low", "Medium", "High"});
        intensityComboBox.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        intensityComboBox.setMaximumSize(new Dimension(250, 30));
        intensityComboBox.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment

        // Create and configure the submit button
        JButton submitButton = new JButton("SUBMIT");
        submitButton.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        submitButton.setForeground(new Color(113, 54, 143));
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment

        // Create labels to display messages and calorie information
        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment

        JLabel caloriesLabel = new JLabel("", SwingConstants.CENTER);
        caloriesLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment

        // Add components to the panel with spacing using rigid areas
        panel.add(Box.createVerticalGlue());
        panel.add(welcomeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(typeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(trainingTypeComboBox);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(dateLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(datePicker);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(durationLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(durationField);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(distanceLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(distanceField);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(intensityLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(intensityComboBox);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(messageLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(submitButton);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(caloriesLabel);
        panel.add(Box.createVerticalGlue());

        // Add the panel to the frame's center region
        frame.add(panel, BorderLayout.CENTER);
        frame.setPreferredSize(new Dimension(500, 600)); // Set preferred size
        frame.pack(); // Adjust frame to fit content
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true); // Make the frame visible

        // Add action listener to handle changes in the training type selection
        trainingTypeComboBox.addActionListener(e -> {
            String selectedType = (String) trainingTypeComboBox.getSelectedItem();
            if ("Running".equals(selectedType) || "Cycling".equals(selectedType)) {
                distanceField.setEnabled(true); // Enable distance field for Running and Cycling
            } else {
                distanceField.setEnabled(false); // Disable distance field for Rope Jumping
                distanceField.setText(""); // Clear the distance field
            }
        });

        // Add action listener to handle submit button clicks
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String trainingType = (String) trainingTypeComboBox.getSelectedItem(); // Get selected training type
                    java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue(); // Get selected date
                    if (selectedDate == null) {
                        throw new IllegalArgumentException("Please select a valid date."); // Ensure a date is selected
                    }
                    // Convert selected date to LocalDateTime
                    LocalDateTime startTime = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    int duration = Integer.parseInt(durationField.getText()); // Get training duration
                    String intensity = (String) intensityComboBox.getSelectedItem(); // Get selected intensity

                    double metValue = getMetValue(trainingType, intensity); // Calculate MET value based on training type and intensity
                    double caloriesBurned = calculateCaloriesBurned(metValue, duration); // Calculate calories burned

                    Activity activity;
                    if ("Running".equals(trainingType)) {
                        double distance = Double.parseDouble(distanceField.getText()); // Get covered distance
                        double avgSpeed = distance / (duration / 60.0); // Calculate average speed
                        // Create a RunningActivity instance with relevant data
                        activity = new RunningActivity(UUID.randomUUID().hashCode(), "Running", caloriesBurned, duration, startTime, startTime.plusMinutes(duration), distance, avgSpeed);
                    } else if ("Cycling".equals(trainingType)) {
                        double distance = Double.parseDouble(distanceField.getText()); // Get covered distance
                        double avgSpeed = distance / (duration / 60.0); // Calculate average speed
                        double maxSpeed = avgSpeed + 5.0; // Assume max speed is average speed plus 5 km/h
                        // Create a CyclingActivity instance with relevant data
                        activity = new CyclingActivity(UUID.randomUUID().hashCode(), "Cycling", caloriesBurned, duration, startTime, startTime.plusMinutes(duration), distance, maxSpeed);
                    } else {
                        // Calculate repetitions for Rope Jumping based on duration
                        int repetitions = calculateRepetitions(duration);
                        // Create a RopeJumpingActivity instance with relevant data
                        activity = new RopeJumpingActivity(UUID.randomUUID().hashCode(), "Rope Jumping", caloriesBurned, duration, startTime, startTime.plusMinutes(duration), repetitions);
                    }

                    saveTrainingToFile(activity); // Save the training activity to a file

                    // Update message and calories label to inform the user of successful registration
                    messageLabel.setText("Training saved successfully!");
                    caloriesLabel.setText("Calories Burned: " + String.format("%.2f", caloriesBurned) + " kcal");
                    messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                    messageLabel.setForeground(new Color(113, 54, 143));
                    caloriesLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                    caloriesLabel.setForeground(new Color(113, 54, 143));

                    // Set a timer to display a redirect message after 5 seconds
                    Timer timer = new Timer(5000, event -> {
                        messageLabel.setText("Redirecting to the main page...");
                        messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                        messageLabel.setForeground(new Color(113, 54, 143));

                        // Set another timer to perform the actual redirection after an additional 2 seconds
                        Timer redirectTimer = new Timer(2000, redirectEvent -> {
                            frame.dispose(); // Close the current frame
                            new WorkoutsGUI(); // Redirect to the WorkoutsGUI (replace with MainGUI() if intended)
                        });
                        redirectTimer.setRepeats(false); // Ensure the timer only runs once
                        redirectTimer.start();
                    });
                    timer.setRepeats(false); // Ensure the timer only runs once
                    timer.start();

                } catch (NumberFormatException ex) {
                    // Handle cases where numeric input is invalid
                    messageLabel.setText("Duration and Distance must be numeric.");
                    messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                    messageLabel.setForeground(new Color(113, 54, 143));
                } catch (Exception ex) {
                    // Handle any other exceptions and display an error message
                    messageLabel.setText("Error: " + ex.getMessage());
                    messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                    messageLabel.setForeground(new Color(113, 54, 143));
                }
            }
        });
    }

    /**
     * Saves the provided training activity to the trainingData.txt file.
     * If the user already has entries, appends the new activity separated by "~".
     * Otherwise, creates a new entry for the user.
     *
     * @param activity The {@code Activity} object representing the training session.
     */
    private void saveTrainingToFile(Activity activity) {
        try {
            File file = new File("src/main/resources/trainingData.txt"); // Define the training data file
            if (!file.exists()) {
                file.createNewFile(); // Create the file if it does not exist
            }

            boolean userExists = false; // Flag to check if the user already has entries
            StringBuilder fileContent = new StringBuilder(); // StringBuilder to accumulate file content
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                // Read each line and look for the user's UUID
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith(loggedInUUID + ";")) {
                        userExists = true;
                        // Append the new training activity separated by "~"
                        line += "~" + activity.getAllInfo();
                    }
                    fileContent.append(line).append("\n"); // Accumulate the line
                }
            }

            if (!userExists) {
                // If the user does not have any entries, add a new line with their UUID and the activity
                fileContent.append(loggedInUUID).append(";").append(activity.getAllInfo()).append("\n");
            }

            // Write the accumulated content back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(fileContent.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save training data.", e); // Throw a runtime exception if saving fails
        }
    }

    /**
     * Creates a centered JLabel with the specified text, font, and color.
     *
     * @param text The text to display on the label.
     * @return A {@code JLabel} configured with the specified properties.
     */
    private JLabel createCenteredLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER); // Create label with centered text
        label.setFont(new Font("Comic Sans MS", Font.BOLD, 16)); // Set font
        label.setForeground(new Color(113, 54, 143)); // Set text color
        label.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
        return label;
    }

    /**
     * Creates a JTextField with predefined styling.
     *
     * @return A {@code JTextField} configured with the specified properties.
     */
    private JTextField createTextField() {
        JTextField textField = new JTextField(20); // Create text field with specified columns
        textField.setFont(new Font("Comic Sans MS", Font.BOLD, 16)); // Set font
        textField.setMaximumSize(new Dimension(250, 30)); // Set maximum size
        textField.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
        return textField;
    }

    /**
     * Determines the MET value based on the training type and intensity.
     *
     * @param trainingType The type of training (e.g., Running, Cycling, Rope Jumping).
     * @param intensity    The intensity level of the training (Low, Medium, High).
     * @return The calculated MET value.
     */
    private double getMetValue(String trainingType, String intensity) {
        double baseMet;
        // Assign base MET value based on training type
        switch (trainingType) {
            case "Running":
                baseMet = MET_RUNNING;
                break;
            case "Cycling":
                baseMet = MET_CYCLING;
                break;
            case "Rope Jumping":
                baseMet = MET_ROPE_JUMPING;
                break;
            default:
                baseMet = 1.0; // Default MET value if training type is unrecognized
        }

        // Adjust MET value based on intensity level
        switch (intensity) {
            case "Low":
                return baseMet * 0.8;
            case "Medium":
                return baseMet;
            case "High":
                return baseMet * 1.2;
            default:
                return baseMet; // Return base MET if intensity is unrecognized
        }
    }

    /**
     * Calculates the number of calories burned based on MET value and training duration.
     *
     * @param metValue The MET value of the training.
     * @param duration The duration of the training in minutes.
     * @return The estimated number of calories burned.
     */
    private double calculateCaloriesBurned(double metValue, int duration) {
        return metValue * userWeight * (duration / 60.0); // Formula to calculate calories burned
    }

    /**
     * Calculates the number of repetitions for Rope Jumping based on training duration.
     * Assumes a generalized pace of 2 jumps per second.
     *
     * @param durationMinutes The duration of the training in minutes.
     * @return The estimated number of repetitions.
     */
    private int calculateRepetitions(int durationMinutes) {
        int repetitionsPerSecond = 2; // Assumed pace
        int durationInSeconds = durationMinutes * 60; // Convert minutes to seconds
        return repetitionsPerSecond * durationInSeconds; // Calculate total repetitions
    }
}
