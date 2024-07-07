package br.unifor.ppgia.resilience4j.circuitBreaker;

import br.unifor.ppgia.resilience4j.RequestModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cb")
public class CircuitBreakerController {

    private final BackendServiceWithCircuitBreaker backendService;

    public CircuitBreakerController(BackendServiceWithCircuitBreaker backendService) {
        this.backendService = backendService;
    }

    @PostMapping
    public ResponseEntity<?> index(@RequestBody RequestModel requestModel) {
        var metrics = backendService.doHttpRequest(requestModel);
        return ResponseEntity.ok(metrics);
    }
}
