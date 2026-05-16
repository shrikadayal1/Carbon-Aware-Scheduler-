package org.carbonaware.simulation;

public class ChartDriver {

    public static void main(String[] args) {

        // Example data from your previous simulation
        int[] time = {5, 10, 15, 20, 25};

        // Baseline Carbon values
        double[] baselineCarbon = {433.05, 430.66, 326.13, 182.54, 155.07};

        // Carbon-Aware Scheduler values
        double[] carbonAwareCarbon = {359.48, 403.50, 395.41, 314.69, 221.83};

        String[] actions = {"EXECUTE", "DEFER", "EXECUTE", "EXECUTE_SLA", "EXECUTE"}; // just for reference

        // Carbon Execution Scores
        double baselineCES = 3066.55;
        double carbonAwareCES = 2607.04;

        // Generate charts
        ChartGenerator.generateCESComparisonChart(baselineCES, carbonAwareCES);
        ChartGenerator.generateCarbonVsTimeChart(time, baselineCarbon, carbonAwareCarbon, actions);

        System.out.println("Charts generated successfully!");
    }
}
