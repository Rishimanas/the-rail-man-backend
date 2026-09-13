package com.railapp.tracker.controllers;

import com.railapp.tracker.dto.TrainTrackingAnalyticsDto;
import com.railapp.tracker.services.TrainTrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trains")
@CrossOrigin(origins = "*")
public class TrainTrackingController {

    private final TrainTrackingService trackingService;

    public TrainTrackingController(TrainTrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @GetMapping("/{trainNumber}/track-analytics")
    public ResponseEntity<TrainTrackingAnalyticsDto> getTrainTrackingAnalytics(@PathVariable String trainNumber) {
        TrainTrackingAnalyticsDto data = trackingService.getTrainTrackingWith30DaysAnalytics(trainNumber);
        return ResponseEntity.ok(data);
    }
}