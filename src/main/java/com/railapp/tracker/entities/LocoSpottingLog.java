package com.railapp.tracker.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "loco_spotting_logs")
public class LocoSpottingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "spot_id")
    private UUID spotId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_number")
    private Train train;

    @Column(name = "run_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate runDate;

    @Column(name = "from_station_code", nullable = false, length = 10)
    private String fromStationCode;

    @Column(name = "to_station_code", nullable = false, length = 10)
    private String toStationCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loco_number")
    private Locomotive locomotive;

    @Column(name = "spotted_at_station", nullable = false, length = 10)
    private String spottedAtStation;

    @Column(name = "spotted_time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime spottedTime;

    @Column(name = "submitted_by")
    private UUID submittedBy;

    @Column(name = "proof_image_url")
    private String proofImageUrl;

    @Column(name = "confidence_weight")
    private Integer confidenceWeight = 0;

    @Column(name = "status", length = 20)
    private String status = "PENDING";

    @Column(name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();

    public LocoSpottingLog() {}

    public UUID getSpotId() { return spotId; }
    public void setSpotId(UUID spotId) { this.spotId = spotId; }
    public Train getTrain() { return train; }
    public void setTrain(Train train) { this.train = train; }
    public LocalDate getRunDate() { return runDate; }
    public void setRunDate(LocalDate runDate) { this.runDate = runDate; }
    public String getFromStationCode() { return fromStationCode; }
    public void setFromStationCode(String fromStationCode) { this.fromStationCode = fromStationCode; }
    public String getToStationCode() { return toStationCode; }
    public void setToStationCode(String toStationCode) { this.toStationCode = toStationCode; }
    public Locomotive getLocomotive() { return locomotive; }
    public void setLocomotive(Locomotive locomotive) { this.locomotive = locomotive; }
    public String getSpottedAtStation() { return spottedAtStation; }
    public void setSpottedAtStation(String spottedAtStation) { this.spottedAtStation = spottedAtStation; }
    public OffsetDateTime getSpottedTime() { return spottedTime; }
    public void setSpottedTime(OffsetDateTime spottedTime) { this.spottedTime = spottedTime; }
    public UUID getSubmittedBy() { return submittedBy; }
    public void setSubmittedBy(UUID submittedBy) { this.submittedBy = submittedBy; }
    public String getProofImageUrl() { return proofImageUrl; }
    public void setProofImageUrl(String proofImageUrl) { this.proofImageUrl = proofImageUrl; }
    public Integer getConfidenceWeight() { return confidenceWeight; }
    public void setConfidenceWeight(Integer confidenceWeight) { this.confidenceWeight = confidenceWeight; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}