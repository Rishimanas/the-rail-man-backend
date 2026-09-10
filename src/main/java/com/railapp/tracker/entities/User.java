package com.railapp.tracker.entities;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "trust_score")
    private Integer trustScore = 10;

    @Column(name = "total_spots")
    private Integer totalSpots = 0;

    @Column(name = "verified_spots")
    private Integer verifiedSpots = 0;

    @Column(name = "badge_tier", length = 20)
    private String badgeTier = "NOVICE";

    public User() {}

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Integer getTrustScore() { return trustScore; }
    public void setTrustScore(Integer trustScore) { this.trustScore = trustScore; }
    public Integer getTotalSpots() { return totalSpots; }
    public void setTotalSpots(Integer totalSpots) { this.totalSpots = totalSpots; }
    public Integer getVerifiedSpots() { return verifiedSpots; }
    public void setVerifiedSpots(Integer verifiedSpots) { this.verifiedSpots = verifiedSpots; }
    public String getBadgeTier() { return badgeTier; }
    public void setBadgeTier(String badgeTier) { this.badgeTier = badgeTier; }
}
