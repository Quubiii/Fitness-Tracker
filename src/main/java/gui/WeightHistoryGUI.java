package main.java.gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.CategoryPlot;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class WeightHistoryGUI {

    private String userUUID;
    private ArrayList<WeightEntry> weightEntries = new ArrayList<>();

    public WeightHistoryGUI() {
        userUUID = LoginGUI.loggedInUserUUID;
        loadWeightDataFromFile("src/main/resources/weightsData.txt");

        JFrame frame = new JFrame("Fitness Tracker - Weight History");
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
        // CENTER PANEL
        // ----------------
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(230, 220, 250));

        // Tworzymy dataset z historią wag
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (WeightEntry we : weightEntries) {
            // "Weight" to nazwa serii, we.getDate() to kategoria (oś X)
            dataset.addValue(we.getWeight(), "Weight", we.getDate());
        }

        // Po stworzeniu wykresu:
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Weight History",
                "Date",
                "Weight (kg)",
                dataset,
                PlotOrientation.VERTICAL,
                false, // legenda
                true,
                false
        );

// 1. Ustawiamy tło całego wykresu (obszar poza plotem) na jasny fiolet:
        Color lightPurple = new Color(230, 220, 250);
        lineChart.setBackgroundPaint(lightPurple);

// 2. Pobieramy obiekt CategoryPlot – obszar rysowania serii
        CategoryPlot plot = lineChart.getCategoryPlot();

// 3. Ustawiamy tło plotu na trochę inny, np. jeszcze jaśniejszy fiolet
        Color lighterPurple = new Color(240, 230, 255);
        plot.setBackgroundPaint(lighterPurple);

// 4. Ustawiamy kolor siatek (opcjonalnie można je wyłączyć)
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);

// 5. Pobieramy renderer (domyślnie przy createLineChart będzie to LineAndShapeRenderer)
        org.jfree.chart.renderer.category.LineAndShapeRenderer renderer =
                (org.jfree.chart.renderer.category.LineAndShapeRenderer) plot.getRenderer();

// 6. Ustawiamy ciemny fiolet dla serii i pogrubiamy linię
        renderer.setSeriesPaint(0, darkPurple);
        renderer.setSeriesStroke(0, new BasicStroke(3.0f)); // grubość 3 px

// 7. Zmieniamy czcionki na Comic Sans
        Font comicSansPlain = new Font("Comic Sans MS", Font.PLAIN, 12);
        Font comicSansBold  = new Font("Comic Sans MS", Font.BOLD, 14);

// - Tytuł wykresu
        if (lineChart.getTitle() != null) {
            lineChart.getTitle().setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        }

// - Etykieta osi X
        plot.getDomainAxis().setLabelFont(comicSansBold);
// - Etykiety wartości (ticki) osi X
        plot.getDomainAxis().setTickLabelFont(comicSansPlain);

// - Etykieta osi Y
        plot.getRangeAxis().setLabelFont(comicSansBold);
// - Etykiety wartości (ticki) osi Y
        plot.getRangeAxis().setTickLabelFont(comicSansPlain);

// 8. Jeśli chcesz, możesz ustawić również kolory osi:
        plot.getDomainAxis().setAxisLinePaint(darkPurple);
        plot.getRangeAxis().setAxisLinePaint(darkPurple);


        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(800, 500));
        centerPanel.add(chartPanel, BorderLayout.CENTER);

        frame.add(centerPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    /**
     * Wczytuje dane wagi z pliku weightsData.txt dla aktualnie zalogowanego użytkownika
     * Format pliku:
     * uuid;2023-01-01 10:00:00~70;2023-02-01 10:00:00~72;...
     */
    private void loadWeightDataFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("weightsData.txt does not exist or is empty.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";");
                if (parts.length < 2) continue;

                String uuidFromFile = parts[0];
                if (!uuidFromFile.equals(userUUID)) {
                    // Nie nasz użytkownik
                    continue;
                }

                // Od indexu 1 do końca mamy wpisy "data~waga"
                for (int i = 1; i < parts.length; i++) {
                    String entry = parts[i];
                    if (entry.contains("~")) {
                        String[] subParts = entry.split("~");
                        if (subParts.length == 2) {
                            String date = subParts[0];
                            double weightVal = 0;
                            try {
                                weightVal = Double.parseDouble(subParts[1]);
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            weightEntries.add(new WeightEntry(date, weightVal));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prosta klasa do przechowywania wpisu wagi: data + wartość wagi
     */
    private static class WeightEntry {
        private final String date;
        private final double weight;

        public WeightEntry(String date, double weight) {
            this.date = date;
            this.weight = weight;
        }

        public String getDate() {
            return date;
        }

        public double getWeight() {
            return weight;
        }
    }
}
