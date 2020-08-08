package com.example.demo.model.user;

import com.example.demo.model.car.Car;
import com.example.demo.service.user.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "tbl_Driver")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private User user;

    @NotNull
    private String cardID;

    @OneToMany
    private List<Car> carList;

    @OneToOne
    private Car usingCar;

    public Driver() {
    }

    public Driver(User user, @NotNull String cardID, List<Car> carList, Car usingCar) {
        this.user = user;
        this.cardID = cardID;
        this.carList = carList;
        this.usingCar = usingCar;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

    public Car getUsingCar() {
        return usingCar;
    }

    public void setUsingCar(Car usingCar) {
        this.usingCar = usingCar;
    }
}
