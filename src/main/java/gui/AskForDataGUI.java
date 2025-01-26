package main.java.gui;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.Period;
import java.util.Properties;

import main.java.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 * The {@code AskForDataGUI} class creates a graphical user interface
 * that allows new users to input their personal data such as name, weight,
 * height, date of birth, and gender. Upon submission, the data is validated
 * and saved to the appropriate files. The class also handles the creation
 * of a new user account by generating a UUID and recording the account
 * creation date.
 */
public class AskForDataGUI {
    private final String login;
    private final String password;

    // Stores the exact time when the account was created.
    private LocalDateTime accDate;

    /**
     * Constructs the {@code AskForDataGUI} and initializes the GUI components.
     *
     * @param login    The login username of the user.
     * @param password The password of the user.
     */
    public AskForDataGUI(String login, String password) {
        this.login = login;
        this.password = password;

        // Create the main frame
        JFrame frame = new JFrame("Fitness Tracker - User Data");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(230, 220, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Title label
        JLabel titleLabel = new JLabel("FILL IN YOUR DATA");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        titleLabel.setForeground(new Color(113, 54, 143));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Name components
        JLabel nameLabel = createCenteredLabel("Name:");
        JTextField nameField = createTextField();

        // Weight components
        JLabel weightLabel = createCenteredLabel("Weight (kg):");
        JTextField weightField = createTextField();

        // Height components
        JLabel heightLabel = createCenteredLabel("Height (cm):");
        JTextField heightField = createTextField();

        // Date of Birth components
        JLabel dobLabel = createCenteredLabel("Date of Birth:");

        // Date Picker setup
        UtilDateModel dateModel = new UtilDateModel();
        Properties dateProperties = new Properties();
        dateProperties.put("text.today", "Today");
        dateProperties.put("text.month", "Month");
        dateProperties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, dateProperties);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.getJFormattedTextField().setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        datePicker.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Gender components
        JLabel genderLabel = createCenteredLabel("Gender:");
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        genderComboBox.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        genderComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Submit button
        JButton submitButton = new JButton("SUBMIT");
        submitButton.setForeground(new Color(113, 54, 143));
        submitButton.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Message label for feedback
        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adding components to the panel with spacing
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        panel.add(nameLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(nameField);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        panel.add(weightLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(weightField);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        panel.add(heightLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(heightField);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        panel.add(dobLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(datePicker);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        panel.add(genderLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(genderComboBox);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        panel.add(messageLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        panel.add(submitButton);
        panel.add(Box.createVerticalGlue());

        // Add the panel to the frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setPreferredSize(new Dimension(500, 600));
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // -- Handling the SUBMIT button click --
        submitButton.addActionListener(e -> {
            String name = nameField.getText();
            String weight = weightField.getText();
            String height = heightField.getText();
            java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
            LocalDate dob = null;

            if (selectedDate != null) {
                dob = selectedDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            }

            String gender = (String) genderComboBox.getSelectedItem();

            try {
                double weightValue = Double.parseDouble(weight);
                int heightValue = Integer.parseInt(height);

                // Check if fields are not empty
                if (name.isEmpty() || dob == null || gender == null) {
                    throw new IllegalArgumentException("Fields cannot be empty.");
                }
                // Ensure the date of birth is not today or a future date
                if (!dob.isBefore(LocalDate.now())) {
                    throw new IllegalArgumentException("Date of Birth cannot be today or a future date.");
                }

                // Create the user in the usersLoginData.txt file and get the UUID
                String userUUID = RegisterGUI.addNewUser(login, password);
                LoginGUI.loggedInUserUUID = userUUID;

                // Set the exact time of account creation
                accDate = LocalDateTime.now();

                // Calculate age
                int age = Period.between(dob, LocalDate.now()).getYears();

                // Format the account creation date (e.g., "2025-01-20 14:33:05")
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String creationDateStr = accDate.format(formatter);

                // Compile the data to be saved in usersData.txt
                // Format: uuid;Name;Weight;Height;Age;Gender;AccountCreationDate;CurrentWeightDate
                String userDataLine = userUUID + ";" + name + ";"
                        + weightValue + ";" + heightValue + ";"
                        + age + ";" + gender + ";" + creationDateStr + ";" + creationDateStr;

                // Write to usersData.txt (appending at the end)
                try (FileWriter writer = new FileWriter("src/main/resources/usersData.txt", true)) {
                    writer.write(userDataLine + System.lineSeparator());
                } catch (IOException ioex) {
                    ioex.printStackTrace();
                    // Display error message if writing to file fails
                    messageLabel.setText("Error writing data to file!");
                    messageLabel.setForeground(Color.RED);
                    return;
                }

                // If execution reaches here, registration and file writing were successful
                messageLabel.setText("Data submitted successfully!");
                messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
                messageLabel.setForeground(Color.GREEN);

                // Close the current window and open the main GUI
                frame.dispose();
                new MainGUI();

            } catch (NumberFormatException ex) {
                // Handle non-numeric weight and height inputs
                messageLabel.setText("Weight and Height must be numeric.");
                messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
                messageLabel.setForeground(Color.RED);
            } catch (IllegalArgumentException ex) {
                // Handle other input validation errors
                messageLabel.setText(ex.getMessage());
                messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
                messageLabel.setForeground(Color.RED);
            }
        });

        // Change cursor to hand cursor when hovering over the submit button
        submitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                submitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    /**
     * Returns the account creation date and time set during the successful submission.
     *
     * @return The {@code LocalDateTime} representing the account creation timestamp.
     */
    public LocalDateTime getAccDate() {
        return accDate;
    }

    /**
     * Creates a centered label with the specified text.
     *
     * @param text The text to be displayed on the label.
     * @return A {@code JLabel} configured with the specified text and styling.
     */
    private JLabel createCenteredLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        label.setForeground(new Color(113, 54, 143));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    /**
     * Creates a text field with predefined styling.
     *
     * @return A {@code JTextField} configured with the specified styling.
     */
    private JTextField createTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        textField.setMaximumSize(new Dimension(250, 30));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        return textField;
    }
}
