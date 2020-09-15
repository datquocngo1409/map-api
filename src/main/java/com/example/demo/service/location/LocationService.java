package com.example.demo.service.location;

import com.example.demo.model.location.GeoPoint;
import com.example.demo.model.location.Location;
import com.example.demo.model.user.Driver;
import com.example.demo.model.user.Passenger;
import com.example.demo.repository.location.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public Location findById(Long id) {
        return locationRepository.findById(id).get();
    }

    public void updateLocation(Location location) {
        locationRepository.save(location);
    }

    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }

    public double distance(Location l1, Location l2) {
        double lat1 = l1.getLatitude();
        double lon1 = l1.getLongitude();
        double lat2 = l2.getLatitude();
        double lon2 = l2.getLongitude();
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }

    public List<GeoPoint> sort(Driver driver, List<Passenger> passengers) {
        List<GeoPoint> result = new ArrayList<>();
        result.add(driver.getUser().getHomeAddress());
        // code
        for (Passenger p : passengers) {
            result.add(p.getUser().getHomeAddress());
        }
        for (int iP = 0; iP < passengers.size() - 1; iP++) {
            int index = result.indexOf(passengers.get(iP).getUser().getHomeAddress());
            boolean added = false;
            for (int iR = index; iR < result.size(); iR++) {
                double distanceFromIndexToOffice = distance(
                        result.get(index).getLocation(),
                        passengers.get(iP).getUser().getOfficeAddress().getLocation());
                double distanceFromIndexToNext = distance(
                        result.get(index).getLocation(),
                        result.get(index + 1).getLocation()
                );
                if (distanceFromIndexToOffice > distanceFromIndexToNext) {
                    continue;
                } else {
                    result.add(index + 1, passengers.get(iP).getUser().getOfficeAddress());
                    added = true;
                    break;
                }
            }
            if (!added) {
                result.add(passengers.get(iP).getUser().getOfficeAddress());
            }
        }
        result.add(passengers.get(passengers.size() - 1).getUser().getOfficeAddress());
        // code
        result.add(driver.getUser().getOfficeAddress());
        return result;
    }

    public double getDistance(List<GeoPoint> geoPointList) {
        double sum = 0;
        for (int i = 0; i < geoPointList.size() - 1; i++) {
            double distance = distance(geoPointList.get(i).getLocation(), geoPointList.get(i+1).getLocation());
            sum += distance;
        }
        return sum;
    }
}
