package com.railapp.tracker.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class LocoSpotResponseDto {
    private UUID spotId;
    private String trainNumber;
    private LocalDate runDate;
    private String fromStationCode;
    private String toStationCode;
    private Integer locoNumber;
    private String locoClass;
    private String shedCode;
    private String specialLivery;
    private Boolean isPushPull;
    private String spottedAtStation;
    private OffsetDateTime spottedTime;
    private Integer confidenceWeight;
    private String status;

    public LocoSpotResponseDto() {}

    public UUID getSpotId() { return spotId; }
    public void setSpotId(UUID spotId) { this.spotId = spotId; }
    public String getTrainNumber() { return trainNumber; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }
    public LocalDate getRunDate() { return runDate; }
    public void setRunDate(LocalDate runDate) { this.runDate = runDate; }
    public String getFromStationCode() { return fromStationCode; }
    public void setFromStationCode(String fromStationCode) { this.fromStationCode = fromStationCode; }
    public String getToStationCode() { return toStationCode; }
    public void setToStationCode(String toStationCode) { this.toStationCode = toStationCode; }
    public Integer getLocoNumber() { return locoNumber; }
    public void setLocoNumber(Integer locoNumber) { this.locoNumber = locoNumber; }
    public String getLocoClass() { return locoClass; }
    public void setLocoClass(String locoClass) { this.locoClass = locoClass; }
    public String getShedCode() { return shedCode; }
    public void setShedCode(String shedCode) { this.shedCode = shedCode; }
    public String getSpecialLivery() { return specialLivery; }
    public void setSpecialLivery(String specialLivery) { this.specialLivery = specialLivery; }
    public Boolean getIsPushPull() { return isPushPull; }
    public void setIsPushPull(Boolean isPushPull) { this.isPushPull = isPushPull; }
    public String getSpottedAtStation() { return spottedAtStation; }
    public void setSpottedAtStation(String spottedAtStation) { this.spottedAtStation = spottedAtStation; }
    public OffsetDateTime getSpottedTime() { return spottedTime; }
    public void setSpottedTime(OffsetDateTime spottedTime) { this.spottedTime = spottedTime; }
    public Integer getConfidenceWeight() { return confidenceWeight; }
    public void setConfidenceWeight(Integer confidenceWeight) { this.confidenceWeight = confidenceWeight; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}