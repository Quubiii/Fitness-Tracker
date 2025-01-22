package main.java.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OurCoachesGUI {

    public OurCoachesGUI() {
        String currentUserUUID = LoginGUI.loggedInUserUUID; // Currently logged-in user UUID
        JFrame frame = new JFrame("Fitness Tracker - Our Coaches");
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
        // Center Panel - Coaches
        // ----------------
        JPanel centerPanel = new JPanel(new GridLayout(1, 2)); // Two columns: Left for José, Right for Jorge
        centerPanel.setBackground(new Color(230, 220, 250));

        // Left Panel - José Alcántar
        JPanel leftPanel = createCoachPanel(
                "José Alcántar",
                "src/main/resources/josee.jpg",
                "c1",
                "Jose knows exactly what he does. He coaches people for 15 years now and is a professional team sports competitor!",
                currentUserUUID,
                frame
        );

        // Right Panel - Jorge Echevarría
        JPanel rightPanel = createCoachPanel(
                "Jorge Echevarría",
                "src/main/resources/jorge.jpeg",
                "c2",
                "Jorge is passionate about fitness and well-being. He specializes in strength training and personalized coaching plans.",
                currentUserUUID,
                frame
        );

        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);

        frame.add(centerPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    /**
     * Creates a JPanel for a coach.
     */
    private JPanel createCoachPanel(String coachName, String imagePath, String coachID,
                                    String coachDescription, String currentUserUUID, JFrame frame) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(230, 220, 250));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel coachImage = createScaledImageLabel(imagePath, 250, 250);
        coachImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel coachLabel = new JLabel("<html><div style='text-align: center;'>"
                + "<h2 style='font-family: Comic Sans MS; font-size: 22px; color: #71368F;'>"
                + coachName
                + "</h2></div></html>", SwingConstants.CENTER);
        coachLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel coachDescriptionLabel = new JLabel("<html><div style='text-align: center;'>"
                + "<p style='font-family: Comic Sans MS; color: #5D3B90;'>"
                + coachDescription
                + "</p></div></html>", SwingConstants.CENTER);
        coachDescriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton requestButton = new JButton("Request Training Session");
        requestButton.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        requestButton.setForeground(new Color(113, 54, 143));
        requestButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Wywołanie nowej, zmodyfikowanej metody
        requestButton.addActionListener(e -> handleRequestClick(coachID, currentUserUUID, frame));

        panel.add(Box.createVerticalGlue());
        panel.add(coachImage);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(coachLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(coachDescriptionLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(requestButton);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    /**
     * Handles the request button click for a specific coach.
     * Dodatkowo sprawdzamy, czy użytkownik nie jest już uczniem coacha.
     */
    private void handleRequestClick(String coachID, String currentUserUUID, JFrame frame) {
        // 1) Najpierw sprawdzamy, czy user nie jest już uczniem w "coachesAccepted.txt"
        String acceptedFilePath = "src/main/resources/coachesStudents.txt";
        boolean userIsAlreadyStudent = false;

        try (BufferedReader br = new BufferedReader(new FileReader(acceptedFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(coachID + ";")) {
                    if (line.contains(currentUserUUID)) {
                        userIsAlreadyStudent = true;
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // Plik nie istnieje – zakładamy, że nikt nie jest jeszcze uczniem
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading accepted file: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        if (userIsAlreadyStudent) {
            JOptionPane.showMessageDialog(frame,
                    "You are already a student of this coach – you cannot request again!");
            return;
        }

        // 2) Jeśli nie jest jeszcze uczniem, sprawdzamy, czy nie wysłał już wcześniej prośby
        String requestsFilePath = "src/main/resources/coachesRequests.txt";
        List<String> lines = new ArrayList<>();
        boolean userAlreadyRequested = false;

        try (BufferedReader br = new BufferedReader(new FileReader(requestsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(coachID + ";")) {
                    if (line.contains(currentUserUUID)) {
                        userAlreadyRequested = true;
                    } else {
                        line += ";" + currentUserUUID;
                    }
                }
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            // Jeśli plik nie istnieje, to nic. Za moment go utworzymy.
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading requests file: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        if (userAlreadyRequested) {
            JOptionPane.showMessageDialog(frame,
                    "You have already sent a request to this coach!");
            return;
        }

        // 3) Sprawdzamy, czy w ogóle istniała linia z danym coachID
        boolean coachLineFound = false;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.startsWith(coachID + ";")) {
                coachLineFound = true;
                lines.set(i, line); // Już dopisaliśmy w trakcie
                break;
            }
        }
        if (!coachLineFound) {
            lines.add(coachID + ";" + currentUserUUID);
        }

        // 4) Zapis do pliku
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(requestsFilePath))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error writing requests file: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        // 5) Komunikat
        JOptionPane.showMessageDialog(frame,
                "Request sent to the coach successfully!");
    }

    /**
     * Creates a JLabel with a scaled image.
     */
    private JLabel createScaledImageLabel(String imagePath, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(scaledImage));
    }
}
