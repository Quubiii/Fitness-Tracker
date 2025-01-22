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

public class WorkoutStatsGUI {

    private String userUUID;
    private ArrayList<Workout> userWorkouts = new ArrayList<>();
    private Workout mostIntenseWorkout;

    public WorkoutStatsGUI() {
        // Get the UUID of the logged-in user
        userUUID = LoginGUI.loggedInUserUUID;

        // Load workout data from the file
        loadWorkoutDataFromFile("src/main/resources/trainingData.txt");

        JFrame frame = new JFrame("Fitness Tracker - Workout Stats");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // ----------------
        // TOP PANEL (Navigation) - analogicznie jak w AboutMeGUI
        // ----------------
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        Color darkPurple = new Color(113, 54, 143);
        topPanel.setBackground(darkPurple);

        JButton mainSiteButton = new JButton("MAIN");
        JButton profileButton = new JButton("PROFILE");
        JButton coachesButton = new JButton("OUR COACHES");
        JButton workoutsButton = new JButton("WORKOUTS");

        mainSiteButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        mainSiteButton.setForeground(darkPurple);
        profileButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        profileButton.setForeground(darkPurple);
        coachesButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        coachesButton.setForeground(darkPurple);
        workoutsButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        workoutsButton.setForeground(darkPurple);

        topPanel.add(mainSiteButton);
        topPanel.add(profileButton);
        topPanel.add(coachesButton);
        topPanel.add(workoutsButton);

        // Profile menu (hover over PROFILE)
        JPopupMenu profileMenu = new JPopupMenu();
        JMenuItem aboutMe = new JMenuItem("About Me");
        aboutMe.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        aboutMe.setBackground(new Color(230, 220, 250));
        aboutMe.setForeground(darkPurple);

        JMenuItem workoutStats = new JMenuItem("Workout Stats");
        workoutStats.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        workoutStats.setBackground(new Color(230, 220, 250));
        workoutStats.setForeground(darkPurple);

        // Opcja WeightHistory - ale już tu jesteśmy, ewentualnie można pominąć
        JMenuItem weightHistory = new JMenuItem("Weight History");
        weightHistory.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        weightHistory.setBackground(new Color(230, 220, 250));
        weightHistory.setForeground(darkPurple);

        JMenuItem logOut = new JMenuItem("Log Out");
        logOut.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        logOut.setBackground(new Color(230, 220, 250));
        logOut.setForeground(darkPurple);

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

        // Show profile menu on hover
        profileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                profileMenu.show(profileButton, 0, profileButton.getHeight());
            }
        });

        // MAIN
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

        // OUR COACHES
        coachesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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

        // WORKOUTS
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

        frame.add(topPanel, BorderLayout.NORTH);

        // ----------------
        // CENTER PANEL (Workout Data)
        // ----------------
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(230, 220, 250));

        // Left panel: Most intense workout
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(230, 220, 250));

        JLabel intenseWorkoutLabel = new JLabel("Most Intense Workout:");
        intenseWorkoutLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        intenseWorkoutLabel.setForeground(darkPurple);
        leftPanel.add(intenseWorkoutLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        if (mostIntenseWorkout != null) {
            JLabel workoutDetails = new JLabel("<html>"
                    + "Name: " + mostIntenseWorkout.getName() + "<br>"
                    + "Calories Burned: " + mostIntenseWorkout.getCaloriesBurned() + "<br>"
                    + "Duration: " + mostIntenseWorkout.getDuration() + " minutes<br>"
                    + "Distance: " + mostIntenseWorkout.getDistance() + " km<br>"
                    + "Speed: " + mostIntenseWorkout.getSpeed() + " km/h<br>"
                    + "</html>");
            workoutDetails.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
            workoutDetails.setForeground(darkPurple);
            leftPanel.add(workoutDetails);
        } else {
            JLabel noDataLabel = new JLabel("No workout data found.");
            noDataLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
            noDataLabel.setForeground(darkPurple);
            leftPanel.add(noDataLabel);
        }

        centerPanel.add(leftPanel, BorderLayout.WEST);

        // Right panel: Chart of total calories burned by activity type
        if (!userWorkouts.isEmpty()) {
            Map<String, Double> caloriesByType = userWorkouts.stream()
                    .collect(Collectors.groupingBy(
                            Workout::getName,
                            Collectors.summingDouble(Workout::getCaloriesBurned)
                    ));

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (Map.Entry<String, Double> entry : caloriesByType.entrySet()) {
                dataset.addValue(entry.getValue(), "Calories Burned", entry.getKey());
            }

            JFreeChart barChart = ChartFactory.createBarChart(
                    "Total Calories Burned by Activity",
                    "Activity Type",
                    "Calories Burned",
                    dataset,
                    PlotOrientation.VERTICAL,
                    false, true, false);

            barChart.setBackgroundPaint(new Color(230, 220, 250));
            CategoryPlot plot = barChart.getCategoryPlot();
            plot.setBackgroundPaint(new Color(230, 220, 250));
            plot.setDomainGridlinePaint(Color.GRAY);
            plot.setRangeGridlinePaint(Color.GRAY);

            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setSeriesPaint(0, new Color(113, 54, 143));

            ChartPanel chartPanel = new ChartPanel(barChart);
            chartPanel.setPreferredSize(new Dimension(600, 400));

            centerPanel.add(chartPanel, BorderLayout.CENTER);
        }

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void loadWorkoutDataFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.err.println("Workout data file not found: " + fileName);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) continue;

                // Rozdziel UUID od danych treningowych
                String[] parts = line.split(";");
                String uuidFromFile = parts[0];

                if (!uuidFromFile.equals(userUUID)) continue; // Pomijaj dane innych użytkowników

                // Parsuj wszystkie treningi użytkownika
                String[] workouts = parts[1].split("~");
                for (String workoutEntry : workouts) {
                    parseActivity(workoutEntry);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseActivity(String activityEntry) {
        try {
            String name = extractField(activityEntry, "Name: ", "Burned calories:");
            double caloriesBurned = Double.parseDouble(extractField(activityEntry, "Burned calories: ", "Duration:").trim());
            double duration = Double.parseDouble(extractField(activityEntry, "Duration: ", "Distance").trim());

            if (name.equalsIgnoreCase("Running")) {
                double distance = Double.parseDouble(extractField(activityEntry, "Distance ran: ", "Average speed:").replace(" km", "").trim());
                double avgSpeed = Double.parseDouble(extractField(activityEntry, "Average speed: ", "").replace(" km/h", "").trim());
                Workout running = new Workout(name, caloriesBurned, duration, distance, avgSpeed);
                userWorkouts.add(running);

            } else if (name.equalsIgnoreCase("Cycling")) {
                double distance = Double.parseDouble(extractField(activityEntry, "Distance cycled: ", "Maximum speed:").replace(" km", "").trim());
                double maxSpeed = Double.parseDouble(extractField(activityEntry, "Maximum speed: ", "").replace(" km/h", "").trim());
                Workout cycling = new Workout(name, caloriesBurned, duration, distance, maxSpeed);
                userWorkouts.add(cycling);

            } else if (name.equalsIgnoreCase("Rope Jumping")) {
                int repetitions = Integer.parseInt(extractField(activityEntry, "Repetitions: ", "Date:").trim());
                Workout ropeJumping = new Workout(name, caloriesBurned, duration, repetitions, 0);
                userWorkouts.add(ropeJumping);

            } else {
                System.err.println("Unsupported activity type: " + name);
            }

            // Aktualizuj najintensywniejszy trening
            if (mostIntenseWorkout == null || caloriesBurned > mostIntenseWorkout.getCaloriesBurned()) {
                mostIntenseWorkout = userWorkouts.get(userWorkouts.size() - 1);
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            System.err.println("Error parsing activity: " + activityEntry);
            e.printStackTrace();
        }
    }

    private String extractField(String entry, String startTag, String endTag) {
        int startIndex = entry.indexOf(startTag);
        if (startIndex == -1) {
            return ""; // Znacznik początkowy nie znaleziony
        }
        startIndex += startTag.length();

        int endIndex = endTag.isEmpty() ? entry.length() : entry.indexOf(endTag, startIndex);
        if (endIndex == -1) {
            endIndex = entry.length(); // Znacznik końcowy nie znaleziony
        }

        if (startIndex >= endIndex) {
            return ""; // Zakres jest nieprawidłowy
        }

        return entry.substring(startIndex, endIndex).trim();
    }

    private static class Workout {
        private final String name;
        private final double caloriesBurned;
        private final double duration;
        private final double distance;
        private final double speed;

        public Workout(String name, double caloriesBurned, double duration, double distance, double speed) {
            this.name = name;
            this.caloriesBurned = caloriesBurned;
            this.duration = duration;
            this.distance = distance;
            this.speed = speed;
        }

        public String getName() {
            return name;
        }

        public double getCaloriesBurned() {
            return caloriesBurned;
        }

        public double getDuration() {
            return duration;
        }

        public double getDistance() {
            return distance;
        }

        public double getSpeed() {
            return speed;
        }
    }
}
