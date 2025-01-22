package main.java.gui.coachgui;

import main.java.gui.LoginGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The {@code CoachGUI} class creates the graphical user interface for the coach's dashboard
 * in the Fitness Tracker application. It provides navigation options for logging out,
 * viewing students, and handling exercise session requests.
 */
public class CoachGUI {

    /**
     * Constructs the CoachGUI, initializing and displaying the coach dashboard frame.
     */
    public CoachGUI() {
        // Create the main application frame with title
        JFrame frame = new JFrame("Fitness Tracker - Coach Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null); // Centers the frame on the screen
        frame.setLayout(new BorderLayout());

        // Initialize the top panel containing navigation buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Define a custom dark purple color for the top panel background
        Color darkPurple = new Color(113, 54, 143);
        topPanel.setBackground(darkPurple);

        // Create navigation buttons
        JButton logOutButton = new JButton("Log Out");
        JButton myStudentsButton = new JButton("My Students");
        JButton exerciseRequestsButton = new JButton("Exercise Session Requests");

        // Set font and foreground color for the buttons
        Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 16);
        logOutButton.setFont(buttonFont);
        logOutButton.setForeground(darkPurple);
        myStudentsButton.setFont(buttonFont);
        myStudentsButton.setForeground(darkPurple);
        exerciseRequestsButton.setFont(buttonFont);
        exerciseRequestsButton.setForeground(darkPurple);

        // Add buttons to the top panel
        topPanel.add(logOutButton);
        topPanel.add(myStudentsButton);
        topPanel.add(exerciseRequestsButton);

        // Add mouse listener to the Log Out button
        logOutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose(); // Close the current frame
                new LoginGUI();   // Open the Login GUI
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                logOutButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logOutButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Revert cursor
            }
        });

        // Add mouse listener to the My Students button
        myStudentsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose(); // Close the current frame
                new MyStudentsGUI(); // Open the My Students GUI
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                myStudentsButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                myStudentsButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Revert cursor
            }
        });

        // Add mouse listener to the Exercise Session Requests button
        exerciseRequestsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose(); // Close the current frame
                new SessionRequestsGUI(); // Open the Session Requests GUI
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                exerciseRequestsButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exerciseRequestsButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Revert cursor
            }
        });

        // Initialize the center panel with a welcoming message
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(230, 220, 250)); // Light purple background

        // HTML formatted welcome message with styling
        String welcomeHtml = "<html>"
                + "<div style='text-align: center;'>"
                + "<h2 style='font-family: Comic Sans MS; font-size: 40px; color: #71368F;'>"
                + "Welcome back, Coach!<br>"
                + "</h2>"
                + "<h2 style='font-family: Comic Sans MS; font-size: 26px; color: #71368F;'>"
                + "Manage your students and handle exercise session requests here.<br>"
                + "Have a great day inspiring fitness!<br>"
                + "</h2>"
                + "</div>"
                + "</html>";

        // Create a label to display the welcome message
        JLabel welcomeLabel = new JLabel(welcomeHtml, SwingConstants.CENTER);
        centerPanel.add(welcomeLabel, BorderLayout.CENTER); // Add label to the center panel

        // Add the top and center panels to the frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);
    }
}
