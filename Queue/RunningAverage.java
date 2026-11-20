/**
 * RunningAverage.java
 *
 * A complete and robust implementation of a Running Average (cumulative moving average) calculator.
 * Computes the mean of a stream of incoming double precision numbers in O(1) time and O(1) space complexity.
 *
 * Core Features:
 *  - add(double value): Appends a new sample to the running dataset.
 *  - getAverage(): Returns the current cumulative average. Returns 0.0 if no elements have been added.
 *  - getCount(): Returns the total number of samples processed so far.
 *  - getSum(): Returns the cumulative sum of all samples processed so far.
 *  - reset(): Clears all recorded samples, resetting count, sum, and average to zero.
 *  - main(String[] args): Comprehensive demonstration of the calculator across various scenarios.
 */
public class RunningAverage {

    // Total number of elements added to the running average.
    private long count;

    // Cumulative sum of all elements added.
    private double sum;

    /**
     * Default constructor initializing an empty RunningAverage instance.
     * Starts with count = 0 and sum = 0.0.
     */
    public RunningAverage() {
        this.count = 0;
        this.sum = 0.0;
    }

    /**
     * Adds a new value to the running average calculation.
     * Updates both the total count and running sum in O(1) time.
     *
     * @param value The numerical value to incorporate into the average.
     */
    public void add(double value) {
        this.count++;
        this.sum += value;
    }

    /**
     * Calculates and returns the current running average (arithmetic mean).
     *
     * Formula: Average = Sum / Count
     * If no values have been added yet (count == 0), returns 0.0 to prevent division by zero.
     *
     * @return The arithmetic mean of all added values, or 0.0 if empty.
     */
    public double getAverage() {
        if (this.count == 0) {
            return 0.0;
        }
        return this.sum / this.count;
    }

    /**
     * Returns the total count of values that have been added.
     *
     * @return The number of values processed.
     */
    public long getCount() {
        return this.count;
    }

    /**
     * Returns the cumulative sum of all values that have been added.
     *
     * @return The running total sum.
     */
    public double getSum() {
        return this.sum;
    }

    /**
     * Resets the running average calculator to its initial state (count = 0, sum = 0.0).
     */
    public void reset() {
        this.count = 0;
        this.sum = 0.0;
    }

    /**
     * Returns a string representation of the current state of the running average calculator.
     *
     * @return A formatted string showing count, sum, and average.
     */
    @Override
    public String toString() {
        return String.format("RunningAverage[count=%d, sum=%.4f, average=%.4f]",
                count, sum, getAverage());
    }

    /**
     * Demonstration and testing entry point.
     * Demonstrates:
     *  1. Initial state behavior (empty calculator).
     *  2. Step-by-step additions with floating-point values.
     *  3. Handling of negative values and zero.
     *  4. Resetting and reusing the calculator instance.
     *  5. Processing a stream of simulated sensor data.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("         RUNNING AVERAGE CALCULATOR DEMO          ");
        System.out.println("==================================================");

        RunningAverage avg = new RunningAverage();

        // 1. Initial state demonstration
        System.out.println("\n--- 1. Initial State (No elements added) ---");
        System.out.println("Count  : " + avg.getCount());
        System.out.println("Sum    : " + avg.getSum());
        System.out.println("Average: " + avg.getAverage());
        System.out.println("State  : " + avg);

        // 2. Incremental additions
        System.out.println("\n--- 2. Adding Stream of Values ---");
        double[] sampleValues = { 10.0, 20.0, 30.0, 40.0, 50.0 };

        System.out.printf("%-11s | %-8s | %-12s | %-12s%n", "Value Added", "Count", "Current Sum", "Running Avg");
        System.out.println("------------+----------+--------------+-------------");

        for (double val : sampleValues) {
            avg.add(val);
            System.out.printf("%-11.2f | %-8d | %-12.2f | %-12.4f%n",
                    val, avg.getCount(), avg.getSum(), avg.getAverage());
        }

        // 3. Testing with negative numbers and decimals
        System.out.println("\n--- 3. Adding Mixed (Negative & Decimal) Values ---");
        double[] mixedValues = { -15.5, 25.25, -5.75, 100.0 };

        for (double val : mixedValues) {
            avg.add(val);
            System.out.printf("Added: %+8.2f => Count: %-2d | Sum: %-9.2f | Average: %-8.4f%n",
                    val, avg.getCount(), avg.getSum(), avg.getAverage());
        }

        // 4. Testing Reset Functionality
        System.out.println("\n--- 4. Resetting Calculator ---");
        System.out.println("Before reset: " + avg);
        avg.reset();
        System.out.println("After reset : " + avg);

        // 5. Simulating a stream of temperature readings
        System.out.println("\n--- 5. Sensor Stream Simulation (e.g. Temperature Readings) ---");
        double[] temperatures = { 21.5, 22.0, 21.8, 23.1, 22.9, 23.5, 24.0, 23.8 };
        System.out.println("Feeding temperature readings (Celsius):");
        for (double temp : temperatures) {
            avg.add(temp);
            System.out.printf("  Reading: %.1f C -> Cumulative Average: %.3f C (Total readings: %d)%n",
                    temp, avg.getAverage(), avg.getCount());
        }

        System.out.println("\nFinal Summary:");
        System.out.println("Total Readings : " + avg.getCount());
        System.out.printf("Sum of Readings: %.2f%n", avg.getSum());
        System.out.printf("Final Average  : %.4f%n", avg.getAverage());
        System.out.println("==================================================");
    }
}
