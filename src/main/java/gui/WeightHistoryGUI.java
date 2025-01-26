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
import java.io.*;
import java.util.ArrayList;

/**
 * The {@code WeightHistoryGUI} class provides a graphical user interface for users to view their weight history.
 * It displays a line chart representing the user's weight over time. The class reads weight data from a file,
 * processes it, and visualizes it using JFreeChart. It also includes navigation buttons to other sections of
 * the Fitness Tracker application.
 */
public class WeightHistoryGUI {

    private String userUUID; // Stores the UUID of the currently logged-in user
    private ArrayList<WeightEntry> weightEntries = new ArrayList<>(); // List to store weight entries

    /**
     * Constructs the {@code WeightHistoryGUI} and initializes the weight history interface.
     * It loads the user's weight data, sets up the main frame, and configures navigation buttons and the weight chart.
     */
    public WeightHistoryGUI() {
        userUUID = LoginGUI.loggedInUserUUID; // Retrieve the UUID of the currently logged-in user
        loadWeightDataFromFile("src/main/resources/weightsData.txt"); // Load weight data from the specified file

        // Create the main application frame
        JFrame frame = new JFrame("Fitness Tracker - Weight History");
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
        // CENTER PANEL (Weight History Chart)
        // ----------------
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(230, 220, 250)); // Light purple background

        // Create dataset for the weight history chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (WeightEntry we : weightEntries) {
            // "Weight" is the series name, we.getDate() is the category (X-axis)
            dataset.addValue(we.getWeight(), "Weight", we.getDate());
        }

        // Create a line chart using the dataset
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Weight History", // Chart title
                "Date",           // X-axis label
                "Weight (kg)",    // Y-axis label
                dataset,          // Dataset
                PlotOrientation.VERTICAL, // Plot orientation
                false, // Include legend
                true,  // Include tooltips
                false  // URLs
        );

        // 1. Set the background of the entire chart area to light purple
        Color lightPurple = new Color(230, 220, 250);
        lineChart.setBackgroundPaint(lightPurple);

        // 2. Retrieve the CategoryPlot object (the plotting area)
        CategoryPlot plot = lineChart.getCategoryPlot();

        // 3. Set the background of the plot area to an even lighter purple
        Color lighterPurple = new Color(240, 230, 255);
        plot.setBackgroundPaint(lighterPurple);

        // 4. Set the gridline colors (optional: gray lines)
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);

        // 5. Retrieve the renderer (default is LineAndShapeRenderer)
        org.jfree.chart.renderer.category.LineAndShapeRenderer renderer =
                (org.jfree.chart.renderer.category.LineAndShapeRenderer) plot.getRenderer();

        // 6. Set the series color to dark purple and make the line thicker
        renderer.setSeriesPaint(0, darkPurple);
        renderer.setSeriesStroke(0, new BasicStroke(3.0f)); // 3-pixel thick lines

        // 7. Change fonts to Comic Sans for various chart components
        Font comicSansPlain = new Font("Comic Sans MS", Font.PLAIN, 12);
        Font comicSansBold  = new Font("Comic Sans MS", Font.BOLD, 14);

        // - Chart title font
        if (lineChart.getTitle() != null) {
            lineChart.getTitle().setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        }

        // - X-axis label font
        plot.getDomainAxis().setLabelFont(comicSansBold);
        // - X-axis tick labels font
        plot.getDomainAxis().setTickLabelFont(comicSansPlain);

        // - Y-axis label font
        plot.getRangeAxis().setLabelFont(comicSansBold);
        // - Y-axis tick labels font
        plot.getRangeAxis().setTickLabelFont(comicSansPlain);

        // 8. Optionally, set the axis line colors to dark purple
        plot.getDomainAxis().setAxisLinePaint(darkPurple);
        plot.getRangeAxis().setAxisLinePaint(darkPurple);

        // Create a ChartPanel to display the chart
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(800, 500)); // Set preferred size for the chart
        centerPanel.add(chartPanel, BorderLayout.CENTER); // Add the chart to the center panel

        // Add the center panel to the main frame
        frame.add(centerPanel, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);
    }

    /**
     * Loads weight data from the specified file for the currently logged-in user.
     * The file format is expected to be:
     * uuid;date1~weight1;date2~weight2;...
     *
     * @param fileName The path to the weightsData.txt file.
     */
    private void loadWeightDataFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("weightsData.txt does not exist or is empty.");
            return; // Exit if the file does not exist
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Skip empty lines
                String[] parts = line.split(";");
                if (parts.length < 2) continue; // Ensure there are enough parts

                String uuidFromFile = parts[0];
                if (!uuidFromFile.equals(userUUID)) {
                    // Skip entries that do not belong to the current user
                    continue;
                }

                // From index 1 to the end, we have entries in the format "date~weight"
                for (int i = 1; i < parts.length; i++) {
                    String entry = parts[i];
                    if (entry.contains("~")) {
                        String[] subParts = entry.split("~");
                        if (subParts.length == 2) {
                            String date = subParts[0];
                            double weightVal = 0;
                            try {
                                weightVal = Double.parseDouble(subParts[1]);
                            } catch (NumberFormatException e) {
                                e.printStackTrace(); // Print stack trace if parsing fails
                            }
                            weightEntries.add(new WeightEntry(date, weightVal)); // Add the weight entry to the list
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if an IO error occurs
        }
    }

    /**
     * A simple class to store weight entries, consisting of a date and the corresponding weight value.
     */
    private static class WeightEntry {
        private final String date;   // The date of the weight entry
        private final double weight; // The weight value

        /**
         * Constructs a {@code WeightEntry} with the specified date and weight.
         *
         * @param date   The date of the weight entry.
         * @param weight The weight value.
         */
        public WeightEntry(String date, double weight) {
            this.date = date;
            this.weight = weight;
        }

        /**
         * Retrieves the date of the weight entry.
         *
         * @return A {@code String} representing the date.
         */
        public String getDate() {
            return date;
        }

        /**
         * Retrieves the weight value of the weight entry.
         *
         * @return A {@code double} representing the weight.
         */
        public double getWeight() {
            return weight;
        }
    }
}
