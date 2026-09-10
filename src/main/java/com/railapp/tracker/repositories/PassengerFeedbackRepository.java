package com.railapp.tracker.repositories;

import com.railapp.tracker.entities.PassengerCoachFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface PassengerFeedbackRepository extends JpaRepository<PassengerCoachFeedback, UUID> {
    List<PassengerCoachFeedback> findByTrainNumberOrderBySubmittedAtDesc(String trainNumber);
}