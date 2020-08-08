package com.example.demo.service.location;

import com.example.demo.model.location.GeoPoint;
import com.example.demo.model.location.Location;
import com.example.demo.repository.location.GeoPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeoPointService {
    @Autowired
    private GeoPointRepository geoPointRepository;

    public List<GeoPoint> findAll() {
        return geoPointRepository.findAll();
    }

    public GeoPoint findById(Long id) {
        return geoPointRepository.findById(id).get();
    }

    public void updateGeoPoint(GeoPoint geoPoint) {
        geoPointRepository.save(geoPoint);
    }

    public void deleteGeoPoint(Long id) {
        geoPointRepository.deleteById(id);
    }
}
