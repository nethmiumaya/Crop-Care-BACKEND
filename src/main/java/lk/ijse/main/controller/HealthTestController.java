package lk.ijse.main.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for health check endpoint.
 */
@RestController
@RequestMapping("api/v1/health")
@Slf4j
public class HealthTestController {

    /**
     * GET /api/v1/health : Health check endpoint.
     *
     * @return a string indicating the health status of the controller
     */
    @GetMapping
    public String healthCheck() {
        log.info("Health check endpoint called");
        return "HealthTestController is Running";
    }
}
