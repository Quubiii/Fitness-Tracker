package main.java.gui.coachgui;

import main.java.gui.LoginGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code SessionRequestsGUI} class creates a graphical user interface
 * that allows coaches to view and manage session requests from students.
 * It displays pending requests in a JTable with options to accept or dismiss each request.
 */
public class SessionRequestsGUI {

    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;

    /**
     * Constructs the SessionRequestsGUI and initializes the GUI components.
     * It checks for a logged-in coach, fetches the associated session requests,
     * and displays them in a table with action buttons.
     */
    public SessionRequestsGUI() {
        // Logged-in user UUID holder
        String coachID = LoginGUI.loggedInCoachID;

        // Validate that a coach is logged in
        if (coachID == null || coachID.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Error: Unable to determine the logged-in coach.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create the main application frame
        frame = new JFrame("Fitness Tracker - Session Requests");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 600);
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

        // Initialize the center panel to display session requests
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(230, 220, 250));

        // Fetch the list of session requests associated with the coach
        List<String[]> requests = getCoachRequests(coachID);

        // Define the table columns
        String[] columnNames = {"Name", "Weight (kg)", "Height (cm)", "Age", "Gender", "UUID", "Actions"};

        // Initialize the table model with column names and disable editing for all columns except "Actions"
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only the "Actions" column is editable
            }
        };

        // Populate the table model with request data
        for (String[] request : requests) {
            Object[] rowData = {
                    request[0], // Name
                    request[1], // Weight
                    request[2], // Height
                    request[3], // Age
                    request[4], // Gender
                    request[5], // UUID (hidden column)
                    "Actions"   // Button text placeholder
            };
            tableModel.addRow(rowData);
        }

        // Create the JTable with the table model
        table = new JTable(tableModel);
        table.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        table.setRowHeight(30);

        // Hide the UUID column (index 5) as it's used internally
        table.getColumnModel().getColumn(5).setMinWidth(0);
        table.getColumnModel().getColumn(5).setMaxWidth(0);
        table.getColumnModel().getColumn(5).setWidth(0);

        // Set custom renderer and editor for the "Actions" column to include buttons
        table.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(coachID));

        // Add the table to a scroll pane and then to the center panel
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(centerPanel, BorderLayout.CENTER); // Add the center panel to the frame
        frame.setVisible(true); // Make the frame visible
    }

    /**
     * Retrieves the list of session requests associated with a given coach.
     * It reads from the coachesRequests.txt file to get student UUIDs
     * and then matches them with user data in usersData.txt.
     *
     * @param coachID The UUID of the coach.
     * @return A list of request data arrays containing name, weight, height, age, gender, and UUID.
     */
    private List<String[]> getCoachRequests(String coachID) {
        List<String[]> requests = new ArrayList<>();
        String coachRequestsFile = "src/main/resources/coachesRequests.txt";
        String userDataFile = "src/main/resources/usersData.txt";

        List<String> studentUUIDs = new ArrayList<>();

        // Read the coachesRequests.txt file to find student UUIDs requesting sessions with the coach
        try (BufferedReader br = new BufferedReader(new FileReader(coachRequestsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(coachID + ";")) { // Check if the line corresponds to the coach
                    String[] parts = line.split(";");
                    for (int i = 1; i < parts.length; i++) { // Start from 1 to skip the coachID
                        studentUUIDs.add(parts[i]); // Add each student UUID to the list
                    }
                    break; // Exit after finding the relevant coach entry
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Match student UUIDs with user data in usersData.txt to retrieve detailed information
        try (BufferedReader br = new BufferedReader(new FileReader(userDataFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length < 6) continue; // Ensure the line has all required fields

                String uuid = parts[0];
                if (studentUUIDs.contains(uuid)) { // Check if the user has requested a session
                    requests.add(new String[]{
                            parts[1],                // Name
                            parts[2],                // Weight
                            parts[3],                // Height
                            parts[4],                // Age
                            parts[5],                // Gender
                            uuid                     // UUID
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return requests; // Return the list of session requests
    }

    /**
     * The {@code ButtonRenderer} class implements {@code TableCellRenderer}
     * to render custom buttons ("Accept" and "Dismiss") in the table cells.
     */
    private class ButtonRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            // Create a panel to hold the buttons
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));

            // Create "Accept" and "Dismiss" buttons
            JButton acceptButton = new JButton("Accept");
            JButton dismissButton = new JButton("Dismiss");

            panel.add(acceptButton);
            panel.add(dismissButton);

            // Set background color based on selection
            if (isSelected) {
                panel.setBackground(table.getSelectionBackground());
            } else {
                panel.setBackground(table.getBackground());
            }

            return panel; // Return the panel containing the buttons
        }
    }

    /**
     * The {@code ButtonEditor} class extends {@code AbstractCellEditor}
     * and implements {@code TableCellEditor} to handle button actions
     * within the table cells.
     */
    private class ButtonEditor extends AbstractCellEditor implements TableCellEditor {

        private JPanel panel;
        private JButton acceptButton;
        private JButton dismissButton;

        private String studentUUID;
        private String coachID;
        private int editingRow;

        /**
         * Constructs the ButtonEditor with the specified coach ID.
         *
         * @param coachID The UUID of the coach.
         */
        public ButtonEditor(String coachID) {
            this.coachID = coachID;
            initPanel();
        }

        /**
         * Initializes the panel and buttons, and sets up action listeners.
         */
        private void initPanel() {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));

            acceptButton = new JButton("Accept");
            dismissButton = new JButton("Dismiss");

            panel.add(acceptButton);
            panel.add(dismissButton);

            // Add action listener for the "Accept" button
            acceptButton.addActionListener(e -> handleAcceptRequest());

            // Add action listener for the "Dismiss" button
            dismissButton.addActionListener(e -> handleDismissRequest());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.editingRow = row;
            this.studentUUID = (String) table.getValueAt(row, 5); // Retrieve the UUID from the hidden column
            return panel; // Return the panel containing the buttons
        }

        @Override
        public Object getCellEditorValue() {
            return null; // No value to return
        }

        /**
         * Handles the acceptance of a session request.
         * It moves the student to the coach's list and removes the request.
         */
        private void handleAcceptRequest() {
            moveStudentToCoach(coachID, studentUUID); // Add the student to the coach's list
            removeRequest(coachID, studentUUID);       // Remove the request from the pending list
            finishEditing();                           // Finalize the editing process
        }

        /**
         * Handles the dismissal of a session request.
         * It removes the request without adding the student to the coach's list.
         */
        private void handleDismissRequest() {
            removeRequest(coachID, studentUUID); // Remove the request from the pending list
            finishEditing();                      // Finalize the editing process
        }

        /**
         * Finalizes the editing process by stopping the cell editor and removing the row from the table.
         */
        private void finishEditing() {
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing(); // Stop editing the cell
            }
            tableModel.removeRow(editingRow); // Remove the row from the table model
        }
    }

    /**
     * Adds a student UUID to the coach's list in coachesStudents.txt.
     * If the coach does not already have an entry, a new one is created.
     *
     * @param coachID     The UUID of the coach.
     * @param studentUUID The UUID of the student to be added.
     */
    private void moveStudentToCoach(String coachID, String studentUUID) {
        String coachesStudentsFile = "src/main/resources/coachesStudents.txt";
        List<String> fileLines = new ArrayList<>();
        boolean foundCoach = false;

        // Read the coachesStudents.txt file to find the coach's current list of students
        try (BufferedReader br = new BufferedReader(new FileReader(coachesStudentsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(coachID + ";")) { // Check if the line corresponds to the coach
                    foundCoach = true;
                    if (!line.contains(";" + studentUUID)) { // Avoid duplicate entries
                        line += ";" + studentUUID; // Append the new student UUID
                    }
                }
                fileLines.add(line); // Add the line to the list
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If the coach does not have an entry, create a new one
        if (!foundCoach) {
            fileLines.add(coachID + ";" + studentUUID);
        }

        // Write the updated list back to the coachesStudents.txt file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(coachesStudentsFile, false))) {
            for (String line : fileLines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a student UUID from the coach's session requests in coachesRequests.txt.
     *
     * @param coachID     The UUID of the coach.
     * @param studentUUID The UUID of the student to be removed from requests.
     */
    private void removeRequest(String coachID, String studentUUID) {
        String coachRequestsFile = "src/main/resources/coachesRequests.txt";
        List<String> fileLines = new ArrayList<>();

        // Read the coachesRequests.txt file and remove the specified student UUID from the coach's requests
        try (BufferedReader br = new BufferedReader(new FileReader(coachRequestsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(coachID + ";")) { // Check if the line corresponds to the coach
                    String[] parts = line.split(";");
                    StringBuilder newLine = new StringBuilder(coachID);
                    for (int i = 1; i < parts.length; i++) {
                        if (!parts[i].equals(studentUUID)) { // Exclude the specified student UUID
                            newLine.append(";").append(parts[i]);
                        }
                    }
                    line = newLine.toString(); // Update the line without the removed UUID
                }
                fileLines.add(line); // Add the line to the list
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write the updated list back to the coachesRequests.txt file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(coachRequestsFile, false))) {
            for (String line : fileLines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
