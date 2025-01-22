package main.java;

import java.time.LocalDateTime;

/**
 * The `RopeJumpingActivity` class represents a rope-jumping workout activity.
 * It extends the `Activity` class and adds a specific attribute for the number of repetitions.
 */
public class RopeJumpingActivity extends Activity {

    private int numberOfRepetitions;

    /**
     * Constructs a `RopeJumpingActivity` object with the specified properties.
     *
     * @param id                  the unique identifier for the activity
     * @param name                the name of the activity
     * @param burnedCalories      the number of calories burned during the activity
     * @param duration            the duration of the activity in minutes
     * @param startTime           the start time of the activity
     * @param endTime             the end time of the activity
     * @param numberOfRepetitions the number of repetitions performed during the activity
     */
    public RopeJumpingActivity(int id, String name, double burnedCalories, double duration, LocalDateTime startTime, LocalDateTime endTime, int numberOfRepetitions) {
        super(id, name, burnedCalories, duration, startTime, endTime);
        this.numberOfRepetitions = numberOfRepetitions;
    }

    /**
     * Returns the number of repetitions performed during the activity.
     *
     * @return the number of repetitions
     */
    public int getNumberOfRepetitions() {
        return numberOfRepetitions;
    }

    /**
     * Sets the number of repetitions performed during the activity.
     *
     * @param numberOfRepetitions the number of repetitions to set
     */
    public void setNumberOfRepetitions(int numberOfRepetitions) {
        this.numberOfRepetitions = numberOfRepetitions;
    }

    /**
     * Provides a summary of all rope-jumping activity details as a formatted string.
     *
     * @return a string containing all activity details
     */
    @Override
    public String getAllInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Name: ").append(getName()).append(" ");
        info.append("Burned calories: ").append(getBurnedCalories()).append(" ");
        info.append("Duration: ").append(getDuration()).append(" ");
        info.append("Repetitions: ").append(getNumberOfRepetitions());

        return info.toString();
    }
}
