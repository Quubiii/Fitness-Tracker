package main.java.gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class RegisterGUI {
    private static HashMap<String, String> loginData;

    public RegisterGUI() {
        loadLoginData("src/main/resources/usersLoginData.txt");

        JFrame frame = new JFrame("Fitness Tracker - Register");
        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("<html><div style='text-align: center;'>CREATE YOUR FITNESS TRACKER ACCOUNT</div></html>");
        welcomeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(113, 54, 143));

        JLabel userLabel = new JLabel("Create Login:");
        JTextField userField = new JTextField(20);
        userField.setFont(new Font("Comic Sans MS", Font.BOLD, 16));

        JLabel passLabel = new JLabel("Create Password:");
        JPasswordField passField = new JPasswordField(20);

        JButton registerButton = new JButton("REGISTER");
        registerButton.setForeground(new Color(113, 54, 143));
        registerButton.setFont(new Font("Comic Sans MS", Font.BOLD, 14));

        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);

        JLabel backToLoginLabel = new JLabel("<html><div style='text-align: center;'><u>Back to login</u></div></html>");
        backToLoginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        backToLoginLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        backToLoginLabel.setForeground(new Color(113, 54, 143));

        // Set fonts and colors
        userLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        userLabel.setForeground(new Color(113, 54, 143));
        passLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        passLabel.setForeground(new Color(113, 54, 143));

        // Center alignment
        welcomeLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        userLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        userField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        passLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        passField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        registerButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        messageLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        backToLoginLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        userField.setMaximumSize(new Dimension(200, 30));
        passField.setMaximumSize(new Dimension(200, 30));

        panel.setBackground(new Color(230, 220, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        panel.add(Box.createVerticalGlue());
        panel.add(welcomeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(userLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(userField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(passLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(passField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(messageLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(registerButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(backToLoginLabel);
        panel.add(Box.createVerticalGlue());

        frame.add(panel, BorderLayout.CENTER);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        registerButton.addActionListener(e -> {
            String login = userField.getText();
            String password = new String(passField.getPassword());

            if (login.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Fields cannot be empty.");
                messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
                messageLabel.setForeground(Color.RED);
            } else if (login.contains(";") || password.contains(";")) {
                messageLabel.setText("Login or password cannot contain ';'.");
                messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
                messageLabel.setForeground(Color.RED);
            } else if (loginData.containsKey(login)) {
                messageLabel.setText("Login already exists.");
                messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
                messageLabel.setForeground(Color.RED);
            } else if (login.contains(" ") || password.contains(" ")) {
                messageLabel.setText("Login or password cannot contain spaces.");
                messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
                messageLabel.setForeground(Color.RED);
            } else {
                // Zamykamy aktualne okno rejestracji
                frame.dispose();
                // Przekazujemy login i hasło do okna askForData
                new AskForDataGUI(login, password);
            }
        });

        // Obsługa zmiany kursora myszy
        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                registerButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        backToLoginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                frame.dispose();
                new LoginGUI(); // Wróć do ekranu logowania
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                backToLoginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                backToLoginLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    /**
     * Wczytuje istniejące dane logowania z pliku.
     * Format każdej linii: uniqueID;login;password
     */
    private void loadLoginData(String filePath) {
        loginData = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    // parts[0] = uniqueID, parts[1] = login, parts[2] = hasło
                    loginData.put(parts[1], parts[2]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the login file: " + e.getMessage());
        }
    }

    /**
     * Dodaje nowego użytkownika do pliku usersLoginData.txt i zwraca jego UUID.
     */
    public static String addNewUser(String login, String password) {
        String filePath = "src/main/resources/usersLoginData.txt";
        String uniqueID = UUID.randomUUID().toString();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            // Zapis w formacie: uniqueID;login;password
            writer.write(uniqueID + ";" + login + ";" + password);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to the login file: " + e.getMessage());
        }

        // Dodaj dane użytkownika do pamięci (HashMap)
        loginData.put(login, password);

        // Zwracamy wygenerowany identyfikator
        return uniqueID;
    }
}
