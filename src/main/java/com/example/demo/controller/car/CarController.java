package com.example.demo.controller.car;

import com.example.demo.model.car.Car;
import com.example.demo.service.car.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
public class CarController {
    @Autowired
    public CarService carService;

    //API trả về List Car.
    @RequestMapping(value = "/car", method = RequestMethod.GET)
    public ResponseEntity<List<Car>> listAllCars() {
        List<Car> accounts = carService.findAll();
        if (accounts.isEmpty()) {
            return new ResponseEntity<List<Car>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Car>>(accounts, HttpStatus.OK);
    }

    //API trả về Car có ID trên url.
    @RequestMapping(value = "/car/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Car> getCarById(@PathVariable("id") Long id) {
        System.out.println("Fetching Car with id " + id);
        Car account = carService.findById(id);
        if (account == null) {
            System.out.println("Car with id " + id + " not found");
            return new ResponseEntity<Car>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Car>(account, HttpStatus.OK);
    }

    //API tạo một Admin mới.
    @RequestMapping(value = "/car", method = RequestMethod.POST)
    public ResponseEntity<Void> createCar(@RequestBody Car car, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Car " + car.getName());
        carService.updateCar(car);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/car/{id}").buildAndExpand(car.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //API cập nhật một Admin với ID trên url.
    @RequestMapping(value = "/car/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Car> updateAdmin(@PathVariable("id") Long id, @RequestBody Car car) {
        System.out.println("Updating Car " + id);

        Car current = carService.findById(id);

        if (current == null) {
            System.out.println("Car with id " + id + " not found");
            return new ResponseEntity<Car>(HttpStatus.NOT_FOUND);
        }

        current = car;

        carService.updateCar(current);
        return new ResponseEntity<Car>(current, HttpStatus.OK);
    }

    //API xóa một Admin với ID trên url.
    @RequestMapping(value = "/car/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Car> deleteCar(@PathVariable("id") Long id) {
        System.out.println("Fetching & Deleting Car with id " + id);

        Car car = carService.findById(id);
        if (car == null) {
            System.out.println("Unable to delete. Car with id " + id + " not found");
            return new ResponseEntity<Car>(HttpStatus.NOT_FOUND);
        }

        carService.deleteCar(id);
        return new ResponseEntity<Car>(HttpStatus.NO_CONTENT);
    }
}
