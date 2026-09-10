package com.railtracker.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "spotting_logs")
public class SpottingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "train_number", nullable = false)
    private String trainNumber;

    @Column(name = "run_date", nullable = false)
    private LocalDate runDate;

    @Column(name = "from_station")
    private String fromStation;

    @Column(name = "to_station")
    private String toStation;

    @Column(name = "loco_number", nullable = false)
    private Integer locoNumber;

    @Column(name = "spotted_at_station")
    private String spottedAtStation;

    @Column(name = "spotted_time")
    private OffsetDateTime spottedTime;

    @Column(name = "submitted_by")
    private String submittedBy;

    @Column(name = "proof_image_url")
    private String proofImageUrl;

    @Column(name = "status")
    private String status = "VERIFIED";

    public SpottingLog() {}

    public Long getId() { return id; }
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
    public String getSubmittedBy() { return submittedBy; }
    public void setSubmittedBy(String submittedBy) { this.submittedBy = submittedBy; }
    public String getProofImageUrl() { return proofImageUrl; }
    public void setProofImageUrl(String proofImageUrl) { this.proofImageUrl = proofImageUrl; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
