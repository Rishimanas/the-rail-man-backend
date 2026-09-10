package com.railapp.tracker.entities;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "train_location_pings")
public class TrainLocationPing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID pingId;

    @Column(name = "train_number", nullable = false, length = 10)
    private String trainNumber;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "speed_kmh")
    private Double speedKmh;

    @Column(name = "nearest_station", length = 10)
    private String nearestStation;

    @Column(name = "delay_minutes")
    private Integer delayMinutes = 0;

    @Column(name = "ping_time")
    private OffsetDateTime pingTime = OffsetDateTime.now();

    public TrainLocationPing() {}

    public UUID getPingId() { return pingId; }
    public void setPingId(UUID pingId) { this.pingId = pingId; }
    public String getTrainNumber() { return trainNumber; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public Double getSpeedKmh() { return speedKmh; }
    public void setSpeedKmh(Double speedKmh) { this.speedKmh = speedKmh; }
    public String getNearestStation() { return nearestStation; }
    public void setNearestStation(String nearestStation) { this.nearestStation = nearestStation; }
    public Integer getDelayMinutes() { return delayMinutes; }
    public void setDelayMinutes(Integer delayMinutes) { this.delayMinutes = delayMinutes; }
    public OffsetDateTime getPingTime() { return pingTime; }
    public void setPingTime(OffsetDateTime pingTime) { this.pingTime = pingTime; }
}