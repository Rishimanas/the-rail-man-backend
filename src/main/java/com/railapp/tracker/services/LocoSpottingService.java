package com.railapp.tracker.services;

import com.railapp.tracker.dto.LocoSpotResponseDto;
import com.railapp.tracker.dto.SpotSubmissionRequest;
import com.railapp.tracker.entities.LocoSpottingLog;
import com.railapp.tracker.entities.Locomotive;
import com.railapp.tracker.entities.Train;
import com.railapp.tracker.entities.User;
import com.railapp.tracker.repositories.LocomotiveRepository;
import com.railapp.tracker.repositories.SpotLogRepository;
import com.railapp.tracker.repositories.TrainRepository;
import com.railapp.tracker.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LocoSpottingService {

    private final SpotLogRepository spotLogRepository;
    private final LocomotiveRepository locomotiveRepository;
    private final UserRepository userRepository;
    private final TrainRepository trainRepository;

    public LocoSpottingService(SpotLogRepository spotLogRepository,
                               LocomotiveRepository locomotiveRepository,
                               UserRepository userRepository,
                               TrainRepository trainRepository) {
        this.spotLogRepository = spotLogRepository;
        this.locomotiveRepository = locomotiveRepository;
        this.userRepository = userRepository;
        this.trainRepository = trainRepository;
    }

    @Transactional
    public LocoSpotResponseDto submitSpot(SpotSubmissionRequest request) {
        Train train = trainRepository.findById(request.getTrainNumber())
                .orElseGet(() -> {
                    Train newTrain = new Train();
                    newTrain.setTrainNumber(request.getTrainNumber());
                    newTrain.setTrainName("Express " + request.getTrainNumber());
                    newTrain.setSourceStn(request.getFromStation() != null ? request.getFromStation() : "SRC");
                    newTrain.setDestStn(request.getToStation() != null ? request.getToStation() : "DEST");
                    return trainRepository.save(newTrain);
                });

        int locoNo = request.getLocoNumber();
        String detectedClass = determineLocoClass(locoNo);
        String detectedShed = determineShedCode(locoNo);

        Locomotive loco = locomotiveRepository.findById(locoNo)
                .map(existing -> {
                    if ("SRC".equals(existing.getShedCode()) && !"SRC".equals(detectedShed)) {
                        existing.setShedCode(detectedShed);
                        existing.setLocoClass(detectedClass);
                        return locomotiveRepository.save(existing);
                    }
                    return existing;
                })
                .orElseGet(() -> {
                    Locomotive newLoco = new Locomotive();
                    newLoco.setLocoNumber(locoNo);
                    newLoco.setLocoClass(detectedClass);
                    newLoco.setShedCode(detectedShed);
                    newLoco.setTractionType(detectedClass.startsWith("WD") ? "DIESEL" : "ELECTRIC");
                    newLoco.setStatus("IN_SERVICE");
                    return locomotiveRepository.save(newLoco);
                });

        UUID userUuid;
        try {
            userUuid = UUID.fromString(request.getSubmittedBy());
        } catch (Exception e) {
            userUuid = UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11");
        }

        final UUID targetUuid = userUuid;
        User user = userRepository.findById(targetUuid)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setUserId(targetUuid);
                    newUser.setUsername("railfan_spotter");
                    newUser.setTrustScore(10);
                    newUser.setTotalSpots(0);
                    newUser.setVerifiedSpots(0);
                    newUser.setBadgeTier("NOVICE");
                    return userRepository.save(newUser);
                });

        int initialWeight = calculateInitialWeight(user, request.getProofImageUrl());
        String initialStatus = initialWeight >= 25 ? "VERIFIED" : "PENDING";

        LocoSpottingLog spot = new LocoSpottingLog();
        spot.setTrain(train);
        spot.setRunDate(request.getRunDate());
        spot.setFromStationCode(request.getFromStation());
        spot.setToStationCode(request.getToStation());
        spot.setLocomotive(loco);
        spot.setSpottedAtStation(request.getSpottedAtStation());
        spot.setSpottedTime(request.getSpottedTime());
        // String conversion fix
        spot.setSubmittedBy(user.getUserId().toString());
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

    public List<LocoSpotResponseDto> getRecentHistory(String trainNumber) {
        LocalDate oneWeekAgo = LocalDate.now().minusDays(7);
        List<LocoSpottingLog> logs = spotLogRepository.findRecentSpotsByTrain(trainNumber, oneWeekAgo);
        List<LocoSpotResponseDto> dtos = new ArrayList<>();
        for (LocoSpottingLog log : logs) {
            dtos.add(mapToDto(log));
        }
        return dtos;
    }

    private String determineLocoClass(int locoNo) {
        if (locoNo >= 30000 && locoNo <= 30800) return "WAP-7";
        if (locoNo >= 37000 && locoNo <= 39999) return "WAP-7";
        if (locoNo >= 22200 && locoNo <= 22999) return "WAP-4";
        if (locoNo >= 30000 && locoNo <= 30200) return "WAP-5";
        if (locoNo >= 27000 && locoNo <= 28999) return "WAG-7";
        if (locoNo >= 31000 && locoNo <= 33999) return "WAG-9";
        if (locoNo >= 41000 && locoNo <= 43999) return "WAG-12B";
        if (locoNo >= 70000 && locoNo <= 70999) return "WDG-4G";
        return "WAP-7";
    }

    private String determineShedCode(int locoNo) {
        if (locoNo >= 39630 && locoNo <= 39670) return "ANGL";
        if (locoNo >= 37100 && locoNo <= 37150) return "ANGL";
        if (locoNo == 22501 || (locoNo >= 22500 && locoNo <= 22550)) return "SRC";
        if (locoNo >= 30450 && locoNo <= 30500) return "SRC";
        if (locoNo >= 39200 && locoNo <= 39250) return "VSKP";
        if (locoNo >= 31800 && locoNo <= 31900) return "BNDM";
        if (locoNo >= 30250 && locoNo <= 30350) return "LGD";
        if (locoNo >= 30351 && locoNo <= 30420) return "RPM";
        if (locoNo >= 30201 && locoNo <= 30240) return "GZB";
        return "ANGL";
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