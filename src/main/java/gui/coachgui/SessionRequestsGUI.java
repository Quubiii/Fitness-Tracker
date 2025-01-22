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

public class SessionRequestsGUI {

    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;

    public SessionRequestsGUI() {
        //Logged-in UUID holder
        String coachID = LoginGUI.loggedInCoachID;

        if (coachID == null || coachID.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Error: Unable to determine the logged-in coach.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        frame = new JFrame("Fitness Tracker - Session Requests");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 600);
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

        //Requests fetching
        List<String[]> requests = getCoachRequests(coachID);

        String[] columnNames = {"Name", "Weight (kg)", "Height (cm)", "Age", "Gender", "UUID", "Actions"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };

        for (String[] request : requests) {
            Object[] rowData = {
                    request[0], // Name
                    request[1], // Weight
                    request[2], // Height
                    request[3], // Age
                    request[4], // Gender
                    request[5], // UUID (hidden column)
                    "Actions"   // Button text
            };
            tableModel.addRow(rowData);
        }

        table = new JTable(tableModel);
        table.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        table.setRowHeight(30);

        //Hide the UUID column (index 5)
        table.getColumnModel().getColumn(5).setMinWidth(0);
        table.getColumnModel().getColumn(5).setMaxWidth(0);
        table.getColumnModel().getColumn(5).setWidth(0);

        table.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(coachID));

        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private List<String[]> getCoachRequests(String coachID) {
        List<String[]> requests = new ArrayList<>();
        String coachRequestsFile = "src/main/resources/coachesRequests.txt";
        String userDataFile = "src/main/resources/usersData.txt";

        List<String> studentUUIDs = new ArrayList<>();

        //Read the requests file
        try (BufferedReader br = new BufferedReader(new FileReader(coachRequestsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(coachID + ";")) {
                    String[] parts = line.split(";");
                    for (int i = 1; i < parts.length; i++) {
                        studentUUIDs.add(parts[i]);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Map UUIDs to user details
        try (BufferedReader br = new BufferedReader(new FileReader(userDataFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length < 6) continue;

                String uuid = parts[0];
                if (studentUUIDs.contains(uuid)) {
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

        return requests;
    }

    private class ButtonRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));

            JButton acceptButton = new JButton("Accept");
            JButton dismissButton = new JButton("Dismiss");

            panel.add(acceptButton);
            panel.add(dismissButton);

            if (isSelected) {
                panel.setBackground(table.getSelectionBackground());
            } else {
                panel.setBackground(table.getBackground());
            }

            return panel;
        }
    }

    private class ButtonEditor extends AbstractCellEditor implements TableCellEditor {

        private JPanel panel;
        private JButton acceptButton;
        private JButton dismissButton;

        private String studentUUID;
        private String coachID;
        private int editingRow;

        public ButtonEditor(String coachID) {
            this.coachID = coachID;
            initPanel();
        }

        private void initPanel() {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));

            acceptButton = new JButton("Accept");
            dismissButton = new JButton("Dismiss");

            panel.add(acceptButton);
            panel.add(dismissButton);

            acceptButton.addActionListener(e -> handleAcceptRequest());
            dismissButton.addActionListener(e -> handleDismissRequest());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.editingRow = row;
            this.studentUUID = (String) table.getValueAt(row, 5);
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }

        private void handleAcceptRequest() {
            moveStudentToCoach(coachID, studentUUID);
            removeRequest(coachID, studentUUID);
            finishEditing();
        }

        private void handleDismissRequest() {
            removeRequest(coachID, studentUUID);
            finishEditing();
        }

        private void finishEditing() {
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
            tableModel.removeRow(editingRow);
        }
    }

    private void moveStudentToCoach(String coachID, String studentUUID) {
        String coachesStudentsFile = "src/main/resources/coachesStudents.txt";
        List<String> fileLines = new ArrayList<>();
        boolean foundCoach = false;

        try (BufferedReader br = new BufferedReader(new FileReader(coachesStudentsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(coachID + ";")) {
                    foundCoach = true;
                    if (!line.contains(";" + studentUUID)) {
                        line += ";" + studentUUID;
                    }
                }
                fileLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!foundCoach) {
            fileLines.add(coachID + ";" + studentUUID);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(coachesStudentsFile, false))) {
            for (String line : fileLines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeRequest(String coachID, String studentUUID) {
        String coachRequestsFile = "src/main/resources/coachesRequests.txt";
        List<String> fileLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(coachRequestsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(coachID + ";")) {
                    String[] parts = line.split(";");
                    StringBuilder newLine = new StringBuilder(coachID);
                    for (int i = 1; i < parts.length; i++) {
                        if (!parts[i].equals(studentUUID)) {
                            newLine.append(";").append(parts[i]);
                        }
                    }
                    line = newLine.toString();
                }
                fileLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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
