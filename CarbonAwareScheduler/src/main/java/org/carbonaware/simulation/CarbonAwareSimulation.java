package org.carbonaware.simulation;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.carbonaware.carbon.CarbonDataProvider;
import org.carbonaware.prediction.CarbonPredictionAgent;
import org.carbonaware.decision.SustainabilityDecisionEngine;
import org.carbonaware.logging.EnvironmentalImpactLogger;

public class CarbonAwareSimulation {

    public static void main(String[] args) {
        try {
            // Read config
            ObjectMapper mapper = new ObjectMapper();
            JsonNode config = mapper.readTree(new File("resources/config.json"));
            List<Integer> timeIntervals = mapper.convertValue(config.get("timeIntervals"), List.class);
            int delta = config.get("predictionDelta").asInt();
            String outputDir = config.get("outputDir").asText();

            Files.createDirectories(Paths.get(outputDir));

            // Create microservices from config
            List<MicroserviceInstance> services = new ArrayList<>();
            for (JsonNode svc : config.get("services")) {
                String id = svc.get("id").asText();
                double slaThreshold = svc.get("slaThreshold").asDouble();
                int flexibility = svc.get("flexibility").asInt();

                services.add(new MicroserviceInstance(
                        id,
                        new CarbonPredictionAgent(new CarbonDataProvider()),
                        new SustainabilityDecisionEngine(slaThreshold, flexibility)
                ));
            }

            String csvFile = outputDir + "/carbon_aware_simulation.csv";

            try (PrintWriter csvWriter = new PrintWriter(new FileWriter(csvFile))) {

                csvWriter.println("Time,ServiceID,PredictedCarbon,Action");

                EnvironmentalImpactLogger logger = new EnvironmentalImpactLogger();
                logger.printHeader();

                for (int currentTime : timeIntervals) {
                    for (MicroserviceInstance service : services) {
                        double predictedCarbon = service.predictCarbon(currentTime, delta);
                        String action = service.decide(currentTime, delta);

                        logger.log(currentTime + delta, service.getServiceId(), predictedCarbon, action);
                        csvWriter.println(currentTime + delta + "," + service.getServiceId() + "," + predictedCarbon + "," + action);
                    }
                }

                logger.printMetrics();
                System.out.println("\nCarbon-aware CSV generated: " + csvFile);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
