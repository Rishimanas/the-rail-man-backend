package com.railapp.tracker.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "passenger_coach_feedbacks")
public class PassengerCoachFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID feedbackId;

    @Column(name = "train_number", nullable = false, length = 10)
    private String trainNumber;

    @Column(name = "travel_date", nullable = false)
    private LocalDate travelDate;

    // AC 1 Tier (1A), AC 2 Tier (2A), AC 3 Tier (3A), Sleeper (SL), First AC cum 2AC (HA)
    @Column(name = "coach_class", nullable = false, length = 10)
    private String coachClass;

    @Column(name = "cleanliness_rating")
    private Integer cleanlinessRating; // 1 to 5

    @Column(name = "punctuality_rating")
    private Integer punctualityRating; // 1 to 5

    @Column(name = "ac_water_rating")
    private Integer acWaterRating; // 1 to 5

    @Column(name = "comments", length = 500)
    private String comments;

    @Column(name = "submitted_at")
    private OffsetDateTime submittedAt = OffsetDateTime.now();

    public PassengerCoachFeedback() {}

    public UUID getFeedbackId() { return feedbackId; }
    public void setFeedbackId(UUID feedbackId) { this.feedbackId = feedbackId; }
    public String getTrainNumber() { return trainNumber; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }
    public LocalDate getTravelDate() { return travelDate; }
    public void setTravelDate(LocalDate travelDate) { this.travelDate = travelDate; }
    public String getCoachClass() { return coachClass; }
    public void setCoachClass(String coachClass) { this.coachClass = coachClass; }
    public Integer getCleanlinessRating() { return cleanlinessRating; }
    public void setCleanlinessRating(Integer cleanlinessRating) { this.cleanlinessRating = cleanlinessRating; }
    public Integer getPunctualityRating() { return punctualityRating; }
    public void setPunctualityRating(Integer punctualityRating) { this.punctualityRating = punctualityRating; }
    public Integer getAcWaterRating() { return acWaterRating; }
    public void setAcWaterRating(Integer acWaterRating) { this.acWaterRating = acWaterRating; }
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
    public OffsetDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(OffsetDateTime submittedAt) { this.submittedAt = submittedAt; }
}