package com.railtracker.repository;

import com.railtracker.model.SpottingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpottingLogRepository extends JpaRepository<SpottingLog, Long> {

    // SIRF LATEST RUN: ORDER BY run_date DESC, spotted_time DESC LIMIT 1
    @Query(value = "SELECT s.* FROM spotting_logs s " +
                   "WHERE s.train_number = :trainNumber " +
                   "ORDER BY s.run_date DESC, s.spotted_time DESC LIMIT 1", 
           nativeQuery = true)
    Optional<SpottingLog> findLatestActiveSpotByTrain(@Param("trainNumber") String trainNumber);

    // Live Recent Spottings Feed
    @Query(value = "SELECT s.* FROM spotting_logs s ORDER BY s.spotted_time DESC LIMIT 25", 
           nativeQuery = true)
    List<SpottingLog> findRecentCommunityFeed();
}
