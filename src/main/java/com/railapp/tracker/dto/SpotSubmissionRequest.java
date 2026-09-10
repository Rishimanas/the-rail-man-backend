package com.railapp.tracker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class SpotSubmissionRequest {

    private String trainNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate runDate;

    private String fromStation;
    private String toStation;
    private Integer locoNumber;
    private String spottedAtStation;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime spottedTime;

    private UUID submittedBy;
    private String proofImageUrl;

    public SpotSubmissionRequest() {}

    public String getTrainNumber() { return trainNumber; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }
    public LocalDate getRunDate() { return runDate; }
    public void setRunDate(LocalDate runDate) { this.runDate = runDate; }
    public String getFromStation() { return fromStation; }
    public void setFromStation(String fromStation) { this.fromStation = fromStation; }
    public String getToStation() { return toStation; }
    public void setToStation(String toStation) { this.toStation = toStation; }
    public Integer getLocoNumber() { return locoNumber; }
    public void setLocoNumber(Integer locoNumber) { this.locoNumber = locoNumber; }
    public String getSpottedAtStation() { return spottedAtStation; }
    public void setSpottedAtStation(String spottedAtStation) { this.spottedAtStation = spottedAtStation; }
    public OffsetDateTime getSpottedTime() { return spottedTime; }
    public void setSpottedTime(OffsetDateTime spottedTime) { this.spottedTime = spottedTime; }
    public UUID getSubmittedBy() { return submittedBy; }
    public void setSubmittedBy(UUID submittedBy) { this.submittedBy = submittedBy; }
    public String getProofImageUrl() { return proofImageUrl; }
    public void setProofImageUrl(String proofImageUrl) { this.proofImageUrl = proofImageUrl; }
}