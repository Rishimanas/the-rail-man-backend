package com.railapp.tracker.services;

import com.railapp.tracker.dto.LocoSpotResponseDto;
import com.railapp.tracker.dto.SpotSubmissionRequest;
import com.railapp.tracker.entities.LocoSpottingLog;
import com.railapp.tracker.entities.Locomotive;
import com.railapp.tracker.entities.Train;
import com.railapp.tracker.entities.User;
import com.railapp.tracker.repositories.LocomotiveRepository;
import com.railapp.tracker.repositories.SpotLogRepository;
import com.railapp.tracker.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocoSpottingService {

    private final SpotLogRepository spotLogRepository;
    private final LocomotiveRepository locomotiveRepository;
    private final UserRepository userRepository;

    public LocoSpottingService(SpotLogRepository spotLogRepository,
                               LocomotiveRepository locomotiveRepository,
                               UserRepository userRepository) {
        this.spotLogRepository = spotLogRepository;
        this.locomotiveRepository = locomotiveRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public LocoSpotResponseDto submitSpot(SpotSubmissionRequest request) {
        Locomotive loco = locomotiveRepository.findById(request.getLocoNumber())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Loco Number: " + request.getLocoNumber()));

        User user = userRepository.findById(request.getSubmittedBy())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + request.getSubmittedBy()));

        int initialWeight = calculateInitialWeight(user, request.getProofImageUrl());
        String initialStatus = initialWeight >= 25 ? "VERIFIED" : "PENDING";

        LocoSpottingLog spot = new LocoSpottingLog();
        Train train = new Train();
        train.setTrainNumber(request.getTrainNumber());
        spot.setTrain(train);
        spot.setRunDate(request.getRunDate());
        spot.setFromStationCode(request.getFromStation());
        spot.setToStationCode(request.getToStation());
        spot.setLocomotive(loco);
        spot.setSpottedAtStation(request.getSpottedAtStation());
        spot.setSpottedTime(request.getSpottedTime());
        spot.setSubmittedBy(user.getUserId());
        spot.setProofImageUrl(request.getProofImageUrl());
        spot.setConfidenceWeight(initialWeight);
        spot.setStatus(initialStatus);

        user.setTotalSpots(user.getTotalSpots() + 1);
        userRepository.save(user);

        LocoSpottingLog saved = spotLogRepository.save(spot);
        return mapToDto(saved);
    }

    public List<LocoSpotResponseDto> getLiveLocos(String trainNumber, LocalDate runDate) {
        List<LocoSpottingLog> logs = spotLogRepository.findVerifiedSpots(trainNumber, runDate);
        List<LocoSpotResponseDto> dtos = new ArrayList<>();
        for (LocoSpottingLog log : logs) {
            dtos.add(mapToDto(log));
        }
        return dtos;
    }

    private LocoSpotResponseDto mapToDto(LocoSpottingLog log) {
        LocoSpotResponseDto dto = new LocoSpotResponseDto();
        dto.setSpotId(log.getSpotId());
        if (log.getTrain() != null) {
            dto.setTrainNumber(log.getTrain().getTrainNumber());
        }
        dto.setRunDate(log.getRunDate());
        dto.setFromStationCode(log.getFromStationCode());
        dto.setToStationCode(log.getToStationCode());
        if (log.getLocomotive() != null) {
            dto.setLocoNumber(log.getLocomotive().getLocoNumber());
            dto.setLocoClass(log.getLocomotive().getLocoClass());
            dto.setShedCode(log.getLocomotive().getShedCode());
        }
        dto.setSpottedAtStation(log.getSpottedAtStation());
        dto.setSpottedTime(log.getSpottedTime());
        dto.setConfidenceWeight(log.getConfidenceWeight());
        dto.setStatus(log.getStatus());
        return dto;
    }

    private int calculateInitialWeight(User user, String proofImageUrl) {
        int weight = 5;
        if ("TRUSTED_SPOTTER".equals(user.getBadgeTier())) {
            weight = 30;
        } else if ("VERIFIED_RAILFAN".equals(user.getBadgeTier())) {
            weight = 15;
        }

        if (proofImageUrl != null && !proofImageUrl.isBlank()) {
            weight += 15;
        }
        return weight;
    }
}
