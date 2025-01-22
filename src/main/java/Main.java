package main.java;

import main.java.gui.LoginGUI;

/**
 * The `Main` class serves as the entry point for the Fitness Tracker application.
 * It initializes the graphical user interface (GUI) by launching the login screen.
 */
public class Main {
    /**
     * The main method starts the Fitness Tracker application by creating an instance
     * of the `LoginGUI` class.
     *
     * @param args command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        new LoginGUI();
    }
}
