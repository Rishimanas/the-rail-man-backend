package com.railapp.tracker.dto;

import java.util.List;

public class TrainTrackingAnalyticsDto {
    private String trainNumber;
    private String trainName;
    private String sourceStation;
    private String destStation;
    private String currentStation;
    private String nextStation;
    private Integer currentDelayMinutes;
    private Double currentSpeedKmh;
    private Double punctualityPercentage30Days;
    private Double avgDelayMinutes30Days;
    private String runningStatus; // ON_TIME, DELAYED, EARLY
    private List<DailyDelayPoint> last30DaysHistory;

    public static class DailyDelayPoint {
        private String runDate;
        private Integer delayMinutes;
        private Boolean isOnTime;

        public DailyDelayPoint(String runDate, Integer delayMinutes, Boolean isOnTime) {
            this.runDate = runDate;
            this.delayMinutes = delayMinutes;
            this.isOnTime = isOnTime;
        }

        public String getRunDate() { return runDate; }
        public Integer getDelayMinutes() { return delayMinutes; }
        public Boolean getIsOnTime() { return isOnTime; }
    }

    public TrainTrackingAnalyticsDto() {}

    public String getTrainNumber() { return trainNumber; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }
    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }
    public String getSourceStation() { return sourceStation; }
    public void setSourceStation(String sourceStation) { this.sourceStation = sourceStation; }
    public String getDestStation() { return destStation; }
    public void setDestStation(String destStation) { this.destStation = destStation; }
    public String getCurrentStation() { return currentStation; }
    public void setCurrentStation(String currentStation) { this.currentStation = currentStation; }
    public String getNextStation() { return nextStation; }
    public void setNextStation(String nextStation) { this.nextStation = nextStation; }
    public Integer getCurrentDelayMinutes() { return currentDelayMinutes; }
    public void setCurrentDelayMinutes(Integer currentDelayMinutes) { this.currentDelayMinutes = currentDelayMinutes; }
    public Double getCurrentSpeedKmh() { return currentSpeedKmh; }
    public void setCurrentSpeedKmh(Double currentSpeedKmh) { this.currentSpeedKmh = currentSpeedKmh; }
    public Double getPunctualityPercentage30Days() { return punctualityPercentage30Days; }
    public void setPunctualityPercentage30Days(Double punctualityPercentage30Days) { this.punctualityPercentage30Days = punctualityPercentage30Days; }
    public Double getAvgDelayMinutes30Days() { return avgDelayMinutes30Days; }
    public void setAvgDelayMinutes30Days(Double avgDelayMinutes30Days) { this.avgDelayMinutes30Days = avgDelayMinutes30Days; }
    public String getRunningStatus() { return runningStatus; }
    public void setRunningStatus(String runningStatus) { this.runningStatus = runningStatus; }
    public List<DailyDelayPoint> getLast30DaysHistory() { return last30DaysHistory; }
    public void setLast30DaysHistory(List<DailyDelayPoint> last30DaysHistory) { this.last30DaysHistory = last30DaysHistory; }
}