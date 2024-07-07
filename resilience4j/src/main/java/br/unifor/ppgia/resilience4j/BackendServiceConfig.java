package br.unifor.ppgia.resilience4j;

import br.unifor.ppgia.resilience4j.circuitBreaker.BackendServiceWithCircuitBreaker;
import br.unifor.ppgia.resilience4j.circuitBreaker.CircuitBreakerRequestModel;
import br.unifor.ppgia.resilience4j.retry.BackendServiceWithRetry;
import br.unifor.ppgia.resilience4j.retry.RetryRequestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class BackendServiceConfig {
    private final static Logger logger = LoggerFactory.getLogger(BackendServiceConfig.class);

    @Value("${READ_TIMEOUT:0}")
    private Long readTimeout;

    @Bean
    public RestTemplate buildRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        logger.info("Read timeout is {}.", readTimeout);
        if (readTimeout > 0) {
            restTemplateBuilder.setReadTimeout(Duration.ofMillis(readTimeout));
        }
        return restTemplateBuilder.build();
    }

    @Bean
    public RestClient buildRestClient(RestTemplate restTemplate) {
        return new RestClient(restTemplate);
    }

    @Bean
    public BackendServiceWithRetry backendServiceWithRetry(
            RestClient restClient,
            @Value("${MULTIPLIER:#{null}}") Double multiplier,
            @Value("${INITIAL_INTERVAL_MILLIS:#{null}}") Integer initialIntervalMillis,
            @Value("${MAX_ATTEMPTS:#{null}}") Integer maxAttempts
    ) {
        var retryRequestModel = new RetryRequestModel(maxAttempts, multiplier, initialIntervalMillis);
        return new BackendServiceWithRetry(restClient, retryRequestModel);
    }

    @Bean
    public BackendServiceWithCircuitBreaker backendServiceWithCircuitBreaker(
            RestClient restClient,
            @Value("${FAILURE_RATE_THRESHOLD:#{null}}") Float failureRateThreshold,
            @Value("${SLIDING_WINDOW_SIZE:#{null}}") Integer slidingWindowSize,
            @Value("${MINIMUM_NUMBER_CALLS:#{null}}") Integer minimumNumberOfCalls,
            @Value("${WAIT_DURATION_OPEN_STATE:#{null}}") Integer waitDurationInOpenState,
            @Value("${PERMITTED_NUMBER_CALLS_HALF_OPEN_STATE:#{null}}") Integer permittedNumberOfCallsInHalfOpenState,
            @Value("${PERMITTED_NUMBER_CALLS_OPEN_STATE:#{null}}") Integer permittedNumberOfCallsInOpenState,
            @Value("${SLIDING_WINDOW_TYPE:#{null}}") String slidingWindowType
    ) {
        var requestModel = new CircuitBreakerRequestModel(
                failureRateThreshold,
                slidingWindowSize,
                minimumNumberOfCalls,
                waitDurationInOpenState,
                permittedNumberOfCallsInHalfOpenState,
                permittedNumberOfCallsInOpenState,
                slidingWindowType
        );
        return new BackendServiceWithCircuitBreaker(restClient, requestModel);
    }
}
