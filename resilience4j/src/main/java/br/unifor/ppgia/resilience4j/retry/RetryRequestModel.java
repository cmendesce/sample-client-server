package br.unifor.ppgia.resilience4j.retry;

public class RetryRequestModel {
    private final Integer maxAttempts;
    private final Double multiplier;
    private final Integer initialIntervalMillis;

    public RetryRequestModel(Integer maxAttempts, Double multiplier, Integer initialIntervalMillis) {
        this.maxAttempts = maxAttempts;
        this.multiplier = multiplier;
        this.initialIntervalMillis = initialIntervalMillis;
    }

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public Integer getInitialIntervalMillis() {
        return initialIntervalMillis;
    }

    public Double getMultiplier() {
        return multiplier;
    }
}
