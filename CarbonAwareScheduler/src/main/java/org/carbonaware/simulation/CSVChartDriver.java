package org.carbonaware.simulation;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;

public class CSVChartDriver {

    public static void main(String[] args) {

        String csvFile = "carbon_simulation_log.csv";

        List<Integer> times = new ArrayList<>();
        List<Double> carbonValues = new ArrayList<>();
        List<String> actions = new ArrayList<>();

        // ---------------------- Read CSV ----------------------
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                times.add(Integer.parseInt(parts[0]));
                carbonValues.add(Double.parseDouble(parts[2]));
                actions.add(parts[3]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ---------------------- Prepare Dataset ----------------------
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < times.size(); i++) {

            // Plot only executed workloads (aligns with CES logic)
            if (!actions.get(i).equals("DEFER")) {
                dataset.addValue(
                        carbonValues.get(i),
                        "Carbon-Aware Executed",
                        Integer.toString(times.get(i))
                );
            }
        }

        // ---------------------- Create Chart ----------------------
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Carbon Intensity vs Time (Carbon-Aware Execution)",
                "Time",
                "Predicted Carbon Intensity",
                dataset
        );

        // ---------------------- Save Chart ----------------------
        try {
            ChartUtils.saveChartAsPNG(
                    new File("Carbon_vs_Time_Auto.png"),
                    lineChart,
                    800,
                    600
            );
            System.out.println(
                    "Auto-generated Carbon vs Time chart saved as Carbon_vs_Time_Auto.png"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
