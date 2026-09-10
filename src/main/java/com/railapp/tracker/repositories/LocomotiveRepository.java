package com.railapp.tracker.repositories;

import com.railapp.tracker.entities.Locomotive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocomotiveRepository extends JpaRepository<Locomotive, Integer> {
}
