package test.java;

import main.java.Activity;

import java.time.LocalDateTime;

/**
 * The `TestActivity` class is a concrete implementation of the abstract `Activity` class.
 * It is used solely for testing purposes.
 */
public class TestActivity extends Activity {

    /**
     * Constructs a `TestActivity` object with the specified properties.
     *
     * @param id             the unique identifier for the activity
     * @param name           the name of the activity
     * @param burnedCalories the number of calories burned during the activity
     * @param duration       the duration of the activity in minutes
     * @param startTime      the start time of the activity
     * @param endTime        the end time of the activity
     */
    public TestActivity(int id, String name, double burnedCalories, double duration, LocalDateTime startTime, LocalDateTime endTime) {
        super(id, name, burnedCalories, duration, startTime, endTime);
    }

    // Możemy dodać dodatkowe metody lub pola, jeśli jest to konieczne dla testów
}
