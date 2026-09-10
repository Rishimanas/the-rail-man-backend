package com.railapp.tracker.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@Service
public class RailRadarClientService {

    private final RestTemplate restTemplate;
    private static final String RAILRADAR_API_BASE = "https://railradar.in/api/v1";

    public RailRadarClientService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Fetches live telemetry (lat, long, speed, delay) from RailRadar crowdsourced feeds
     */
    public Map<String, Object> fetchLiveTrainStatus(String trainNumber) {
        try {
            String url = RAILRADAR_API_BASE + "/trains/" + trainNumber + "/live";
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            return response.getBody();
        } catch (Exception e) {
            // Graceful fallback to internal tracking logs if external radar is throttled
            return Map.of("trainNumber", trainNumber, "status", "OFFLINE_FALLBACK", "error", e.getMessage());
        }
    }
}