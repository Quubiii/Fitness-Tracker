package main.java.gui.coachgui;

import main.java.gui.LoginGUI;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyStudentsGUI {

    public MyStudentsGUI() {
        //Logged-in user UUID holder
        String coachID = LoginGUI.loggedInCoachID;

        if (coachID == null || coachID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: Unable to determine the logged-in coach.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame frame = new JFrame("Fitness Tracker - My Students");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        //Top Panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Color darkPurple = new Color(113, 54, 143);
        topPanel.setBackground(darkPurple);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        backButton.setForeground(darkPurple);

        topPanel.add(backButton);

        backButton.addActionListener(e -> {
            frame.dispose();
            new CoachGUI();
        });

        frame.add(topPanel, BorderLayout.NORTH);

        //Center Panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(230, 220, 250));

        //Fetch Students
        List<String[]> students = getCoachStudents(coachID);
        String[] columnNames = {"Name", "Weight (kg)", "Height (cm)", "Age", "Gender", "BMI"};
        String[][] data = new String[students.size()][columnNames.length];

        for (int i = 0; i < students.size(); i++) {
            data[i] = students.get(i);
        }

        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);

        centerPanel.add(scrollPane, BorderLayout.CENTER);
        frame.add(centerPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private List<String[]> getCoachStudents(String coachID) {
        List<String[]> students = new ArrayList<>();
        String coachStudentsFile = "src/main/resources/coachesStudents.txt";
        String userDataFile = "src/main/resources/usersData.txt";

        List<String> studentUUIDs = new ArrayList<>();

        //Reading coachesStudents.txt file
        try (BufferedReader br = new BufferedReader(new FileReader(coachStudentsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(coachID)) {
                    String[] parts = line.split(";");
                    for (int i = 1; i < parts.length; i++) {
                        studentUUIDs.add(parts[i]);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading coach's students file: " + e.getMessage());
        }

        //Match UUID with usersData.txt
        try (BufferedReader br = new BufferedReader(new FileReader(userDataFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (studentUUIDs.contains(parts[0])) {
                    String name = parts[1];
                    double weight = Double.parseDouble(parts[2]);
                    double height = Double.parseDouble(parts[3]) / 100.0;
                    int age = Integer.parseInt(parts[4]);
                    String gender = parts[5];

                    double bmi = calculateBMI(weight, height);

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

        return students;
    }

    private double calculateBMI(double weight, double height) {
        return weight / (height * height);
    }
}
