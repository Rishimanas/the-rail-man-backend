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
    private final LocoMasterRosterService rosterService;

    public LocoSpottingService(SpotLogRepository spotLogRepository,
                               LocomotiveRepository locomotiveRepository,
                               UserRepository userRepository,
                               TrainRepository trainRepository,
                               LocoMasterRosterService rosterService) {
        this.spotLogRepository = spotLogRepository;
        this.locomotiveRepository = locomotiveRepository;
        this.userRepository = userRepository;
        this.trainRepository = trainRepository;
        this.rosterService = rosterService;
    }

    @Transactional
    public LocoSpotResponseDto submitSpot(SpotSubmissionRequest request) {
        Train train = trainRepository.findById(request.getTrainNumber())
                .orElseGet(() -> {
                    Train newTrain = new Train();
                    newTrain.setTrainNumber(request.getTrainNumber());
                    newTrain.setTrainName("Express " + request.getTrainNumber());
                    newTrain.setSourceStn(request.getFromStation() != null ? request.getFromStation() : "HWD");
                    newTrain.setDestStn(request.getToStation() != null ? request.getToStation() : "SC");
                    return trainRepository.save(newTrain);
                });

        int locoNo = request.getLocoNumber();
        LocoMasterRosterService.LocoProfile profile = rosterService.lookup(locoNo);

        Locomotive loco = locomotiveRepository.findById(locoNo)
                .map(existing -> {
                    existing.setShedCode(profile.shedCode);
                    existing.setLocoClass(profile.locoClass);
                    existing.setSpecialLivery(profile.livery);
                    existing.setIsPushPull(profile.isPushPull);
                    existing.setIsConverted(profile.isConverted);
                    return locomotiveRepository.save(existing);
                })
                .orElseGet(() -> {
                    Locomotive newLoco = new Locomotive();
                    newLoco.setLocoNumber(locoNo);
                    newLoco.setLocoClass(profile.locoClass);
                    newLoco.setShedCode(profile.shedCode);
                    newLoco.setSpecialLivery(profile.livery);
                    newLoco.setIsPushPull(profile.isPushPull);
                    newLoco.setIsConverted(profile.isConverted);
                    newLoco.setTractionType(profile.locoClass.startsWith("WD") ? "DIESEL" : "ELECTRIC");
                    newLoco.setStatus("IN_SERVICE");
                    return locomotiveRepository.save(newLoco);
                });

        UUID targetUuid = request.getSubmittedBy() != null
                ? request.getSubmittedBy()
                : UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11");

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

    @Transactional
    public List<LocoSpotResponseDto> getRecentHistory(String trainNumber) {
        LocalDate oneWeekAgo = LocalDate.now().minusDays(7);
        List<LocoSpottingLog> logs = spotLogRepository.findRecentSpotsByTrain(trainNumber, oneWeekAgo);
        List<LocoSpotResponseDto> dtos = new ArrayList<>();
        for (LocoSpottingLog log : logs) {
            if (log.getLocomotive() != null) {
                int num = log.getLocomotive().getLocoNumber();
                LocoMasterRosterService.LocoProfile p = rosterService.lookup(num);
                // Synchronize and heal stored database record
                if (!p.shedCode.equals(log.getLocomotive().getShedCode()) || !p.locoClass.equals(log.getLocomotive().getLocoClass())) {
                    log.getLocomotive().setShedCode(p.shedCode);
                    log.getLocomotive().setLocoClass(p.locoClass);
                    log.getLocomotive().setSpecialLivery(p.livery);
                    log.getLocomotive().setIsPushPull(p.isPushPull);
                    log.getLocomotive().setIsConverted(p.isConverted);
                    locomotiveRepository.save(log.getLocomotive());
                }
            }
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
            dto.setSpecialLivery(log.getLocomotive().getSpecialLivery());
            dto.setIsPushPull(log.getLocomotive().getIsPushPull());
        }
        dto.setSpottedAtStation(log.getSpottedAtStation());
        dto.setSpottedTime(log.getSpottedTime());
        dto.setConfidenceWeight(log.getConfidenceWeight());
        dto.setStatus(log.getStatus());
        return dto;
    }

    private int calculateInitialWeight(User user, String proofImageUrl) {
        int weight = 5;
        if ("TRUSTED_SPOTTER".equals(user.getBadgeTier())) weight = 30;
        else if ("VERIFIED_RAILFAN".equals(user.getBadgeTier())) weight = 15;
        if (proofImageUrl != null && !proofImageUrl.isBlank()) weight += 15;
        return weight;
    }
}