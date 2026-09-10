package com.railapp.tracker.controllers;

import com.railapp.tracker.dto.LocoSpotResponseDto;
import com.railapp.tracker.dto.SpotSubmissionRequest;
import com.railapp.tracker.services.LocoSpottingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/spots")
@CrossOrigin(origins = "*")
public class LocoSpottingController {

    private final LocoSpottingService spottingService;

    public LocoSpottingController(LocoSpottingService spottingService) {
        this.spottingService = spottingService;
    }

    @PostMapping("/submit")
    public ResponseEntity<LocoSpotResponseDto> submitSpotting(@RequestBody SpotSubmissionRequest request) {
        LocoSpotResponseDto savedSpot = spottingService.submitSpot(request);
        return ResponseEntity.ok(savedSpot);
    }

    @GetMapping("/live")
    public ResponseEntity<List<LocoSpotResponseDto>> getLiveLoco(
            @RequestParam String trainNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate runDate) {
        List<LocoSpotResponseDto> spots = spottingService.getLiveLocos(trainNumber, runDate);
        return ResponseEntity.ok(spots);
    }
}
