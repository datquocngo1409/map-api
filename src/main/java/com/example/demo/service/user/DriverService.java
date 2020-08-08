package com.example.demo.service.user;

import com.example.demo.model.user.Driver;
import com.example.demo.model.user.User;
import com.example.demo.repository.user.DriverRepository;
import com.example.demo.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    public Driver findById(Long id) {
        return driverRepository.findById(id).get();
    }

    public void updateDriver(Driver driver) {
        driverRepository.save(driver);
    }

    public void deleteDriver(Long id) {
        User user = driverRepository.findById(id).get().getUser();
        driverRepository.deleteById(id);
        user.setDriver(false);
    }

    public Driver findByUser(User user) { return driverRepository.findByUser(user); }
}
