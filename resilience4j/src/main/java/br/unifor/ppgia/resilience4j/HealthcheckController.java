package br.unifor.ppgia.resilience4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/healthx")
public class HealthcheckController {
        
    @GetMapping
    public ResponseEntity<?> index() {
        return ResponseEntity.ok().build();
    }
}
