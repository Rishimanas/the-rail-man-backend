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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LocoSpottingService {

    private static final Logger log = LoggerFactory.getLogger(LocoSpottingService.class);

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

    private String safeTrim(String val, int maxLen, String def) {
        if (val == null || val.trim().isEmpty()) return def;
        String t = val.trim();
        return t.length() > maxLen ? t.substring(0, maxLen) : t;
    }

    @Transactional
    public LocoSpotResponseDto submitSpot(SpotSubmissionRequest request) {
        String safeTrain = safeTrim(request.getTrainNumber(), 20, "12703");
        String safeFrom = safeTrim(request.getFromStation(), 20, "HWD");
        String safeTo = safeTrim(request.getToStation(), 20, "SC");
        String safeSpotted = safeTrim(request.getSpottedAtStation(), 20, "CHATRAPUR");

        Train train = trainRepository.findById(safeTrain)
                .orElseGet(() -> {
                    Train newTrain = new Train();
                    newTrain.setTrainNumber(safeTrain);
                    newTrain.setTrainName("Express " + safeTrain);
                    newTrain.setSourceStn(safeFrom);
                    newTrain.setDestStn(safeTo);
                    return trainRepository.save(newTrain);
                });

        int locoNo = request.getLocoNumber() != null ? request.getLocoNumber() : 39184;
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

        UUID targetUuid = UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11");

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

        LocoSpottingLog spot = new LocoSpottingLog();
        spot.setTrain(train);
        spot.setRunDate(request.getRunDate() != null ? request.getRunDate() : LocalDate.now());
        spot.setFromStationCode(safeFrom);
        spot.setToStationCode(safeTo);
        spot.setLocomotive(loco);
        spot.setSpottedAtStation(safeSpotted);
        spot.setSpottedTime(OffsetDateTime.now(ZoneOffset.UTC));
        spot.setSubmittedBy(user.getUserId());
        spot.setProofImageUrl("https://railradar.in/proofs/sample.jpg");
        spot.setConfidenceWeight(30);
        spot.setStatus("VERIFIED");

        user.setTotalSpots(user.getTotalSpots() + 1);
        userRepository.save(user);

        LocoSpottingLog saved = spotLogRepository.save(spot);
        return mapToDto(saved);
    }

    public List<LocoSpotResponseDto> getLiveLocos(String trainNumber, LocalDate runDate) {
        List<LocoSpottingLog> logs = spotLogRepository.findVerifiedSpots(trainNumber, runDate);
        List<LocoSpotResponseDto> dtos = new ArrayList<>();
        for (LocoSpottingLog logItem : logs) {
            dtos.add(mapToDto(logItem));
        }
        return dtos;
    }

    @Transactional
    public List<LocoSpotResponseDto> getRecentHistory(String trainNumber) {
        LocalDate oneWeekAgo = LocalDate.now().minusDays(7);
        List<LocoSpottingLog> logs = spotLogRepository.findRecentSpotsByTrain(trainNumber, oneWeekAgo);
        List<LocoSpotResponseDto> dtos = new ArrayList<>();
        for (LocoSpottingLog logItem : logs) {
            if (logItem.getLocomotive() != null) {
                int num = logItem.getLocomotive().getLocoNumber();
                LocoMasterRosterService.LocoProfile p = rosterService.lookup(num);
                logItem.getLocomotive().setShedCode(p.shedCode);
                logItem.getLocomotive().setLocoClass(p.locoClass);
                logItem.getLocomotive().setSpecialLivery(p.livery);
                logItem.getLocomotive().setIsPushPull(p.isPushPull);
                logItem.getLocomotive().setIsConverted(p.isConverted);
                locomotiveRepository.save(logItem.getLocomotive());
            }
            dtos.add(mapToDto(logItem));
        }
        return dtos;
    }

    private LocoSpotResponseDto mapToDto(LocoSpottingLog logItem) {
        LocoSpotResponseDto dto = new LocoSpotResponseDto();
        dto.setSpotId(logItem.getSpotId());
        if (logItem.getTrain() != null) {
            dto.setTrainNumber(logItem.getTrain().getTrainNumber());
        }
        dto.setRunDate(logItem.getRunDate());
        dto.setFromStationCode(logItem.getFromStationCode());
        dto.setToStationCode(logItem.getToStationCode());
        if (logItem.getLocomotive() != null) {
            dto.setLocoNumber(logItem.getLocomotive().getLocoNumber());
            dto.setLocoClass(logItem.getLocomotive().getLocoClass());
            dto.setShedCode(logItem.getLocomotive().getShedCode());
            dto.setSpecialLivery(logItem.getLocomotive().getSpecialLivery());
            dto.setIsPushPull(logItem.getLocomotive().getIsPushPull());
        }
        dto.setSpottedAtStation(logItem.getSpottedAtStation());
        dto.setSpottedTime(logItem.getSpottedTime());
        dto.setConfidenceWeight(logItem.getConfidenceWeight());
        dto.setStatus(logItem.getStatus());
        return dto;
    }
}