package com.example.demo.service.user;

import com.example.demo.model.user.Passenger;
import com.example.demo.model.user.User;
import com.example.demo.repository.user.PassengerRepository;
import com.example.demo.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PassengerRepository passengerRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    public void createUser(User user) {
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
