package com.example.demo.model.carpool;

import com.example.demo.model.user.Driver;
import com.example.demo.model.user.Passenger;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_Carpool")
public class Carpool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Driver driver;

    @ManyToMany
    private List<Passenger> passengerList;

    private int numberUser;

    private int capacity;

    public Carpool() {
    }

    public Carpool(Driver driver, List<Passenger> passengerList) {
        this.driver = driver;
        this.passengerList = passengerList;
        this.numberUser = passengerList.size() + 1;
        this.capacity = driver.getUsingCar().getCapacity();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public List<Passenger> getPassengerList() {
        return passengerList;
    }

    public void setPassengerList(List<Passenger> passengerList) {
        this.passengerList = passengerList;
    }

    public int getNumberUser() {
        return numberUser;
    }

    public void setNumberUser(int numberUser) {
        this.numberUser = numberUser;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
