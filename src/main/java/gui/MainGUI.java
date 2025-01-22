package main.java.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainGUI {

    private String userName;
    private String dateOfAccountCreation;

    public MainGUI() {
        // Wczytujemy dane aktualnie zalogowanego użytkownika (UUID z LoginGUI)
        String currentUserUUID = LoginGUI.loggedInUserUUID;
        loadUserDataFromFile("src/main/resources/usersData.txt", currentUserUUID);

        JFrame frame = new JFrame("Fitness Tracker - Main");
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
        // Panel centralny (Center Panel) z powitaniem
        // ----------------
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(230, 220, 250)); // Jasny fiolet, tło

        // Teksty zastępcze, gdyby brakowało danych
        String nameText = (userName != null && !userName.isEmpty()) ? userName : "Guest";
        String dateText = (dateOfAccountCreation != null && !dateOfAccountCreation.isEmpty())
                ? dateOfAccountCreation
                : "some time";

        // Obliczamy różnicę czasu między datą założenia konta a chwilą obecną
        String whichIsLine = "";  // tu wrzucimy np. "Which is X days Y hours Z minutes"
        try {
            if (dateOfAccountCreation != null && !dateOfAccountCreation.isEmpty()) {
                // Załóżmy, że data w pliku jest w formacie "yyyy-MM-dd HH:mm:ss"
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime creationDateTime = LocalDateTime.parse(dateOfAccountCreation, formatter);
                LocalDateTime now = LocalDateTime.now();

                // Wyliczamy różnicę
                Duration diff = Duration.between(creationDateTime, now);
                long totalMinutes = diff.toMinutes();
                long days = totalMinutes / (24 * 60);
                long hours = (totalMinutes % (24 * 60)) / 60;
                long minutes = totalMinutes % 60;

                // Budujemy napis
                whichIsLine = "<br>Which is " + days + " day(s) " + hours + " hour(s) " + minutes + " minute(s)";
            }
        } catch (Exception ex) {
            // Gdyby parse się nie powiódł z jakiegoś powodu
            System.err.println("Cannot parse dateOfAccountCreation: " + dateOfAccountCreation);
        }

        String welcomeHtml = "<html>"
                + "<div style='text-align: center;'>"
                + "<h2 style='font-family: Comic Sans MS; font-size: 40px; color: #71368F;'>"
                + "Welcome back <b>" + nameText + "</b>!<br>"
                + "</h2>"
                + "<h2 style='font-family: Comic Sans MS; font-size: 26px; color: #71368F;'>"
                + "You are using our app since <b>" + dateText
                + whichIsLine
                + " ALREADY! \uD83D\uDE31💪🔥"
                + "<br>Look at THAT determination gurll! 😎👏"
                + "<br><br>"
                + "Remember, every time is a great time to exercise<br>~As someone wise probably said once...<br>SLAAY ;* 💃🕺"
                + "</h2>"
                + "</div>"
                + "</html>";

        JLabel welcomeLabel = new JLabel(welcomeHtml, SwingConstants.CENTER);

        // Dodajemy napis do panelu centralnego
        centerPanel.add(welcomeLabel, BorderLayout.CENTER);

        // ----------------
        // Dodajemy panele do głównego frame
        // ----------------
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    /**
     * Metoda wczytuje dane zalogowanego użytkownika z pliku usersData.txt
     * na podstawie jego UUID.
     */
    private void loadUserDataFromFile(String filePath, String userUUID) {
        // Jeśli w ogóle nie ma zalogowanego użytkownika, pomijamy wczytywanie
        if (userUUID == null) {
            return;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("File " + filePath + " does not exist!");
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
                    this.userName = parts[1];
                    // parts[2] = weight
                    // parts[3] = height
                    // parts[4] = age
                    // parts[5] = gender
                    this.dateOfAccountCreation = parts[6];
                    // parts[7] = dateOfCurrentWeight
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
