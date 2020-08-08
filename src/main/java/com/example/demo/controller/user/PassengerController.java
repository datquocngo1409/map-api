package com.example.demo.controller.user;

import com.example.demo.model.user.Passenger;
import com.example.demo.service.user.PassengerService;
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
public class PassengerController {
    @Autowired
    public PassengerService passengerService;

    //API trả về List Passenger.
    @RequestMapping(value = "/passenger", method = RequestMethod.GET)
    public ResponseEntity<List<Passenger>> listAllPassengers() {
        List<Passenger> accounts = passengerService.findAll();
        if (accounts.isEmpty()) {
            return new ResponseEntity<List<Passenger>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Passenger>>(accounts, HttpStatus.OK);
    }

    //API trả về Passenger có ID trên url.
    @RequestMapping(value = "/passenger/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Passenger> getPassengerById(@PathVariable("id") Long id) {
        System.out.println("Fetching Passenger with id " + id);
        Passenger account = passengerService.findById(id);
        if (account == null) {
            System.out.println("Passenger with id " + id + " not found");
            return new ResponseEntity<Passenger>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Passenger>(account, HttpStatus.OK);
    }

    //API tạo một Admin mới.
    @RequestMapping(value = "/passenger", method = RequestMethod.POST)
    public ResponseEntity<Void> createPassenger(@RequestBody Passenger passenger, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Passenger " + passenger.getUser().getName());
        passengerService.updatePassenger(passenger);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/passenger/{id}").buildAndExpand(passenger.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //API cập nhật một Admin với ID trên url.
    @RequestMapping(value = "/passenger/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Passenger> updateAdmin(@PathVariable("id") Long id, @RequestBody Passenger passenger) {
        System.out.println("Updating Passenger " + id);

        Passenger current = passengerService.findById(id);

        if (current == null) {
            System.out.println("Passenger with id " + id + " not found");
            return new ResponseEntity<Passenger>(HttpStatus.NOT_FOUND);
        }

        current = passenger;

        passengerService.updatePassenger(current);
        return new ResponseEntity<Passenger>(current, HttpStatus.OK);
    }

    //API xóa một Admin với ID trên url.
    @RequestMapping(value = "/passenger/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Passenger> deletePassenger(@PathVariable("id") Long id) {
        System.out.println("Fetching & Deleting Passenger with id " + id);

        Passenger passenger = passengerService.findById(id);
        if (passenger == null) {
            System.out.println("Unable to delete. Passenger with id " + id + " not found");
            return new ResponseEntity<Passenger>(HttpStatus.NOT_FOUND);
        }

        passengerService.deletePassenger(id);
        return new ResponseEntity<Passenger>(HttpStatus.NO_CONTENT);
    }
}
