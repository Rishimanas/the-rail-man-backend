package com.railapp.tracker.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "trains")
public class Train {
    @Id
    @Column(name = "train_number", length = 20)
    private String trainNumber;

    @Column(name = "train_name", nullable = false, length = 100)
    private String trainName;

    @Column(name = "source_stn", nullable = false, length = 50)
    private String sourceStn;

    @Column(name = "dest_stn", nullable = false, length = 50)
    private String destStn;

    public Train() {}

    public Train(String trainNumber, String trainName, String sourceStn, String destStn) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.sourceStn = sourceStn;
        this.destStn = destStn;
    }

    public String getTrainNumber() { return trainNumber; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }
    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }
    public String getSourceStn() { return sourceStn; }
    public void setSourceStn(String sourceStn) { this.sourceStn = sourceStn; }
    public String getDestStn() { return destStn; }
    public void setDestStn(String destStn) { this.destStn = destStn; }
}