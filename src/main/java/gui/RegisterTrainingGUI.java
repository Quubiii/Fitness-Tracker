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

public class RegisterTrainingGUI {
    private static final double MET_RUNNING = 7.0;
    private static final double MET_CYCLING = 6.0;
    private static final double MET_ROPE_JUMPING = 12.0;

    private double userWeight;
    String loggedInUUID = LoginGUI.loggedInUserUUID;

    public RegisterTrainingGUI(double userWeight) {
        this.userWeight = userWeight;

        JFrame frame = new JFrame("Fitness Tracker - Register Training");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(230, 220, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel welcomeLabel = new JLabel("REGISTER YOUR TRAINING", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(113, 54, 143));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel typeLabel = createCenteredLabel("Select Training Type:");
        JComboBox<String> trainingTypeComboBox = new JComboBox<>(new String[]{"Running", "Cycling", "Rope Jumping"});
        trainingTypeComboBox.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        trainingTypeComboBox.setMaximumSize(new Dimension(250, 30));
        trainingTypeComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel dateLabel = createCenteredLabel("Select Training Date:");

        UtilDateModel dateModel = new UtilDateModel();
        Properties dateProperties = new Properties();
        dateProperties.put("text.today", "Today");
        dateProperties.put("text.month", "Month");
        dateProperties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, dateProperties);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.getJFormattedTextField().setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        datePicker.setAlignmentX(Component.CENTER_ALIGNMENT);
        datePicker.setMaximumSize(new Dimension(250, 30));

        JLabel durationLabel = createCenteredLabel("Training Duration (minutes):");
        JTextField durationField = createTextField();

        JLabel distanceLabel = createCenteredLabel("Covered Distance (km):");
        JTextField distanceField = createTextField();
        distanceField.setEnabled(false); // Domyślnie wyłączone

        JLabel intensityLabel = createCenteredLabel("Select Intensity:");
        JComboBox<String> intensityComboBox = new JComboBox<>(new String[]{"Low", "Medium", "High"});
        intensityComboBox.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        intensityComboBox.setMaximumSize(new Dimension(250, 30));
        intensityComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton submitButton = new JButton("SUBMIT");
        submitButton.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        submitButton.setForeground(new Color(113, 54, 143));
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel caloriesLabel = new JLabel("", SwingConstants.CENTER);
        caloriesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

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

        frame.add(panel, BorderLayout.CENTER);
        frame.setPreferredSize(new Dimension(500, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        trainingTypeComboBox.addActionListener(e -> {
            String selectedType = (String) trainingTypeComboBox.getSelectedItem();
            if ("Running".equals(selectedType) || "Cycling".equals(selectedType)) {
                distanceField.setEnabled(true);
            } else {
                distanceField.setEnabled(false);
                distanceField.setText("");
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String trainingType = (String) trainingTypeComboBox.getSelectedItem();
                    java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
                    if (selectedDate == null) {
                        throw new IllegalArgumentException("Please select a valid date.");
                    }
                    LocalDateTime startTime = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    int duration = Integer.parseInt(durationField.getText());
                    String intensity = (String) intensityComboBox.getSelectedItem();

                    double metValue = getMetValue(trainingType, intensity);
                    double caloriesBurned = calculateCaloriesBurned(metValue, duration);

                    Activity activity;
                    if ("Running".equals(trainingType)) {
                        double distance = Double.parseDouble(distanceField.getText());
                        double avgSpeed = distance / (duration / 60.0);
                        activity = new RunningActivity(UUID.randomUUID().hashCode(), "Running", caloriesBurned, duration, startTime, startTime.plusMinutes(duration), distance, avgSpeed);
                    } else if ("Cycling".equals(trainingType)) {
                        double distance = Double.parseDouble(distanceField.getText());
                        double avgSpeed = distance / (duration / 60.0);
                        double maxSpeed = avgSpeed + 5.0;
                        activity = new CyclingActivity(UUID.randomUUID().hashCode(), "Cycling", caloriesBurned, duration, startTime, startTime.plusMinutes(duration), distance, maxSpeed);
                    } else {
                        // Calculate repetitions
                        int repetitions = calculateRepetitions(duration);
                        activity = new RopeJumpingActivity(UUID.randomUUID().hashCode(), "Rope Jumping", caloriesBurned, duration, startTime, startTime.plusMinutes(duration), repetitions);
                    }

                    saveTrainingToFile(activity);

                    // Update message and calories label
                    messageLabel.setText("Training saved successfully!");
                    caloriesLabel.setText("Calories Burned: " + String.format("%.2f", caloriesBurned) + " kcal");
                    messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                    messageLabel.setForeground(new Color(113, 54, 143));
                    caloriesLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                    caloriesLabel.setForeground(new Color(113, 54, 143));

                    // Add a redirect message after displaying stats
                    Timer timer = new Timer(5000, event -> {
                        messageLabel.setText("Redirecting to the main page...");
                        messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                        messageLabel.setForeground(new Color(113, 54, 143));

                        // Wait another 2 seconds for clarity
                        Timer redirectTimer = new Timer(2000, redirectEvent -> {
                            frame.dispose();
                            new WorkoutsGUI(); // Replace with MainGUI() if you intend to redirect to the main page
                        });
                        redirectTimer.setRepeats(false);
                        redirectTimer.start();
                    });
                    timer.setRepeats(false); // Ensure the timer only runs once
                    timer.start();

                } catch (NumberFormatException ex) {
                    messageLabel.setText("Duration and Distance must be numeric.");
                    messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                    messageLabel.setForeground(new Color(113, 54, 143));
                } catch (Exception ex) {
                    messageLabel.setText("Error: " + ex.getMessage());
                    messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
                    messageLabel.setForeground(new Color(113, 54, 143));
                }
            }
        });

    }

    private void saveTrainingToFile(Activity activity) {
        try {
            File file = new File("src/main/resources/trainingData.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            boolean userExists = false;
            StringBuilder fileContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith(loggedInUUID + ";")) {
                        userExists = true;
                        // Dodaj nowy trening oddzielając istniejące treningi znakiem "~"
                        line += "~" + activity.getAllInfo();
                    }
                    fileContent.append(line).append("\n");
                }
            }

            if (!userExists) {
                // Dodaj nowego użytkownika z pierwszym treningiem
                fileContent.append(loggedInUUID).append(";").append(activity.getAllInfo()).append("\n");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(fileContent.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save training data.", e);
        }
    }



    private JLabel createCenteredLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        label.setForeground(new Color(113, 54, 143));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        textField.setMaximumSize(new Dimension(250, 30));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        return textField;
    }

    private double getMetValue(String trainingType, String intensity) {
        double baseMet;
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
                baseMet = 1.0;
        }

        switch (intensity) {
            case "Low":
                return baseMet * 0.8;
            case "Medium":
                return baseMet;
            case "High":
                return baseMet * 1.2;
            default:
                return baseMet;
        }
    }

    private double calculateCaloriesBurned(double metValue, int duration) {
        return metValue * userWeight * (duration / 60.0);
    }

    private int calculateRepetitions(int durationMinutes) {
        // Przyjęte uogólnione tempo: 2 skoki na sekundę
        int repetitionsPerSecond = 2;
        int durationInSeconds = durationMinutes * 60;
        return repetitionsPerSecond * durationInSeconds;
    }

}
