package com.railapp.tracker.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "locomotives")
public class Locomotive {

    @Id
    @Column(name = "loco_number")
    private Integer locoNumber;

    @Column(name = "loco_class", nullable = false, length = 30)
    private String locoClass;

    @Column(name = "shed_code", nullable = false, length = 15)
    private String shedCode;

    @Column(name = "traction_type", nullable = false, length = 20)
    private String tractionType;

    @Column(name = "special_livery", length = 100)
    private String specialLivery;

    @Column(name = "is_push_pull")
    private Boolean isPushPull = false;

    @Column(name = "is_converted")
    private Boolean isConverted = false;

    @Column(name = "status", length = 20)
    private String status = "IN_SERVICE";

    public Locomotive() {}

    public Integer getLocoNumber() { return locoNumber; }
    public void setLocoNumber(Integer locoNumber) { this.locoNumber = locoNumber; }
    public String getLocoClass() { return locoClass; }
    public void setLocoClass(String locoClass) { this.locoClass = locoClass; }
    public String getShedCode() { return shedCode; }
    public void setShedCode(String shedCode) { this.shedCode = shedCode; }
    public String getTractionType() { return tractionType; }
    public void setTractionType(String tractionType) { this.tractionType = tractionType; }
    public String getSpecialLivery() { return specialLivery; }
    public void setSpecialLivery(String specialLivery) { this.specialLivery = specialLivery; }
    public Boolean getIsPushPull() { return isPushPull; }
    public void setIsPushPull(Boolean isPushPull) { this.isPushPull = isPushPull; }
    public Boolean getIsConverted() { return isConverted; }
    public void setIsConverted(Boolean isConverted) { this.isConverted = isConverted; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}