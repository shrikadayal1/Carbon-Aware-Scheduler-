package org.carbonaware.decision;

/**
 * Makes carbon-aware scheduling decisions for a microservice.
 * Can defer execution or force execution based on SLA constraints.
 */
public class SustainabilityDecisionEngine {

    private final double carbonThreshold;
    private final int maxDeferralSteps;

    public SustainabilityDecisionEngine(double carbonThreshold, int maxDeferralSteps) {
        this.carbonThreshold = carbonThreshold;
        this.maxDeferralSteps = maxDeferralSteps;
    }

    /**
     * Determines scheduling action based on predicted carbon intensity
     * and consecutive deferral count.
     *
     * @param predictedCarbon Predicted carbon intensity
     * @param deferralCount   Number of consecutive deferrals
     * @return Action string: EXECUTE, DEFER, EXECUTE_SLA
     */
    public String decideAction(double predictedCarbon, int deferralCount) {

        // SLA hard stop: force execution
        if (deferralCount >= maxDeferralSteps) {
            return "EXECUTE_SLA";
        }

        // Carbon exceeds threshold: defer execution
        if (predictedCarbon > carbonThreshold) {
            return "DEFER";
        }

        // Normal execution
        return "EXECUTE";
    }
}
