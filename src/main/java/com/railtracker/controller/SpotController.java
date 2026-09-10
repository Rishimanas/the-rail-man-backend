package com.railtracker.controller;

import com.railtracker.model.SpottingLog;
import com.railtracker.repository.SpottingLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/spots")
@CrossOrigin(origins = "*")
public class SpotController {

    @Autowired
    private SpottingLogRepository spottingLogRepository;

    @GetMapping("/latest")
    public ResponseEntity<?> getLatestSpot(@RequestParam String trainNumber) {
        return spottingLogRepository.findLatestActiveSpotByTrain(trainNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/feed")
    public ResponseEntity<List<SpottingLog>> getLiveFeed() {
        return ResponseEntity.ok(spottingLogRepository.findRecentCommunityFeed());
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitSpot(@RequestBody SpottingLog spot) {
        SpottingLog saved = spottingLogRepository.save(spot);
        return ResponseEntity.ok(saved);
    }
}
