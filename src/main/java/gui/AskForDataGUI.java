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

public class AskForDataGUI {
    private final String login;
    private final String password;

    // Przechowujemy dokładny czas założenia konta.
    private LocalDateTime accDate;

    public AskForDataGUI(String login, String password) {
        this.login = login;
        this.password = password;

        JFrame frame = new JFrame("Fitness Tracker - User Data");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(230, 220, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel titleLabel = new JLabel("FILL IN YOUR DATA");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        titleLabel.setForeground(new Color(113, 54, 143));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = createCenteredLabel("Name:");
        JTextField nameField = createTextField();

        JLabel weightLabel = createCenteredLabel("Weight (kg):");
        JTextField weightField = createTextField();

        JLabel heightLabel = createCenteredLabel("Height (cm):");
        JTextField heightField = createTextField();

        JLabel dobLabel = createCenteredLabel("Date of Birth:");

        // Date Picker
        UtilDateModel dateModel = new UtilDateModel();
        Properties dateProperties = new Properties();
        dateProperties.put("text.today", "Today");
        dateProperties.put("text.month", "Month");
        dateProperties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, dateProperties);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.getJFormattedTextField().setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        datePicker.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel genderLabel = createCenteredLabel("Gender:");
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        genderComboBox.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        genderComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton submitButton = new JButton("SUBMIT");
        submitButton.setForeground(new Color(113, 54, 143));
        submitButton.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Dodawanie komponentów do panelu
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

        frame.add(panel, BorderLayout.CENTER);
        frame.setPreferredSize(new Dimension(500, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // -- Obsługa przycisku SUBMIT --
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

                // Sprawdzanie, czy pola nie są puste
                if (name.isEmpty() || dob == null || gender == null) {
                    throw new IllegalArgumentException("Fields cannot be empty.");
                }
                // Zabezpieczenie, aby nie można było wybrać dzisiejszej ani młodszej daty
                if (!dob.isBefore(LocalDate.now())) {
                    throw new IllegalArgumentException("Date of Birth cannot be today or a future date.");
                }

                // Tworzymy użytkownika w pliku usersLoginData.txt, pobierając jego UUID
                String userUUID = RegisterGUI.addNewUser(login, password);
                LoginGUI.loggedInUserUUID = userUUID;

                // Ustawiamy dokładny czas założenia konta
                accDate = LocalDateTime.now();

                // Wyliczanie wieku
                int age = Period.between(dob, LocalDate.now()).getYears();

                // Formatowanie daty założenia konta (np. "2025-01-20 14:33:05")
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String creationDateStr = accDate.format(formatter);

                // Kompletujemy dane do zapisu w pliku usersData.txt
                // Format: uuid;Imię;Waga;Wzrost;Wiek;Płeć;DATA_ZAŁOŻENIA_KONTA
                String userDataLine = userUUID + ";" + name + ";"
                        + weightValue + ";" + heightValue + ";"
                        + age + ";" + gender + ";" + creationDateStr + ";" + creationDateStr;

                // Zapis do pliku usersData.txt (dopisywanie na końcu)
                try (FileWriter writer = new FileWriter("src/main/resources/usersData.txt", true)) {
                    writer.write(userDataLine + System.lineSeparator());
                } catch (IOException ioex) {
                    ioex.printStackTrace();
                    // Komunikat w razie błędu zapisu do pliku
                    messageLabel.setText("Error writing data to file!");
                    messageLabel.setForeground(Color.RED);
                    return;
                }

                // Jeśli doszło tu, znaczy, że rejestracja i zapisy się udały
                messageLabel.setText("Data submitted successfully!");
                messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
                messageLabel.setForeground(Color.GREEN);

                // Zamykamy okno
                frame.dispose();
                new MainGUI();

            } catch (NumberFormatException ex) {
                messageLabel.setText("Weight and Height must be numeric.");
                messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
                messageLabel.setForeground(Color.RED);
            } catch (IllegalArgumentException ex) {
                messageLabel.setText(ex.getMessage());
                messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
                messageLabel.setForeground(Color.RED);
            }
        });

        // Zmiana kursora myszy na "rączkę" przy najechaniu
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
     * Zwraca datę i czas założenia konta (ustawianą w momencie sukcesu SUBMIT).
     */
    public LocalDateTime getAccDate() {
        return accDate;
    }

    // Metody pomocnicze dla komponentów
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
}
