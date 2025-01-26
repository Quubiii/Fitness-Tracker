package main.java.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.*;

/**
 * The {@code WorkoutStatsGUI} class provides a graphical user interface for users to view statistics
 * about their workouts within the Fitness Tracker application. It displays the most intense workout
 * and a bar chart representing the total calories burned by each activity type. The class reads
 * workout data from a file, processes it, and visualizes it using JFreeChart. It also includes
 * navigation buttons to other sections of the application.
 */
public class WorkoutStatsGUI {

    private String userUUID; // Stores the UUID of the currently logged-in user
    private ArrayList<Workout> userWorkouts = new ArrayList<>(); // List to store user's workouts
    private Workout mostIntenseWorkout; // Stores the most intense workout based on calories burned

    /**
     * Constructs the {@code WorkoutStatsGUI} and initializes the workout statistics interface.
     * It loads the user's workout data, sets up the main frame, configures navigation buttons,
     * and displays the most intense workout and a bar chart of calories burned by activity type.
     */
    public WorkoutStatsGUI() {
        // Retrieve the UUID of the currently logged-in user
        userUUID = LoginGUI.loggedInUserUUID;

        // Load workout data from the specified file
        loadWorkoutDataFromFile("src/main/resources/trainingData.txt");

        // Create the main application frame
        JFrame frame = new JFrame("Fitness Tracker - Workout Stats");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800); // Increased size for better layout
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setLayout(new BorderLayout());

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
            new WorkoutStatsGUI();  // Reload the WorkoutStatsGUI
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
        // CENTER PANEL (Workout Statistics)
        // ----------------
        JPanel centerPanel = new JPanel(new BorderLayout()); // Create the center panel with BorderLayout
        centerPanel.setBackground(new Color(230, 220, 250)); // Set background color to light purple

