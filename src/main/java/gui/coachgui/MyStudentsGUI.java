package main.java.gui.coachgui;

import main.java.gui.LoginGUI;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MyStudentsGUI} class creates a graphical user interface
 * that displays a list of students associated with the currently logged-in coach.
 * It retrieves student data from text files and presents it in a JTable.
 */
public class MyStudentsGUI {

    /**
     * Constructs the MyStudentsGUI and initializes the GUI components.
     * It checks for a logged-in coach, fetches the associated students,
     * and displays them in a table.
     */
    public MyStudentsGUI() {
        // Logged-in user UUID holder
        String coachID = LoginGUI.loggedInCoachID;

        // Validate that a coach is logged in
        if (coachID == null || coachID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: Unable to determine the logged-in coach.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create the main application frame
        JFrame frame = new JFrame("Fitness Tracker - My Students");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setLayout(new BorderLayout());

        // Initialize the top panel with a back button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Color darkPurple = new Color(113, 54, 143);
        topPanel.setBackground(darkPurple);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        backButton.setForeground(darkPurple);

        topPanel.add(backButton);

        // Add action listener to handle back button clicks
        backButton.addActionListener(e -> {
            frame.dispose(); // Close the current frame
            new CoachGUI(); // Open the CoachGUI
        });

        frame.add(topPanel, BorderLayout.NORTH); // Add the top panel to the frame

        // Initialize the center panel to display student data
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(230, 220, 250));

        // Fetch the list of students associated with the coach
        List<String[]> students = getCoachStudents(coachID);
        String[] columnNames = {"Name", "Weight (kg)", "Height (cm)", "Age", "Gender", "BMI"};
        String[][] data = new String[students.size()][columnNames.length];

        // Populate the data array with student information
        for (int i = 0; i < students.size(); i++) {
            data[i] = students.get(i);
        }

        // Create a table to display the student data
        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);

        centerPanel.add(scrollPane, BorderLayout.CENTER); // Add the table to the center panel
        frame.add(centerPanel, BorderLayout.CENTER); // Add the center panel to the frame

        frame.setVisible(true); // Make the frame visible
    }

    /**
     * Retrieves the list of students associated with a given coach.
     * It reads from the coachesStudents.txt file to get student UUIDs
     * and then matches them with user data in usersData.txt.
     *
     * @param coachID The UUID of the coach.
     * @return A list of student data arrays containing name, weight, height, age, gender, and BMI.
     */
    private List<String[]> getCoachStudents(String coachID) {
        List<String[]> students = new ArrayList<>();
        String coachStudentsFile = "src/main/resources/coachesStudents.txt";
        String userDataFile = "src/main/resources/usersData.txt";

        List<String> studentUUIDs = new ArrayList<>();

        // Reading coachesStudents.txt file to find student UUIDs for the given coach
        try (BufferedReader br = new BufferedReader(new FileReader(coachStudentsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(coachID)) { // Check if the line corresponds to the coach
                    String[] parts = line.split(";");
                    for (int i = 1; i < parts.length; i++) { // Start from 1 to skip the coachID
                        studentUUIDs.add(parts[i]); // Add each student UUID to the list
                    }
                    break; // Exit after finding the relevant coach entry
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading coach's students file: " + e.getMessage());
        }

        // Match student UUIDs with user data in usersData.txt
        try (BufferedReader br = new BufferedReader(new FileReader(userDataFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (studentUUIDs.contains(parts[0])) { // Check if the user is a student of the coach
                    String name = parts[1];
                    double weight = Double.parseDouble(parts[2]);
                    double height = Double.parseDouble(parts[3]) / 100.0; // Convert cm to meters
                    int age = Integer.parseInt(parts[4]);
                    String gender = parts[5];

                    double bmi = calculateBMI(weight, height); // Calculate BMI

                    // Add the student's data to the list in the required format
                    students.add(new String[]{
                            name,
                            String.format("%.1f", weight),
                            String.format("%.0f", height * 100),
                            String.valueOf(age),
                            gender,
                            String.format("%.2f", bmi)
                    });
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user data file: " + e.getMessage());
        }

        return students; // Return the list of students
    }

    /**
     * Calculates the Body Mass Index (BMI) given weight and height.
     *
     * @param weight The weight of the individual in kilograms.
     * @param height The height of the individual in meters.
     * @return The calculated BMI.
     */
    private double calculateBMI(double weight, double height) {
        return weight / (height * height);
    }
}
