package com.example.demo.repository.location;

import com.example.demo.model.location.GeoPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeoPointRepository extends JpaRepository<GeoPoint, Long> {
}