        // Left panel: Most intense workout and summary
        JPanel leftPanel = new JPanel(); // Create the left panel to display the most intense workout and summary
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); // Set layout to vertical BoxLayout
        leftPanel.setBackground(new Color(230, 220, 250)); // Match the background color

        JLabel intenseWorkoutLabel = new JLabel("Most Intense Workout:"); // Label for the most intense workout
        intenseWorkoutLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24)); // Set font style and size
        intenseWorkoutLabel.setForeground(darkPurple); // Set text color
        leftPanel.add(intenseWorkoutLabel); // Add the label to the left panel
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add vertical spacing

        if (mostIntenseWorkout != null) {
            // Conditionally display workout details based on the type of workout
            StringBuilder html = new StringBuilder("<html>");
            html.append("Name: ").append(mostIntenseWorkout.getName()).append("<br>");
            html.append("Calories Burned: ").append(mostIntenseWorkout.getCaloriesBurned()).append("<br>");
            html.append("Duration: ").append(mostIntenseWorkout.getDuration()).append(" minutes<br>");

            if (mostIntenseWorkout.getName().equalsIgnoreCase("Rope Jumping")) {
                html.append("Repetitions: ").append(mostIntenseWorkout.getRepetitions()).append("<br>");
            } else {
                html.append("Distance: ").append(mostIntenseWorkout.getDistance()).append(" km<br>");
                html.append("Speed: ").append(mostIntenseWorkout.getSpeed()).append(" km/h<br>");
            }

            html.append("</html>");

            JLabel workoutDetails = new JLabel(html.toString());
            workoutDetails.setFont(new Font("Comic Sans MS", Font.PLAIN, 18)); // Set font style and size
            workoutDetails.setForeground(darkPurple); // Set text color
            leftPanel.add(workoutDetails); // Add the workout details to the left panel
        } else {
            // Inform the user if no workout data is found
            JLabel noDataLabel = new JLabel("No workout data found.");
            noDataLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18)); // Set font style and size
            noDataLabel.setForeground(darkPurple); // Set text color
            leftPanel.add(noDataLabel); // Add the no data label to the left panel
        }

        // ----------------
        // SUMMARY PANEL
        // ----------------
        JPanel summaryPanel = new JPanel(); // Create a panel for the summary
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS)); // Set layout to vertical BoxLayout
        summaryPanel.setBackground(new Color(230, 220, 250)); // Match the background color
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Add top padding

        // Calculate statistics
        double averageActivityTime = calculateAverageActivityTime();
        double averageActivityCalories = calculateAverageActivityCalories();
        double totalActivityTime = calculateTotalActivityTime();
        double totalBurnedCalories = calculateTotalBurnedCalories();
        Workout longestActivity = findTheLongestActivity();
        Workout shortestActivity = findTheShortestActivity();

        // Create labels for each statistic
        JLabel averageTimeLabel = new JLabel("Average Activity Time: " + String.format("%.2f", averageActivityTime) + " minutes");
        averageTimeLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        averageTimeLabel.setForeground(darkPurple);

        JLabel averageCaloriesLabel = new JLabel("Average Activity Calories: " + String.format("%.2f", averageActivityCalories) + " calories");
        averageCaloriesLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        averageCaloriesLabel.setForeground(darkPurple);

        JLabel totalTimeLabel = new JLabel("Total Activity Time: " + String.format("%.2f", totalActivityTime) + " minutes");
        totalTimeLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        totalTimeLabel.setForeground(darkPurple);

        JLabel totalCaloriesLabel = new JLabel("Total Burned Calories: " + String.format("%.2f", totalBurnedCalories) + " calories");
        totalCaloriesLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        totalCaloriesLabel.setForeground(darkPurple);

        JLabel longestActivityLabel = new JLabel("Longest Activity: " + (longestActivity != null ? longestActivity.getName() + " (" + longestActivity.getDuration() + " minutes)" : "N/A"));
        longestActivityLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        longestActivityLabel.setForeground(darkPurple);

        JLabel shortestActivityLabel = new JLabel("Shortest Activity: " + (shortestActivity != null ? shortestActivity.getName() + " (" + shortestActivity.getDuration() + " minutes)" : "N/A"));
        shortestActivityLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        shortestActivityLabel.setForeground(darkPurple);

        // Add labels to the summary panel
        summaryPanel.add(averageTimeLabel);
        summaryPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add spacing between labels
        summaryPanel.add(averageCaloriesLabel);
        summaryPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        summaryPanel.add(totalTimeLabel);
        summaryPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        summaryPanel.add(totalCaloriesLabel);
        summaryPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        summaryPanel.add(longestActivityLabel);
        summaryPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        summaryPanel.add(shortestActivityLabel);

        leftPanel.add(summaryPanel); // Add the summary panel to the left panel

        centerPanel.add(leftPanel, BorderLayout.WEST); // Add the left panel to the center panel's west region

        // Right panel: Chart of total calories burned by activity type
        if (!userWorkouts.isEmpty()) {
            // Calculate total calories burned by each activity type
            Map<String, Double> caloriesByType = userWorkouts.stream()
                    .collect(Collectors.groupingBy(
                            Workout::getName,
                            Collectors.summingDouble(Workout::getCaloriesBurned)
                    ));

            // Create a dataset for the bar chart
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (Map.Entry<String, Double> entry : caloriesByType.entrySet()) {
                dataset.addValue(entry.getValue(), "Calories Burned", entry.getKey());
            }

            // Create a bar chart using the dataset
            JFreeChart barChart = ChartFactory.createBarChart(
                    "Total Calories Burned by Activity", // Chart title
                    "Activity Type",                      // X-axis label
                    "Calories Burned",                    // Y-axis label
                    dataset,                              // Dataset
                    PlotOrientation.VERTICAL,             // Plot orientation
                    false, // Include legend
                    true,  // Include tooltips
                    false  // URLs
            );

            // Customize the background color of the chart
            barChart.setBackgroundPaint(new Color(230, 220, 250)); // Light purple background

            // Retrieve the plot from the chart to customize further
            CategoryPlot plot = barChart.getCategoryPlot();

            // Set the background color of the plot area
            plot.setBackgroundPaint(new Color(230, 220, 250)); // Match the overall background color

            // Set gridline colors
            plot.setDomainGridlinePaint(Color.GRAY); // X-axis gridlines
            plot.setRangeGridlinePaint(Color.GRAY);  // Y-axis gridlines

            // Retrieve and customize the renderer for the bar chart
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setSeriesPaint(0, new Color(113, 54, 143)); // Set bar color to dark purple

            // Create a ChartPanel to display the bar chart
            ChartPanel chartPanel = new ChartPanel(barChart);
            chartPanel.setPreferredSize(new Dimension(600, 400)); // Set preferred size for the chart

            centerPanel.add(chartPanel, BorderLayout.CENTER); // Add the chart panel to the center panel's center region
        }

        frame.add(centerPanel, BorderLayout.CENTER); // Add the center panel to the main frame
        frame.setVisible(true); // Make the frame visible
    }

    /**
     * Loads workout data from the specified file for the currently logged-in user.
     * The file format is expected to be:
     * uuid;Workout1~Workout2~Workout3~...
     *
     * Each workout entry should contain details separated by identifiable markers.
     *
     * @param fileName The path to the trainingData.txt file.
     */
    private void loadWorkoutDataFromFile(String fileName) {
        File file = new File(fileName); // Define the workout data file
        if (!file.exists()) {
            System.err.println("Workout data file not found: " + fileName); // Print error if file does not exist
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) { // Open the file for reading
            String line;
            while ((line = br.readLine()) != null) { // Read each line
                if (line.isEmpty()) continue; // Skip empty lines

                // Split the line by semicolon to separate UUID from workout entries
                String[] parts = line.split(";");
                String uuidFromFile = parts[0];

                if (!uuidFromFile.equals(userUUID)) continue; // Skip data that does not belong to the current user

                // Split the workout entries by tilde (~)
                String[] workouts = parts[1].split("~");
                for (String workoutEntry : workouts) { // Parse each workout entry
                    parseActivity(workoutEntry);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if an IO error occurs
        }
    }

    /**
     * Parses a single workout entry string and adds it to the user's workout list.
     *
     * @param activityEntry The workout entry string containing workout details.
     */
    private void parseActivity(String activityEntry) {
        try {
            // Extract the workout name
            String name = extractField(activityEntry, "Name: ", "Burned calories:");

            // Extract calories burned
            String caloriesStr = extractField(activityEntry, "Burned calories: ", "Duration:");
            double caloriesBurned = Double.parseDouble(caloriesStr.trim());

            // Initialize variables for duration and other fields
            double duration = 0.0;
            double distance = 0.0;
            double speed = 0.0;
            int repetitions = 0;

            // Conditional parsing based on workout name
            if (name.equalsIgnoreCase("Running")) {
                // For Running, duration ends at "Distance ran:"
                String durationStr = extractField(activityEntry, "Duration: ", "Distance ran:");
                duration = Double.parseDouble(durationStr.trim());

                // Extract distance ran
                String distanceStr = extractField(activityEntry, "Distance ran: ", "Average speed:");
                distance = Double.parseDouble(distanceStr.replace(" km", "").trim());

                // Extract average speed
                String speedStr = extractField(activityEntry, "Average speed: ", "");
                speed = Double.parseDouble(speedStr.replace(" km/h", "").trim());

                // Create and add the Running workout
                Workout running = new Workout(name, caloriesBurned, duration, distance, speed);
                userWorkouts.add(running);

            } else if (name.equalsIgnoreCase("Cycling")) {
                // For Cycling, duration ends at "Distance cycled:"
                String durationStr = extractField(activityEntry, "Duration: ", "Distance cycled:");
                duration = Double.parseDouble(durationStr.trim());

                // Extract distance cycled
                String distanceStr = extractField(activityEntry, "Distance cycled: ", "Maximum speed:");
                distance = Double.parseDouble(distanceStr.replace(" km", "").trim());

                // Extract maximum speed
                String speedStr = extractField(activityEntry, "Maximum speed: ", "");
                speed = Double.parseDouble(speedStr.replace(" km/h", "").trim());

                // Create and add the Cycling workout
                Workout cycling = new Workout(name, caloriesBurned, duration, distance, speed);
                userWorkouts.add(cycling);

            } else if (name.equalsIgnoreCase("Rope Jumping")) {
                // For Rope Jumping, duration ends at "Repetitions:"
                String durationStr = extractField(activityEntry, "Duration: ", "Repetitions:");
                duration = Double.parseDouble(durationStr.trim());

                // Extract repetitions
                String repetitionsStr = extractField(activityEntry, "Repetitions: ", "");
                repetitions = Integer.parseInt(repetitionsStr.trim());

                // Create and add the Rope Jumping workout
                Workout ropeJumping = new Workout(name, caloriesBurned, duration, repetitions, 0);
                userWorkouts.add(ropeJumping);

            } else {
                System.err.println("Unsupported activity type: " + name); // Inform about unsupported activity types
            }

            // Update the most intense workout based on calories burned
            if (mostIntenseWorkout == null || caloriesBurned > mostIntenseWorkout.getCaloriesBurned()) {
                mostIntenseWorkout = userWorkouts.get(userWorkouts.size() - 1); // Set as the most intense workout
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            System.err.println("Error parsing activity: " + activityEntry); // Inform about parsing errors
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    /**
     * Extracts a specific field from a workout entry string based on start and end markers.
     *
     * @param entry    The workout entry string.
     * @param startTag The starting marker indicating the beginning of the desired field.
     * @param endTag   The ending marker indicating the end of the desired field.
     * @return The extracted field as a {@code String}. Returns an empty string if markers are not found.
     */
    private String extractField(String entry, String startTag, String endTag) {
        int startIndex = entry.indexOf(startTag); // Find the start index of the desired field
        if (startIndex == -1) {
            return ""; // Return empty string if start marker is not found
        }
        startIndex += startTag.length(); // Move past the start marker

        int endIndex = endTag.isEmpty() ? entry.length() : entry.indexOf(endTag, startIndex); // Find the end index
        if (endIndex == -1) {
            endIndex = entry.length(); // If end marker is not found, set to the end of the string
        }

        if (startIndex >= endIndex) {
            return ""; // Return empty string if the range is invalid
        }

        return entry.substring(startIndex, endIndex).trim(); // Extract and return the desired field
    }

    /**
     * Calculates the average duration of all workouts.
     *
     * @return The average activity time in minutes. Returns 0.0 if there are no workouts.
     */
    private double calculateAverageActivityTime() {
        return userWorkouts.stream()
                .mapToDouble(Workout::getDuration)
                .average()
                .orElse(0.0);
    }

    /**
     * Calculates the average calories burned per workout.
     *
     * @return The average calories burned. Returns 0.0 if there are no workouts.
     */
    private double calculateAverageActivityCalories() {
        return userWorkouts.stream()
                .mapToDouble(Workout::getCaloriesBurned)
                .average()
                .orElse(0.0);
    }

    /**
     * Calculates the total duration of all workouts.
     *
     * @return The total activity time in minutes.
     */
    private double calculateTotalActivityTime() {
        return userWorkouts.stream()
                .mapToDouble(Workout::getDuration)
                .sum();
    }

    /**
     * Calculates the total calories burned across all workouts.
     *
     * @return The total burned calories.
     */
    private double calculateTotalBurnedCalories() {
        return userWorkouts.stream()
                .mapToDouble(Workout::getCaloriesBurned)
                .sum();
    }

    /**
     * Finds the workout with the longest duration.
     *
     * @return The longest {@code Workout}. Returns {@code null} if there are no workouts.
     */
    private Workout findTheLongestActivity() {
        return userWorkouts.stream()
                .max(Comparator.comparingDouble(Workout::getDuration))
                .orElse(null);
    }

    /**
     * Finds the workout with the shortest duration.
     *
     * @return The shortest {@code Workout}. Returns {@code null} if there are no workouts.
     */
    private Workout findTheShortestActivity() {
        return userWorkouts.stream()
                .min(Comparator.comparingDouble(Workout::getDuration))
                .orElse(null);
    }

    /**
     * The {@code Workout} class represents a single workout session, containing details such as
     * the workout name, calories burned, duration, distance, and speed.
     */
    private static class Workout {
        private final String name;             // Name of the workout (e.g., Running, Cycling)
        private final double caloriesBurned;   // Calories burned during the workout
        private final double duration;         // Duration of the workout in minutes
        private final double distance;         // Distance covered during the workout in kilometers or repetitions for Rope Jumping
        private final double speed;            // Speed achieved during the workout in km/h

        /**
         * Constructs a {@code Workout} with the specified details.
         *
         * @param name           The name of the workout.
         * @param caloriesBurned The number of calories burned.
         * @param duration       The duration of the workout in minutes.
         * @param distance       The distance covered in kilometers or repetitions.
         * @param speed          The speed achieved in kilometers per hour.
         */
        public Workout(String name, double caloriesBurned, double duration, double distance, double speed) {
            this.name = name;
            this.caloriesBurned = caloriesBurned;
            this.duration = duration;
            this.distance = distance;
            this.speed = speed;
        }

        /**
         * Retrieves the name of the workout.
         *
         * @return A {@code String} representing the workout name.
         */
        public String getName() {
            return name;
        }

        /**
         * Retrieves the number of calories burned during the workout.
         *
         * @return A {@code double} representing calories burned.
         */
        public double getCaloriesBurned() {
            return caloriesBurned;
        }

        /**
         * Retrieves the duration of the workout.
         *
         * @return A {@code double} representing the duration in minutes.
         */
        public double getDuration() {
            return duration;
        }

        /**
         * Retrieves the distance covered during the workout.
         *
         * @return A {@code double} representing the distance in kilometers or repetitions.
         */
        public double getDistance() {
            return distance;
        }

        /**
         * Retrieves the speed achieved during the workout.
         *
         * @return A {@code double} representing the speed in kilometers per hour.
         */
        public double getSpeed() {
            return speed;
        }

        /**
         * Retrieves the number of repetitions for Rope Jumping workouts.
         *
         * @return An {@code int} representing repetitions. Returns 0 for other workout types.
         */
        public int getRepetitions() {
            if (name.equalsIgnoreCase("Rope Jumping")) {
                return (int) distance; // distance is used to store repetitions for Rope Jumping
            }
            return 0; // 0 for other workout types
        }
    }
}
