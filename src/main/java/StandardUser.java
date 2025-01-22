package main.java;

import java.util.ArrayList;

/**
 * The `StandardUser` class represents a user in the Fitness Tracker application.
 * It contains user details, weight history, activity list, and methods to perform
 * various calculations based on the user's data and activities.
 */
public class StandardUser {
    private int id;
    private String name;
    private double weight;
    private double height;
    private int age;
    private String gender;
    private ArrayList<Double> weightHistory;
    private ArrayList<Activity> activitiesList;

    /**
     * Constructs a `StandardUser` object with the specified properties.
     *
     * @param id              the unique identifier for the user
     * @param name            the user's name
     * @param weight          the user's weight in kilograms
     * @param height          the user's height in meters
     * @param age             the user's age in years
     * @param gender          the user's gender
     * @param weightHistory   the history of the user's weight measurements
     * @param activitiesList  the list of activities performed by the user
     */
    public StandardUser(int id, String name, double weight, double height, int age, String gender, ArrayList<Double> weightHistory, ArrayList<Activity> activitiesList) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.gender = gender;
        this.weightHistory = weightHistory;
        this.activitiesList = activitiesList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<Double> getWeightHistory() {
        return weightHistory;
    }

    public ArrayList<Activity> getActivitiesList() {
        return activitiesList;
    }

    /**
     * Calculates the Body Mass Index (BMI) using the user's weight and height.
     *
     * @param weight the user's weight in kilograms
     * @param height the user's height in meters
     * @return the calculated BMI
     */
    public double calculateBMI(double weight, double height) {
        return weight / (height * height);
    }

    /**
     * Calculates the average duration of all activities performed by the user.
     *
     * @return the average activity duration in minutes
     */
    public double calculateAverageActivityTime() {
        double totalDuration = activitiesList.stream()
                .mapToDouble(Activity::calculateDuration)
                .sum();
        return totalDuration / activitiesList.size();
    }

    /**
     * Calculates the average calories burned per activity.
     *
     * @return the average calories burned
     */
    public double calculateAverageActivityCalories() {
        double totalCalories = activitiesList.stream()
                .mapToDouble(Activity::getBurnedCalories)
                .sum();
        return totalCalories / activitiesList.size();
    }

    /**
     * Calculates the total duration of all activities.
     *
     * @return the total activity duration in minutes
     */
    public double calculateTotalActivityTime() {
        return activitiesList.stream()
                .mapToDouble(Activity::getDuration)
                .sum();
    }

    /**
     * Calculates the total calories burned in all activities.
     *
     * @return the total calories burned
     */
    public double calculateTotalBurnedCalories() {
        return activitiesList.stream()
                .mapToDouble(Activity::getBurnedCalories)
                .sum();
    }

    /**
     * Finds the activity with the longest duration.
     *
     * @return the longest activity
     */
    public Activity findTheLongestActivity() {
        return activitiesList.stream()
                .max((a, b) -> Double.compare(a.getDuration(), b.getDuration()))
                .orElse(null);
    }

    /**
     * Finds the activity with the shortest duration.
     *
     * @return the shortest activity
     */
    public Activity findTheShortestActivity() {
        return activitiesList.stream()
                .min((a, b) -> Double.compare(a.getDuration(), b.getDuration()))
                .orElse(null);
    }

    /**
     * Finds the most effective activity based on calories burned per minute.
     *
     * @return the most effective activity
     */
    public Activity findMostEffectiveActivity() {
        return activitiesList.stream()
                .max((a, b) -> Double.compare(a.calculateCaloriesPerMinute(), b.calculateCaloriesPerMinute()))
                .orElse(null);
    }

    /**
     * Provides a summary of all user details and calculated statistics.
     *
     * @return a string containing all user information
     */
    public String getAllInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Name: ").append(getName()).append("\n");
        info.append("Weight: ").append(getWeight()).append("\n");
        info.append("Height: ").append(getHeight()).append("\n");
        info.append("Age: ").append(getAge()).append("\n");
        info.append("Gender: ").append(getGender()).append("\n");
        info.append("Total activity time: ").append(calculateTotalActivityTime()).append(" mins [Avg: ").append(calculateAverageActivityTime()).append(" mins]\n");
        info.append("Total burnt calories: ").append(calculateTotalBurnedCalories()).append(" kcal [Avg: ").append(calculateAverageActivityCalories()).append(" kcal]\n");
        info.append("Longest activity: ").append(findTheLongestActivity().getName()).append(": ").append(findTheLongestActivity().getDuration()).append(" mins\n");
        info.append("Most effective activity: ").append(findMostEffectiveActivity().getName()).append(": ").append(findMostEffectiveActivity().calculateCaloriesPerMinute()).append(" kcal/min\n");

        return info.toString();
    }
}
