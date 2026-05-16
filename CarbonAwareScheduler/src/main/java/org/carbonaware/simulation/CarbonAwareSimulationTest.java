package org.carbonaware.simulation;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CarbonAwareSimulationTest {

    public static void main(String[] args) {
        System.out.println("=== Running CarbonAwareSimulation Test ===");

        // Run the main simulation
        CarbonAwareSimulation.main(args);

        // Check if CSV exists
        String csvFile = "simulation_output/carbon_aware_simulation.csv";
        File file = new File(csvFile);

        if (file.exists() && file.length() > 0) {
            System.out.println("PASS: CSV generated and is not empty: " + csvFile);
        } else {
            System.out.println("FAIL: CSV missing or empty");
        }

        // Optional: quick preview first few lines
        try {
            System.out.println("\nPreview of first 5 lines:");
            Files.lines(Paths.get(csvFile)).limit(5).forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("=== Test Completed ===");
    }
}
