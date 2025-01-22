package main.java;

import java.time.LocalDateTime;

/**
 * The `RunningActivity` class represents a running workout activity.
 * It extends the `Activity` class and includes specific attributes for the
 * distance covered and the average speed.
 */
public class RunningActivity extends Activity {

    private double coveredDistance;
    private double averageSpeed;

    /**
     * Constructs a `RunningActivity` object with the specified properties.
     *
     * @param id              the unique identifier for the activity
     * @param name            the name of the activity
     * @param burnedCalories  the number of calories burned during the activity
     * @param duration        the duration of the activity in minutes
     * @param startTime       the start time of the activity
     * @param endTime         the end time of the activity
     * @param coveredDistance the distance covered during the activity in kilometers
     * @param averageSpeed    the average speed during the activity in kilometers per hour
     */
    public RunningActivity(int id, String name, double burnedCalories, double duration, LocalDateTime startTime, LocalDateTime endTime, double coveredDistance, double averageSpeed) {
        super(id, name, burnedCalories, duration, startTime, endTime);
        this.coveredDistance = coveredDistance;
        this.averageSpeed = averageSpeed;
    }

    /**
     * Returns the distance covered during the activity in kilometers.
     *
     * @return the covered distance
     */
    public double getCoveredDistance() {
        return coveredDistance;
    }

    /**
     * Sets the distance covered during the activity in kilometers.
     *
     * @param coveredDistance the covered distance to set
     */
    public void setCoveredDistance(double coveredDistance) {
        this.coveredDistance = coveredDistance;
    }

    /**
     * Returns the average speed during the activity in kilometers per hour.
     *
     * @return the average speed
     */
    public double getAverageSpeed() {
        return averageSpeed;
    }

    /**
     * Sets the average speed during the activity in kilometers per hour.
     *
     * @param averageSpeed the average speed to set
     */
    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    /**
     * Provides a summary of all running activity details as a formatted string.
     *
     * @return a string containing all activity details
     */
    @Override
    public String getAllInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Name: ").append(getName()).append(" ");
        info.append("Burned calories: ").append(getBurnedCalories()).append(" ");
        info.append("Duration: ").append(getDuration()).append(" ");
        info.append("Distance ran: ").append(getCoveredDistance()).append(" km ");
        info.append("Average speed: ").append(getAverageSpeed()).append(" km/h");

        return info.toString();
    }
}
