package org.carbonaware.prediction;

import org.carbonaware.carbon.CarbonDataProvider;

/**
 * Predicts near-future carbon intensity using lightweight moving-average modeling.
 * Acts as a simplified ML predictor based on historical carbon data.
 */
public class CarbonPredictionAgent {

    private final CarbonDataProvider carbonDataProvider;

    // Size of historical window for moving average prediction
    private static final int WINDOW_SIZE = 3;

    public CarbonPredictionAgent(CarbonDataProvider carbonDataProvider) {
        this.carbonDataProvider = carbonDataProvider;
    }

    /**
     * Predicts future carbon intensity at currentTime + delta.
     * Uses a moving average over past WINDOW_SIZE observations,
     * and adds a bias based on recent trend.
     *
     * @param currentTime Current simulation time
     * @param delta       Prediction horizon
     * @return predicted carbon intensity
     */
    public double predictCarbonIntensity(int currentTime, int delta) {

        double sum = 0.0;

        // Average over past WINDOW_SIZE steps
        for (int i = 1; i <= WINDOW_SIZE; i++) {
            int pastTime = Math.max(currentTime - i, 0);
            sum += carbonDataProvider.getCarbonIntensityAtTime(pastTime);
        }

        double average = sum / WINDOW_SIZE;

        // Bias toward future trend
        double futureBias =
                carbonDataProvider.getCarbonIntensityAtTime(currentTime + delta)
                - carbonDataProvider.getCarbonIntensityAtTime(currentTime);

        return average + 0.5 * futureBias;
    }
}
