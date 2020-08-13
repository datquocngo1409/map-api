package com.example.demo.service.user;

import com.example.demo.model.user.Passenger;
import com.example.demo.model.user.User;
import com.example.demo.repository.user.PassengerRepository;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.service.location.GeoPointService;
import com.example.demo.service.location.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private PasswordEncoder bcryptEncoder;
    @Autowired
    private GeoPointService geoPointService;
    @Autowired
    private LocationService locationService;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    public void createUser(User user) {
        locationService.updateLocation(user.getHomeAddress().getLocation());
        locationService.updateLocation(user.getOfficeAddress().getLocation());
        geoPointService.updateGeoPoint(user.getHomeAddress());
        geoPointService.updateGeoPoint(user.getOfficeAddress());
        userRepository.save(user);
        passengerRepository.save(new Passenger(user));
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        Passenger passenger = passengerRepository.findByUser(userRepository.findById(id).get());
        userRepository.deleteById(id);
        passengerRepository.delete(passenger);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByToken(String token) {
        return userRepository.findByToken(token);
    }
}
