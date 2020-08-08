package com.example.demo.repository.carpool;

import com.example.demo.model.carpool.Carpool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarpoolRepository extends JpaRepository<Carpool, Long> {
}
