package org.carbonaware.simulation;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FullChartDriver {

    public static void main(String[] args) {
        String baselineCSV = "baseline_simulation.csv";
        String carbonAwareCSV = "carbon_aware_simulation.csv";

        generateCarbonVsTimeChart(baselineCSV, carbonAwareCSV);
        generateCESComparisonChart(baselineCSV, carbonAwareCSV);
        generateActionStackedChart(carbonAwareCSV);
    }

    // ---------------------- Carbon vs Time Line Chart ----------------------
    private static void generateCarbonVsTimeChart(String baselineCSV, String carbonAwareCSV) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        readCarbonOverTime(baselineCSV, dataset, "Baseline");
        readCarbonOverTime(carbonAwareCSV, dataset, "CarbonAware");

        JFreeChart chart = ChartFactory.createLineChart(
                "Predicted Carbon vs Time",
                "Time",
                "Predicted Carbon",
                dataset
        );

        saveChart(chart, "Carbon_vs_Time.png", 800, 600);
    }

    private static void readCarbonOverTime(String csv, DefaultCategoryDataset dataset, String label) {
        try (BufferedReader br = new BufferedReader(new FileReader(csv))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int time = Integer.parseInt(parts[0].trim());
                String serviceId = parts[1].trim();
                double carbon = Double.parseDouble(parts[2].trim());

                dataset.addValue(carbon, serviceId + "-" + label, String.valueOf(time));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------------- CES Comparison Bar Chart ----------------------
    private static void generateCESComparisonChart(String baselineCSV, String carbonAwareCSV) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        double baselineCES = computeTotalCES(baselineCSV);
        double carbonAwareCES = computeTotalCES(carbonAwareCSV);

        dataset.addValue(baselineCES, "Baseline", "CES");
        dataset.addValue(carbonAwareCES, "Carbon-Aware", "CES");

        JFreeChart chart = ChartFactory.createBarChart(
                "Carbon Execution Score (CES) Comparison",
                "Simulation Type",
                "Total CES (Non-Deferred)",
                dataset
        );

        saveChart(chart, "CES_Comparison.png", 800, 600);
    }

    private static double computeTotalCES(String csvFile) {
        double totalCES = 0.0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length < 4) continue;

                String action = parts[3].trim();
                double ces = Double.parseDouble(parts[2].trim());

                // Only count executed tasks
                if (!action.equalsIgnoreCase("DEFER")) {
                    totalCES += ces;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalCES;
    }

    // ---------------------- Stacked Action Chart ----------------------
    private static void generateActionStackedChart(String carbonAwareCSV) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (BufferedReader br = new BufferedReader(new FileReader(carbonAwareCSV))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                int time = Integer.parseInt(parts[0].trim());
                String serviceId = parts[1].trim();
                String action = parts[3].trim();

                dataset.addValue(1, action, serviceId + "@" + time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createStackedBarChart(
                "Microservice Actions Over Time (Carbon-Aware)",
                "Service@Time",
                "Action Count",
                dataset
        );

        saveChart(chart, "Action_Stacked_Bar.png", 1000, 600);
    }

    // ---------------------- Utility ----------------------
    private static void saveChart(JFreeChart chart, String fileName, int w, int h) {
        try {
            ChartUtils.saveChartAsPNG(new File(fileName), chart, w, h);
            System.out.println(fileName + " saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
