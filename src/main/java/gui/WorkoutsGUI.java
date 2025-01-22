package main.java.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WorkoutsGUI {
    public WorkoutsGUI() {
        String loggedInUUID = LoginGUI.loggedInUserUUID;
        JFrame frame = new JFrame("Fitness Tracker - Workouts");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);

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

        // Center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(230, 220, 250));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("<html><div style='text-align: center;'>WITH OUR APP, YOU CAN CURRENTLY TRACK 3 TYPES OF EXERCISES!</div></html>");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        titleLabel.setForeground(darkPurple);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Load and scale images
        ImageIcon runningIcon = new ImageIcon("src/main/resources/running.png");
        ImageIcon scaledRunningIcon = new ImageIcon(runningIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH));

        ImageIcon cyclingIcon = new ImageIcon("src/main/resources/cycling.png");
        ImageIcon scaledCyclingIcon = new ImageIcon(cyclingIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH));

        ImageIcon ropeJumpingIcon = new ImageIcon("src/main/resources/rope_jumping.png");
        ImageIcon scaledRopeJumpingIcon = new ImageIcon(ropeJumpingIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH));

        // Labels with images
        JLabel runningLabel = new JLabel("Running");
        runningLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        runningLabel.setForeground(darkPurple);
        runningLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        runningLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
        runningLabel.setIcon(scaledRunningIcon);

        JLabel cyclingLabel = new JLabel("Cycling");
        cyclingLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        cyclingLabel.setForeground(darkPurple);
        cyclingLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        cyclingLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
        cyclingLabel.setIcon(scaledCyclingIcon);

        JLabel ropeJumpingLabel = new JLabel("Rope Jumping");
        ropeJumpingLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        ropeJumpingLabel.setForeground(darkPurple);
        ropeJumpingLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        ropeJumpingLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
        ropeJumpingLabel.setIcon(scaledRopeJumpingIcon);

        // Images panel
        JPanel imagesPanel = new JPanel();
        imagesPanel.setBackground(new Color(230, 220, 250)); // Match the background color
        imagesPanel.setLayout(new GridLayout(1, 3, 20, 20)); // 1 row, 3 columns
        imagesPanel.add(runningLabel);
        imagesPanel.add(cyclingLabel);
        imagesPanel.add(ropeJumpingLabel);

        JButton registerButton = new JButton("REGISTER MY WORKOUT");
        registerButton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        registerButton.setForeground(darkPurple);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                registerButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                frame.dispose();
                new RegisterTrainingGUI(getUserWeight(loggedInUUID));
            }
        });

        // Adding components to center panel
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(imagesPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(registerButton);

        // Frame layout
        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private double getUserWeight(String uuid) {
        String filePath = "src/main/resources/usersData.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 3 && parts[0].equals(uuid)) {
                    return Double.parseDouble(parts[2]); // Waga jest trzecim elementem
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the user data file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid weight format in file.");
        }

        // Wartość domyślna w razie braku wagi
        return 0.0;
    }
}
