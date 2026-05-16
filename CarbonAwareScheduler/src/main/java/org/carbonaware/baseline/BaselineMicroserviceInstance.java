package org.carbonaware.baseline;

public class BaselineMicroserviceInstance {

    private final String serviceId;
    private int executionCount = 0;

    public BaselineMicroserviceInstance(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String execute() {
        executionCount++;
        return "EXECUTE";
    }

    public int getExecutionCount() {
        return executionCount;
    }
}
