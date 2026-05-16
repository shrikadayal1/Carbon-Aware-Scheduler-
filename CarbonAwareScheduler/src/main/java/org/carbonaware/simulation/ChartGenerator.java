package org.carbonaware.simulation;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import java.io.File;

public class ChartGenerator {

    public static void generateCESComparisonChart(double baselineCES, double carbonAwareCES) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(baselineCES, "Baseline", "Scheduler");
        dataset.addValue(carbonAwareCES, "Carbon-Aware", "Scheduler");

        JFreeChart barChart = ChartFactory.createBarChart(
                "Carbon Execution Score Comparison",
                "Scheduler Type",
                "CES",
                dataset
        );

        try {
            ChartUtils.saveChartAsPNG(new File("CES_Comparison.png"), barChart, 640, 480);
            System.out.println("CES comparison chart saved as CES_Comparison.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateCarbonVsTimeChart(int[] time, double[] baselineCarbon, double[] carbonAwareCarbon, String[] actions) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < time.length; i++) {
            dataset.addValue(baselineCarbon[i], "Baseline", Integer.toString(time[i]));
            dataset.addValue(carbonAwareCarbon[i], "Carbon-Aware", Integer.toString(time[i]));
        }

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Predicted Carbon vs Time",
                "Time",
                "Predicted Carbon",
                dataset
        );

        try {
            ChartUtils.saveChartAsPNG(new File("Carbon_vs_Time.png"), lineChart, 800, 600);
            System.out.println("Carbon vs Time chart saved as Carbon_vs_Time.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
