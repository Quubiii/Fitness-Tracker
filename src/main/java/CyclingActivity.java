package main.java;

import java.time.LocalDateTime;

/**
 * The `CyclingActivity` class represents a cycling workout activity.
 * It extends the `Activity` class and adds specific attributes related to cycling,
 * such as covered distance and maximum speed.
 */
public class CyclingActivity extends Activity {

    private double coveredDistance;
    private double maxSpeed;

    /**
     * Constructs a `CyclingActivity` object with the specified parameters.
     *
     * @param id              the unique identifier for the activity
     * @param name            the name of the activity
     * @param burnedCalories  the number of calories burned during the activity
     * @param duration        the duration of the activity in minutes
     * @param startTime       the start time of the activity
     * @param endTime         the end time of the activity
     * @param coveredDistance the distance covered during the activity in kilometers
     * @param maxSpeed        the maximum speed achieved during the activity in kilometers per hour
     */
    public CyclingActivity(int id, String name, double burnedCalories, double duration, LocalDateTime startTime, LocalDateTime endTime, double coveredDistance, double maxSpeed) {
        super(id, name, burnedCalories, duration, startTime, endTime);
        this.coveredDistance = coveredDistance;
        this.maxSpeed = maxSpeed;
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
     * Returns the maximum speed achieved during the activity in kilometers per hour.
     *
     * @return the maximum speed
     */
    public double getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * Sets the maximum speed achieved during the activity in kilometers per hour.
     *
     * @param maxSpeed the maximum speed to set
     */
    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    /**
     * Provides a summary of all cycling activity details as a formatted string.
     *
     * @return a string containing all activity details
     */
    @Override
    public String getAllInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Name: ").append(getName()).append(" ");
        info.append("Burned calories: ").append(getBurnedCalories()).append(" ");
        info.append("Duration: ").append(getDuration()).append(" ");
        info.append("Distance cycled: ").append(getCoveredDistance()).append(" km ");
        info.append("Maximum speed: ").append(getMaxSpeed()).append(" km/h");

        return info.toString();
    }
}
