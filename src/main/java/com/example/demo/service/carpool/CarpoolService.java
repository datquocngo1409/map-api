package com.example.demo.service.carpool;

import com.example.demo.model.carpool.Carpool;
import com.example.demo.model.user.Driver;
import com.example.demo.model.user.Passenger;
import com.example.demo.repository.carpool.CarpoolRepository;
import com.example.demo.repository.user.DriverRepository;
import com.example.demo.repository.user.PassengerRepository;
import com.example.demo.service.user.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarpoolService {
    @Autowired
    private CarpoolRepository carpoolRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private PassengerService passengerService;

    public List<Carpool> findAll() {
        return carpoolRepository.findAll();
    }

    public Carpool findById(Long id) {
        return carpoolRepository.findById(id).get();
    }

    public void updateCarpool(Carpool carpool) {
        carpoolRepository.save(carpool);
    }

    public void deleteCarpool(Long id) {
        carpoolRepository.deleteById(id);
    }

    public boolean addPassenger(Long id, Passenger passenger) {
        Carpool carpool = carpoolRepository.findById(id).get();
        if (carpool.getNumberUser() >= carpool.getCapacity()) {
            return false;
        }
        carpool.getPassengerList().add(passenger);
        carpool.setNumberUser(carpool.getNumberUser() + 1);
        carpoolRepository.save(carpool);
        return true;
    }

    public boolean removePassenger(Long id, Passenger passenger) {
        Carpool carpool = carpoolRepository.findById(id).get();
        if (!carpool.getPassengerList().contains(passenger)) {
            return false;
        }
        if (carpool.getNumberUser() < 2) {
            return false;
        }
        carpool.getPassengerList().remove(passenger);
        carpool.setNumberUser(carpool.getNumberUser() - 1);
        carpoolRepository.save(carpool);
        return true;
    }

    public boolean canChangeDriver(Long id) {
        Carpool carpool = carpoolRepository.findById(id).get();
        if (carpool.getNumberUser() < 2) {
            return false;
        }
        return passengerService.isDriver(carpool.getPassengerList().get(0).getId());
    }

    public boolean changeDriver(Long id) {
        Carpool carpool = carpoolRepository.findById(id).get();
        Passenger passenger = carpool.getPassengerList().get(0);
        if (carpool.getNumberUser() < 2) {
            return false;
        }
        if (!carpool.getPassengerList().contains(passenger)) {
            return false;
        }
        Driver newDriver = driverRepository.findByUser(passenger.getUser());
        if (newDriver == null) {
            return false;
        }
        carpool.setDriver(newDriver);
        carpool.setNumberUser(carpool.getNumberUser() - 1);
        return true;
    }
}
