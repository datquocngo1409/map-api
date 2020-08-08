package com.example.demo.repository.user;

import com.example.demo.model.user.Driver;
import com.example.demo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    Driver findByUser(User user);
}
