package com.railapp.tracker.controllers;

import com.railapp.tracker.entities.PassengerCoachFeedback;
import com.railapp.tracker.repositories.PassengerFeedbackRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/feedback")
@CrossOrigin(origins = "*")
public class FeedbackController {

    private final PassengerFeedbackRepository feedbackRepository;

    public FeedbackController(PassengerFeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @PostMapping("/submit")
    public ResponseEntity<PassengerCoachFeedback> submitFeedback(@RequestBody PassengerCoachFeedback feedback) {
        PassengerCoachFeedback saved = feedbackRepository.save(feedback);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{trainNumber}")
    public ResponseEntity<List<PassengerCoachFeedback>> getTrainFeedbacks(@PathVariable String trainNumber) {
        List<PassengerCoachFeedback> list = feedbackRepository.findByTrainNumberOrderBySubmittedAtDesc(trainNumber);
        return ResponseEntity.ok(list);
    }
}