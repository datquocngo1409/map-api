package com.example.demo.repository.user;

import com.example.demo.model.user.Passenger;
import com.example.demo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    Passenger findByUser(User user);
}
