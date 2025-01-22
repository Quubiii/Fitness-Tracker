package main.java.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AboutMeGUI {

    private final BMIIndicator bmiIndicator;
    private final JLabel bmiLabel;
    private final JLabel bmiCategoryLabel;

    private String userUUID;
    private String userName;
    private double userWeight;
    private double userHeight;
    private int userAge;
    private String userGender;
    private String dateOfAccountCreation;
    private String dateOfCurrentWeight;

    public AboutMeGUI() {
        userUUID = LoginGUI.loggedInUserUUID;

        loadUserDataFromFile("src/main/resources/usersData.txt");

        JFrame frame = new JFrame("Fitness Tracker - About Me");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        //Top
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

        JPopupMenu profileMenu = new JPopupMenu();
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

        profileMenu.add(aboutMe);
        profileMenu.add(workoutStats);
        profileMenu.add(weightHistory);
        profileMenu.add(logOut);

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

        profileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                profileMenu.show(profileButton, 0, profileButton.getHeight());
            }
        });

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

        coachesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Go to Our Coaches...");
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

        //Center
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(230, 220, 250));

        //Left
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(230, 220, 250));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        String headingText = "Hey " + (userName != null ? userName : "") + "! Your current information:";
        JLabel yourDataLabel = new JLabel(headingText);
        yourDataLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        Color dataColor = new Color(113, 54, 143);
        yourDataLabel.setForeground(dataColor);

        leftPanel.add(yourDataLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel uuidLabel = new JLabel("UUID: " + (userUUID != null ? userUUID : "-"));
        JLabel nameLabel = new JLabel("Name: " + (userName != null ? userName : "-"));
        JLabel weightLabel = new JLabel("Weight: " + userWeight);
        JLabel heightLabel = new JLabel("Height: " + userHeight);
        JLabel ageLabel = new JLabel("Age: " + userAge);
        JLabel genderLabel = new JLabel("Gender: " + (userGender != null ? userGender : "-"));
        JLabel accountDateLabel = new JLabel("Date of account creation: "
                + (dateOfAccountCreation != null ? dateOfAccountCreation : "-"));
        JLabel currentWeightDateLabel = new JLabel("Date of current weight: "
                + (dateOfCurrentWeight != null ? dateOfCurrentWeight : "-"));

        Font dataFont = new Font("Comic Sans MS", Font.BOLD, 20);

        uuidLabel.setFont(dataFont);
        nameLabel.setFont(dataFont);
        weightLabel.setFont(dataFont);
        heightLabel.setFont(dataFont);
        ageLabel.setFont(dataFont);
        genderLabel.setFont(dataFont);
        accountDateLabel.setFont(dataFont);
        currentWeightDateLabel.setFont(dataFont);

        uuidLabel.setForeground(dataColor);
        nameLabel.setForeground(dataColor);
        weightLabel.setForeground(dataColor);
        heightLabel.setForeground(dataColor);
        ageLabel.setForeground(dataColor);
        genderLabel.setForeground(dataColor);
        accountDateLabel.setForeground(dataColor);
        currentWeightDateLabel.setForeground(dataColor);

        leftPanel.add(uuidLabel);
        leftPanel.add(nameLabel);
        leftPanel.add(weightLabel);
        leftPanel.add(heightLabel);
        leftPanel.add(ageLabel);
        leftPanel.add(genderLabel);
        leftPanel.add(accountDateLabel);
        leftPanel.add(currentWeightDateLabel);

        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton editButton = new JButton("EDIT SOME INFORMATION");
        editButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        editButton.setForeground(darkPurple);
        editButton.setBackground(new Color(230, 220, 250));
        editButton.setFocusPainted(false);

        editButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(editButton);

        editButton.addActionListener(e -> openEditDialog(frame, nameLabel, weightLabel, heightLabel, currentWeightDateLabel));

        centerPanel.add(leftPanel, BorderLayout.WEST);

        //Right
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(230, 220, 250));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        bmiLabel = new JLabel("Your BMI: -");
        bmiLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        bmiLabel.setForeground(dataColor);
        bmiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        rightPanel.add(bmiLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        bmiCategoryLabel = new JLabel("");
        bmiCategoryLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        bmiCategoryLabel.setForeground(dataColor);
        bmiCategoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(bmiCategoryLabel);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        bmiIndicator = new BMIIndicator();
        bmiIndicator.setPreferredSize(new Dimension(320, 320));
        bmiIndicator.setAlignmentX(Component.CENTER_ALIGNMENT);

        rightPanel.add(bmiIndicator);

        centerPanel.add(rightPanel, BorderLayout.EAST);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        if (userHeight > 0) {
            double heightInMeters = userHeight / 100.0;
            double calculatedBMI = userWeight / Math.pow(heightInMeters, 2);
            setBMI(calculatedBMI);
        }
    }

    private void openEditDialog(JFrame parentFrame,
                                JLabel nameLabel,
                                JLabel weightLabel,
                                JLabel heightLabel,
                                JLabel currentWeightDateLabel) {

        JDialog dialog = new JDialog(parentFrame, "Edit Information", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
        dialog.getContentPane().setBackground(new Color(230, 220, 250));

        JTextField nameField = new JTextField(userName);
        JTextField weightField = new JTextField(String.valueOf(userWeight));
        JTextField heightField_ = new JTextField(String.valueOf(userHeight));

        JLabel nameLbl = new JLabel("Name:");
        JLabel weightLbl = new JLabel("Weight:");
        JLabel heightLbl_ = new JLabel("Height:");

        Font lblFont = new Font("Comic Sans MS", Font.BOLD, 16);
        Color lblColor = new Color(113, 54, 143);
        nameLbl.setFont(lblFont);
        nameLbl.setForeground(lblColor);
        weightLbl.setFont(lblFont);
        weightLbl.setForeground(lblColor);
        heightLbl_.setFont(lblFont);
        heightLbl_.setForeground(lblColor);

        nameField.setMaximumSize(new Dimension(200, 30));
        weightField.setMaximumSize(new Dimension(200, 30));
        heightField_.setMaximumSize(new Dimension(200, 30));

        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        saveButton.setForeground(lblColor);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        cancelButton.setForeground(lblColor);

        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        fieldPanel.setBackground(new Color(230, 220, 250));
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        fieldPanel.add(nameLbl);
        fieldPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldPanel.add(nameField);
        fieldPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        fieldPanel.add(weightLbl);
        fieldPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldPanel.add(weightField);
        fieldPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        fieldPanel.add(heightLbl_);
        fieldPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        fieldPanel.add(heightField_);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(230, 220, 250));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        dialog.add(fieldPanel);
        dialog.add(buttonPanel);

        saveButton.addActionListener(e -> {
            String newName = nameField.getText().trim();
            double newWeight;
            double newHeight;
            try {
                newWeight = Double.parseDouble(weightField.getText().trim());
            } catch (NumberFormatException ex) {
                newWeight = userWeight;
            }
            try {
                newHeight = Double.parseDouble(heightField_.getText().trim());
            } catch (NumberFormatException ex) {
                newHeight = userHeight;
            }

            String newWeightDate = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // 1) Zapis do pliku usersData.txt
            updateUserDataInFile(
                    userUUID,
                    newName,
                    newWeight,
                    newHeight,
                    userAge,
                    userGender,
                    dateOfAccountCreation,
                    newWeightDate
            );

            // 2) Uaktualnij w pamięci
            userName = newName;
            userWeight = newWeight;
            userHeight = newHeight;
            dateOfCurrentWeight = newWeightDate;

            // 3) Odśwież labelki
            nameLabel.setText("Name: " + userName);
            weightLabel.setText("Weight: " + userWeight);
            heightLabel.setText("Height: " + userHeight);
            currentWeightDateLabel.setText("Date of current weight: " + dateOfCurrentWeight);

            // 4) Przelicz BMI
            if (userHeight > 0) {
                double heightInMeters = userHeight / 100.0;
                double newBMI = userWeight / Math.pow(heightInMeters, 2);
                setBMI(newBMI);
            }

            // 5) ZAKTUALIZUJ plik weightsData.txt
            updateWeightsDataInFile(userUUID, newWeightDate, newWeight);

            dialog.dispose();
        });

        // Obsługa "Cancel"
        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void updateUserDataInFile(String uuid, String newName, double newWeight, double newHeight,
                                      int age, String gender, String accountCreationDate,
                                      String weightDate) {

        File file = new File("src/main/resources/usersData.txt");
        if (!file.exists()) {
            System.err.println("File not found: " + file.getAbsolutePath());
            return;
        }

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    sb.append("\n");
                    continue;
                }
                String[] parts = line.split(";");
                if (parts.length < 8) {
                    sb.append(line).append("\n");
                    continue;
                }

                String uuidFromFile = parts[0];
                if (uuidFromFile.equals(uuid)) {
                    // Nadpisujemy dany wiersz
                    String newLine = uuid + ";" + newName + ";" + newWeight + ";" +
                            newHeight + ";" + age + ";" + gender + ";" +
                            accountCreationDate + ";" + weightDate;
                    sb.append(newLine).append("\n");
                    found = true;
                } else {
                    sb.append(line).append("\n");
                }
            }

            if (!found) {
                System.out.println("UUID not found in file; nothing updated.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            bw.write(sb.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setBMI(double userBMI) {
        bmiLabel.setText(String.format("Your BMI: %.1f", userBMI));
        bmiIndicator.setBMI(userBMI);
        bmiIndicator.repaint();

        // Ustalamy kategorię + kolor
        if (userBMI < 18.5) {
            bmiCategoryLabel.setText("Underweight");
            bmiCategoryLabel.setForeground(new Color(173, 216, 230)); // niebieski
        } else if (userBMI < 25.0) {
            bmiCategoryLabel.setText("Normal weight");
            bmiCategoryLabel.setForeground(new Color(144, 238, 144)); // zielony
        } else if (userBMI < 30.0) {
            bmiCategoryLabel.setText("Overweight");
            bmiCategoryLabel.setForeground(new Color(255, 218, 185)); // pomarańczowy
        } else {
            bmiCategoryLabel.setText("Obesity");
            bmiCategoryLabel.setForeground(new Color(250, 128, 114)); // czerwony
        }
    }

    // Ładowanie danych z pliku
    private void loadUserDataFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.err.println("File " + fileName + " does not exist!");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) continue;
                String[] parts = line.split(";");
                if (parts.length < 8) continue;

                String uuidFromFile = parts[0];
                if (uuidFromFile.equals(userUUID)) {
                    userName = parts[1];
                    try {
                        userWeight = Double.parseDouble(parts[2]);
                    } catch (NumberFormatException e) {
                        userWeight = 0.0;
                    }
                    try {
                        userHeight = Double.parseDouble(parts[3]);
                    } catch (NumberFormatException e) {
                        userHeight = 0.0;
                    }
                    try {
                        userAge = Integer.parseInt(parts[4]);
                    } catch (NumberFormatException e) {
                        userAge = 0;
                    }
                    userGender = parts[5];
                    dateOfAccountCreation = parts[6];
                    dateOfCurrentWeight = parts[7];
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateWeightsDataInFile(String uuid, String newDate, double newWeight) {
        File file = new File("src/main/resources/weightsData.txt");
        boolean fileExists = file.exists();
        StringBuilder sb = new StringBuilder();
        boolean foundUserLine = false;

        // Jeśli plik nie istnieje, tworzymy pusty
        if (!fileExists) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                sb.append(line).append("\n");
                continue;
            }
            String[] parts = line.split(";");
            if (parts.length < 2) {
                sb.append(line).append("\n");
                continue;
            }

            String uuidFromFile = parts[0];
            if (uuidFromFile.equals(uuid)) {
                // Znaleźliśmy linię użytkownika
                foundUserLine = true;
                // Sprawdzamy, czy data jest już zapisana
                boolean dateAlreadySaved = false;
                StringBuilder userLineBuilder = new StringBuilder();
                userLineBuilder.append(uuid);

                for (int i = 1; i < parts.length; i++) {
                    String entry = parts[i];
                    if (entry.startsWith(newDate + "~")) {
                        dateAlreadySaved = true;
                    }
                    userLineBuilder.append(";").append(entry);
                }
                // Jeśli data nie istnieje, dopisujemy
                if (!dateAlreadySaved) {
                    userLineBuilder.append(";").append(newDate).append("~").append(newWeight);
                }
                sb.append(userLineBuilder.toString()).append("\n");
            } else {
                sb.append(line).append("\n");
            }
        }

        // Jeśli nie znaleziono w ogóle linii użytkownika, dodajemy nową
        if (!foundUserLine) {
            String newLine = uuid + ";" + newDate + "~" + newWeight;
            sb.append(newLine).append("\n");
        }

        // Zapisujemy ponownie do pliku
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            bw.write(sb.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Klasa wewnętrzna - wskaźnik BMI.
     */
    private static class BMIIndicator extends JPanel {
        private double userBMI = 0;

        public void setBMI(double userBMI) {
            this.userBMI = userBMI;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Fill background
            g2d.setColor(new Color(230, 220, 250));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            int width = getWidth();
            int height = getHeight();

            int radius = (int) (Math.min(width, height) / 2.5);

            // Odległość od prawej krawędzi, np. 50 pikseli
            int marginRight = 50;

            // Wyliczamy centerX, aby półkole było odsunięte
            // od prawej krawędzi o marginRight pikseli
            int centerX = width - radius - marginRight;
            int centerY = height / 2;

            int startAngle = 0;
            int arcAngle = 180;

            // Czerwona część (obesity)
            g2d.setColor(new Color(250, 128, 114));
            g2d.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2,
                    startAngle, arcAngle / 4);

            // Pomarańczowa część (overweight)
            g2d.setColor(new Color(255, 218, 185));
            g2d.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2,
                    startAngle + arcAngle / 4, arcAngle / 4);

            // Zielona część (normal weight)
            g2d.setColor(new Color(144, 238, 144));
            g2d.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2,
                    startAngle + arcAngle / 2, arcAngle / 4);

            // Niebieska część (underweight)
            g2d.setColor(new Color(173, 216, 230));
            g2d.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2,
                    startAngle + 3 * arcAngle / 4, arcAngle / 4);

            // Wskaźnik (czarna linia)
            g2d.setColor(Color.BLACK);
            double angle;
            if (userBMI < 18.5) {
                angle = 180 - ((userBMI / 18.5) * 45);
            } else if (userBMI < 25.0) {
                angle = 135 - (((userBMI - 18.5) / (25.0 - 18.5)) * 45);
            } else if (userBMI < 30.0) {
                angle = 90 - (((userBMI - 25.0) / (30.0 - 25.0)) * 45);
            } else {
                angle = 45 - (((userBMI - 30.0) / (40.0 - 30.0)) * 45);
                if (angle < 0) angle = 0;
            }

            double rad = Math.toRadians(angle);
            int pointerX = (int) (centerX + Math.cos(rad) * radius * 0.9);
            int pointerY = (int) (centerY - Math.sin(rad) * radius * 0.9);

            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(centerX, centerY, pointerX, pointerY);

            // Obrys półkola + linia bazowa
            g2d.setStroke(new BasicStroke(3));
            g2d.drawArc(centerX - radius, centerY - radius, radius * 2, radius * 2,
                    startAngle, arcAngle);
            g2d.drawLine(centerX - radius, centerY, centerX + radius, centerY);
        }
    }
}
