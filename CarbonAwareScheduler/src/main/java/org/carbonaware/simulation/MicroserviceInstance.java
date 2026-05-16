package org.carbonaware.simulation;

import org.carbonaware.prediction.CarbonPredictionAgent;
import org.carbonaware.decision.SustainabilityDecisionEngine;

/**
 * Represents a microservice instance in the carbon-aware simulation.
 * Each instance predicts carbon intensity and decides whether to execute,
 * defer, or execute under SLA.
 */
public class MicroserviceInstance {

    private final String serviceId;
    private final CarbonPredictionAgent predictor;
    private final SustainabilityDecisionEngine decisionEngine;

    private int deferralCount = 0; // Tracks consecutive deferrals

    public MicroserviceInstance(String serviceId,
                                CarbonPredictionAgent predictor,
                                SustainabilityDecisionEngine decisionEngine) {
        this.serviceId = serviceId;
        this.predictor = predictor;
        this.decisionEngine = decisionEngine;
    }

    public String getServiceId() {
        return serviceId;
    }

    /**
     * Predicts future carbon intensity using the prediction agent.
     *
     * @param currentTime Current simulation time
     * @param delta       Prediction horizon
     * @return predicted carbon intensity
     */
    public double predictCarbon(int currentTime, int delta) {
        return predictor.predictCarbonIntensity(currentTime, delta);
    }

    /**
     * Decides the action to take based on predicted carbon and deferral count.
     * Updates deferralCount internally.
     *
     * @param currentTime Current simulation time
     * @param delta       Prediction horizon
     * @return action string: EXECUTE, DEFER, EXECUTE_SLA
     */
    public String decide(int currentTime, int delta) {

        double predictedCarbon = predictCarbon(currentTime, delta);
        String action = decisionEngine.decideAction(predictedCarbon, deferralCount);

        // Update deferral counter
        if (action.equals("DEFER")) {
            deferralCount++;
        } else {
            deferralCount = 0; // reset after execution
        }

        return action;
    }
}
