package test.java;

import main.java.Activity;
import main.java.StandardUser;
import test.java.TestActivity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The `StandardUserTest` class contains unit tests for the `StandardUser` class.
 * It verifies the correctness of constructors, getters, setters, and various
 * calculation methods related to user activities and statistics.
 */
public class StandardUserTest {

    private StandardUser user;
    private ArrayList<Double> weightHistory;
    private ArrayList<Activity> activitiesList;

    /**
     * Sets up the test environment before each test.
     * Initializes a `StandardUser` object with sample weight history and activities.
     */
    @BeforeEach
    public void setUp() {
        // Initialize weight history
        weightHistory = new ArrayList<>(Arrays.asList(80.0, 79.5, 79.0, 78.5));

        // Initialize activities list
        activitiesList = new ArrayList<>();

        // Add sample activities
        activitiesList.add(new TestActivity(1, "Running", 300.0, 30.0, LocalDateTime.now().minusMinutes(60), LocalDateTime.now().minusMinutes(30)));
        activitiesList.add(new TestActivity(2, "Cycling", 450.0, 45.0, LocalDateTime.now().minusMinutes(120), LocalDateTime.now().minusMinutes(75)));
        activitiesList.add(new TestActivity(3, "Rope Jumping", 200.0, 20.0, LocalDateTime.now().minusMinutes(90), LocalDateTime.now().minusMinutes(70)));

        // Create StandardUser object
        user = new StandardUser(1, "Jan Kowalski", 78.5, 1.75, 30, "Male", weightHistory, activitiesList);
    }

    /**
     * Tests the constructor and getter methods of the `StandardUser` class.
     */
    @Test
    public void testConstructorAndGetters() {
        assertEquals(1, user.getId());
        assertEquals("Jan Kowalski", user.getName());
        assertEquals(78.5, user.getWeight());
        assertEquals(1.75, user.getHeight());
        assertEquals(30, user.getAge());
        assertEquals("Male", user.getGender());
        assertEquals(weightHistory, user.getWeightHistory());
        assertEquals(activitiesList, user.getActivitiesList());
    }

    /**
     * Tests the setter methods of the `StandardUser` class.
     */
    @Test
    public void testSetters() {
        user.setId(2);
        assertEquals(2, user.getId());

        user.setName("Anna Nowak");
        assertEquals("Anna Nowak", user.getName());

        user.setWeight(70.0);
        assertEquals(70.0, user.getWeight());

        user.setHeight(1.68);
        assertEquals(1.68, user.getHeight());

        user.setAge(28);
        assertEquals(28, user.getAge());

        user.setGender("Female");
        assertEquals("Female", user.getGender());
    }

    /**
     * Tests the BMI calculation method of the `StandardUser` class.
     */
    @Test
    public void testCalculateBMI() {
        double bmi = user.calculateBMI(user.getWeight(), user.getHeight());
        assertEquals(78.5 / (1.75 * 1.75), bmi, 0.0001);
    }

    /**
     * Tests the average activity time calculation method of the `StandardUser` class.
     */
    @Test
    public void testCalculateAverageActivityTime() {
        double expectedAverage = (30.0 + 45.0 + 20.0) / 3;
        assertEquals(expectedAverage, user.calculateAverageActivityTime(), 0.0001);
    }

    /**
     * Tests the average burned calories calculation method of the `StandardUser` class.
     */
    @Test
    public void testCalculateAverageActivityCalories() {
        double expectedAverage = (300.0 + 450.0 + 200.0) / 3;
        assertEquals(expectedAverage, user.calculateAverageActivityCalories(), 0.0001);
    }

    /**
     * Tests the total activity time calculation method of the `StandardUser` class.
     */
    @Test
    public void testCalculateTotalActivityTime() {
        double expectedTotal = 30.0 + 45.0 + 20.0;
        assertEquals(expectedTotal, user.calculateTotalActivityTime(), 0.0001);
    }

    /**
     * Tests the total burned calories calculation method of the `StandardUser` class.
     */
    @Test
    public void testCalculateTotalBurnedCalories() {
        double expectedTotal = 300.0 + 450.0 + 200.0;
        assertEquals(expectedTotal, user.calculateTotalBurnedCalories(), 0.0001);
    }

    /**
     * Tests the method that finds the longest activity performed by the user.
     */
    @Test
    public void testFindTheLongestActivity() {
        Activity longest = user.findTheLongestActivity();
        assertNotNull(longest);
        assertEquals("Cycling", longest.getName());
        assertEquals(45.0, longest.getDuration());
    }

    /**
     * Tests the method that finds the shortest activity performed by the user.
     */
    @Test
    public void testFindTheShortestActivity() {
        Activity shortest = user.findTheShortestActivity();
        assertNotNull(shortest);
        assertEquals("Rope Jumping", shortest.getName());
        assertEquals(20.0, shortest.getDuration());
    }

    /**
     * Tests the method that finds the most effective activity based on calories burned per minute.
     */
    @Test
    public void testFindMostEffectiveActivity() {
        Activity mostEffective = user.findMostEffectiveActivity();
        assertNotNull(mostEffective);
        // Check which activity has the highest kcal/min
        // Running: 300 / 30 = 10
        // Cycling: 450 / 45 = 10
        // Rope Jumping: 200 / 20 = 10
        // All have 10 kcal/min, so any of them can be returned
        assertTrue(Arrays.asList("Running", "Cycling", "Rope Jumping").contains(mostEffective.getName()));
        assertEquals(10.0, mostEffective.calculateCaloriesPerMinute(), 0.0001);
    }

    /**
     * Tests the BMI calculation method with zero height, which should handle the exception or return Infinity.
     */
    @Test
    public void testCalculateBMIWithZeroHeight() {
        double bmi = user.calculateBMI(user.getWeight(), 0.0);
        assertTrue(Double.isInfinite(bmi) || Double.isNaN(bmi), "BMI should be infinite or NaN with height 0");
    }
}
