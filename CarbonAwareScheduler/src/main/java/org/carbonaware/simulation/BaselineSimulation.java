package org.carbonaware.simulation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.carbonaware.carbon.CarbonDataProvider;
import org.carbonaware.baseline.BaselineMicroserviceInstance;

public class BaselineSimulation {

    public static void main(String[] args) {
        try {
            // Read config
            ObjectMapper mapper = new ObjectMapper();
            JsonNode config = mapper.readTree(new File("resources/config.json"));
            List<Integer> timeIntervals = mapper.convertValue(config.get("timeIntervals"), List.class);
            String outputDir = config.get("outputDir").asText();

            Files.createDirectories(Paths.get(outputDir));

            // Prepare microservices
            List<BaselineMicroserviceInstance> services = List.of(
                new BaselineMicroserviceInstance("S1"),
                new BaselineMicroserviceInstance("S2")
            );

            CarbonDataProvider dataProvider = new CarbonDataProvider();

            String csvFile = outputDir + "/baseline_simulation.csv";
            try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {
                writer.println("Time,ServiceID,PredictedCarbon,Action");

                double carbonSum = 0;
                int execCount = 0;

                for (int time : timeIntervals) {
                    for (BaselineMicroserviceInstance service : services) {
                        double carbon = dataProvider.getCarbonIntensity(time);
                        String action = "EXECUTE";

                        System.out.println(time + "," + service.getServiceId() + "," + carbon + "," + action);
                        writer.println(time + "," + service.getServiceId() + "," + carbon + "," + action);

                        carbonSum += carbon;
                        execCount++;
                    }
                }

                System.out.println("\n=== BASELINE METRICS ===");
                System.out.println("Total Executions: " + execCount);
                System.out.println("Carbon Execution Score: " + carbonSum);
                System.out.println("\nBaseline CSV generated: " + csvFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
