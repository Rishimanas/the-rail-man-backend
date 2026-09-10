package com.railapp.tracker.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "locomotives")
public class Locomotive {
    @Id
    @Column(name = "loco_number")
    private Integer locoNumber;

    @Column(name = "loco_class", nullable = false, length = 20)
    private String locoClass;

    @Column(name = "shed_code", nullable = false, length = 10)
    private String shedCode;

    @Column(name = "traction_type", length = 10)
    private String tractionType;

    @Column(name = "status", length = 20)
    private String status;

    public Locomotive() {}

    public Locomotive(Integer locoNumber, String locoClass, String shedCode, String tractionType, String status) {
        this.locoNumber = locoNumber;
        this.locoClass = locoClass;
        this.shedCode = shedCode;
        this.tractionType = tractionType;
        this.status = status;
    }

    public Integer getLocoNumber() { return locoNumber; }
    public void setLocoNumber(Integer locoNumber) { this.locoNumber = locoNumber; }
    public String getLocoClass() { return locoClass; }
    public void setLocoClass(String locoClass) { this.locoClass = locoClass; }
    public String getShedCode() { return shedCode; }
    public void setShedCode(String shedCode) { this.shedCode = shedCode; }
    public String getTractionType() { return tractionType; }
    public void setTractionType(String tractionType) { this.tractionType = tractionType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
