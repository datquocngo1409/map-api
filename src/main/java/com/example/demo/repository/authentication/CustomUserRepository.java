package com.example.demo.repository.authentication;

import com.example.demo.model.authentication.CustomUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomUserRepository extends CrudRepository<CustomUser, Long> {
    CustomUser findByUsername(String username);
}
