package org.carbonaware.logging;

import java.io.FileWriter;
import java.io.IOException;

public class EnvironmentalImpactLogger {

    private FileWriter csvWriter;
    private int totalExecutions = 0;
    private int totalDeferrals = 0;
    private int slaForcedExecutions = 0;
    private double carbonExecutionScore = 0.0;

    public EnvironmentalImpactLogger() {
        try {
            csvWriter = new FileWriter("carbon_simulation_log.csv");
            csvWriter.append("Time,ServiceID,PredictedCarbon,Action\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(int time, String serviceId, double predictedCarbon, String action) {
        System.out.println(time + "," + serviceId + "," + predictedCarbon + "," + action);
        try {
            csvWriter.append(time + "," + serviceId + "," + predictedCarbon + "," + action + "\n");
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Update metrics
        carbonExecutionScore += predictedCarbon;
        if (action.equals("EXECUTE") || action.equals("EXECUTE_SLA")) {
            totalExecutions++;
        }
        if (action.equals("DEFER")) {
            totalDeferrals++;
        }
        if (action.equals("EXECUTE_SLA")) {
            slaForcedExecutions++;
        }
    }

    public void printHeader() {
        System.out.println("Time,ServiceID,PredictedCarbon,Action");
    }

    public void printMetrics() {
        System.out.println("\n=== METRICS SUMMARY ===");
        System.out.println("Total Executions: " + totalExecutions);
        System.out.println("Total Deferrals: " + totalDeferrals);
        System.out.println("SLA Forced Executions: " + slaForcedExecutions);
        System.out.println("Carbon Execution Score: " + carbonExecutionScore);
    }

    public void close() {
        try {
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
