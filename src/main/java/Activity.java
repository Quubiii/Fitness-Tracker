package main.java;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * The `Activity` class serves as an abstract base class representing a generic activity.
 * It includes properties such as the activity's ID, name, burned calories, duration,
 * and the start and end times. Subclasses can extend this class to provide specific behavior
 * for different activity types.
 */
public abstract class Activity {
    private int id;
    private String name;
    private double burnedCalories;
    private double duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    /**
     * Constructs an `Activity` object with the specified properties.
     *
     * @param id             the unique identifier for the activity
     * @param name           the name of the activity
     * @param burnedCalories the number of calories burned during the activity
     * @param duration       the duration of the activity in minutes
     * @param startTime      the start time of the activity
     * @param endTime        the end time of the activity
     */
    public Activity(int id, String name, double burnedCalories, double duration, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.name = name;
        this.burnedCalories = burnedCalories;
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Returns the unique identifier for the activity.
     *
     * @return the activity ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the activity.
     *
     * @param id the activity ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the activity.
     *
     * @return the activity name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the activity.
     *
     * @param name the activity name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the number of calories burned during the activity.
     *
     * @return the burned calories
     */
    public double getBurnedCalories() {
        return burnedCalories;
    }

    /**
     * Sets the number of calories burned during the activity.
     *
     * @param burnedCalories the burned calories to set
     */
    public void setBurnedCalories(double burnedCalories) {
        this.burnedCalories = burnedCalories;
    }

    /**
     * Returns the start time of the activity.
     *
     * @return the start time
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the activity.
     *
     * @param startTime the start time to set
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the end time of the activity.
     *
     * @return the end time
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the activity.
     *
     * @param endTime the end time to set
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Returns the duration of the activity in minutes.
     *
     * @return the duration
     */
    public double getDuration() {
        return duration;
    }

    /**
     * Calculates the duration of the activity in minutes based on the start and end times.
     *
     * @return the calculated duration
     */
    public double calculateDuration() {
        double calculatedDuration = Duration.between(getStartTime(), getEndTime()).toMinutes();
        this.duration = calculatedDuration;
        return calculatedDuration;
    }

    /**
     * Calculates the number of calories burned per minute during the activity.
     *
     * @return the calories burned per minute
     */
    public double calculateCaloriesPerMinute() {
        return getBurnedCalories() / getDuration();
    }

    /**
     * Provides a summary of all activity details as a formatted string.
     *
     * @return a string containing all activity details
     */
    public String getAllInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Name: ").append(getName()).append(" ");
        info.append("Burned calories: ").append(getBurnedCalories()).append(" ");
        info.append("Duration: ").append(getDuration());
        return info.toString();
    }
}
