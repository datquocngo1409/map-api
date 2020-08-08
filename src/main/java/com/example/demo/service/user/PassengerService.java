package com.example.demo.service.user;

import com.example.demo.model.user.Driver;
import com.example.demo.model.user.Passenger;
import com.example.demo.model.user.User;
import com.example.demo.repository.user.DriverRepository;
import com.example.demo.repository.user.PassengerRepository;
import com.example.demo.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DriverRepository driverRepository;

    public List<Passenger> findAll() {
        return passengerRepository.findAll();
    }

    public Passenger findById(Long id) {
        return passengerRepository.findById(id).get();
    }

    public void updatePassenger(Passenger passenger) {
        passengerRepository.save(passenger);
    }

    public void deletePassenger(Long id) {
        User user = passengerRepository.findById(id).get().getUser();
        passengerRepository.deleteById(id);
        userRepository.delete(user);
    }

    public Passenger findByUser(User user) { return passengerRepository.findByUser(user); }

    public boolean isDriver(Long id) {
        Driver driver = driverRepository.findByUser(passengerRepository.findById(id).get().getUser());
        return driver != null;
    }
}
