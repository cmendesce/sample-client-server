package br.unifor.ppgia.resilience4j.baseline;

import br.unifor.ppgia.resilience4j.RequestModel;
import br.unifor.ppgia.resilience4j.RestClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/baseline")
public class BaselineController {

    private final RestClient restClient;

    public BaselineController(RestClient restClient) {
        this.restClient = restClient;
    }

    @PostMapping
    public ResponseEntity<?> index(@RequestBody RequestModel requestModel) {
        var backendService = new BackendServiceSimple(restClient);
        var metrics = backendService.doHttpRequest(requestModel);
        return ResponseEntity.ok(metrics);
    }
}
