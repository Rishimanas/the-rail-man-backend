package com.railapp.tracker.services;

import com.railapp.tracker.dto.TrainTrackingAnalyticsDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TrainTrackingService {

    public TrainTrackingAnalyticsDto getTrainTrackingWith30DaysAnalytics(String trainNumber) {
        TrainTrackingAnalyticsDto dto = new TrainTrackingAnalyticsDto();
        dto.setTrainNumber(trainNumber);

        // Train meta details
        if ("18407".equals(trainNumber)) {
            dto.setTrainName("Puri - Koraput Express");
            dto.setSourceStation("PURI");
            dto.setDestStation("KRPU");
            dto.setCurrentStation("KHURDA ROAD JN (KUR)");
            dto.setNextStation("NIRAKARPUR (NKP)");
            dto.setCurrentDelayMinutes(12);
            dto.setCurrentSpeedKmh(68.5);
            dto.setRunningStatus("DELAYED");
        } else if ("12703".equals(trainNumber)) {
            dto.setTrainName("Falaknuma Express");
            dto.setSourceStation("HWH");
            dto.setDestStation("SC");
            dto.setCurrentStation("CHATRAPUR (CAP)");
            dto.setNextStation("BRAHMAPUR (BAM)");
            dto.setCurrentDelayMinutes(4);
            dto.setCurrentSpeedKmh(82.0);
            dto.setRunningStatus("ON_TIME");
        } else {
            dto.setTrainName("Superfast Express " + trainNumber);
            dto.setSourceStation("NDLS");
            dto.setDestStation("BBS");
            dto.setCurrentStation("ENROUTE");
            dto.setNextStation("UPCOMING");
            dto.setCurrentDelayMinutes(8);
            dto.setCurrentSpeedKmh(75.0);
            dto.setRunningStatus("ON_TIME");
        }

        // 30 Days Punctuality & Delay Aggregator
        List<TrainTrackingAnalyticsDto.DailyDelayPoint> history = new ArrayList<>();
        int onTimeDays = 0;
        int totalDelayMinutes = 0;
        LocalDate today = LocalDate.now();
        Random rand = new Random(trainNumber.hashCode());

        for (int i = 30; i >= 1; i--) {
            LocalDate date = today.minusDays(i);
            int delay = rand.nextInt(35); // simulated daily end-to-end delay
            boolean onTime = delay <= 15; // IR Punctuality Criterion: <= 15 mins delay
            if (onTime) onTimeDays++;
            totalDelayMinutes += delay;

            history.add(new TrainTrackingAnalyticsDto.DailyDelayPoint(date.toString(), delay, onTime));
        }

        double punctualityPct = Math.round(((double) onTimeDays / 30.0) * 1000.0) / 10.0;
        double avgDelay = Math.round(((double) totalDelayMinutes / 30.0) * 10.0) / 10.0;

        dto.setPunctualityPercentage30Days(punctualityPct);
        dto.setAvgDelayMinutes30Days(avgDelay);
        dto.setLast30DaysHistory(history);

        return dto;
    }
}