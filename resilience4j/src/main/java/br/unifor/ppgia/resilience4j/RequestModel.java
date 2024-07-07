package br.unifor.ppgia.resilience4j;

public class RequestModel {

    private int maxRequests;
    private int successfulRequests;
    private String targetUrl;

    public int getMaxRequests() {
        return maxRequests;
    }

    public int getSuccessfulRequests() {
        return successfulRequests;
    }

    public String getTargetUrl() {
        return targetUrl;
    }
}
