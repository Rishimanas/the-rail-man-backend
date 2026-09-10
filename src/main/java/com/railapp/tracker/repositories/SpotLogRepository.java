package com.railapp.tracker.repositories;

import com.railapp.tracker.entities.LocoSpottingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface SpotLogRepository extends JpaRepository<LocoSpottingLog, UUID> {

    @Query("SELECT s FROM LocoSpottingLog s " +
           "WHERE s.train.trainNumber = :trainNumber " +
           "AND s.runDate = :runDate " +
           "AND s.status IN ('VERIFIED', 'PENDING') " +
           "ORDER BY s.confidenceWeight DESC, s.spottedTime DESC")
    List<LocoSpottingLog> findVerifiedSpots(
        @Param("trainNumber") String trainNumber,
        @Param("runDate") LocalDate runDate
    );
}