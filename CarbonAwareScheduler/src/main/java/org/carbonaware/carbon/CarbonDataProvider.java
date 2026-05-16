package org.carbonaware.carbon;

import java.util.Random;

public class CarbonDataProvider {

    private final Random random = new Random(42);

    /**
     * Simulates time-varying carbon intensity (gCO2/kWh)
     */
    public double getCarbonIntensityAtTime(int time) {

        double base = 300;
        double peakVariation = 150 * Math.sin(time / 5.0);
        double noise = random.nextDouble() * 30 - 15;

        double carbon = base + peakVariation + noise;

        return Math.max(150, Math.min(carbon, 600));
    }

    // 🔑 THIS is what the simulation expects
    public double getCarbonIntensity(int time) {
        return getCarbonIntensityAtTime(time);
    }
}
