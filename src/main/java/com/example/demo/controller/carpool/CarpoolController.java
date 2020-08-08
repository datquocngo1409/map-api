package com.example.demo.controller.carpool;

import com.example.demo.model.carpool.Carpool;
import com.example.demo.model.user.Passenger;
import com.example.demo.service.carpool.CarpoolService;
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
public class CarpoolController {
    @Autowired
    public CarpoolService carpoolService;

    @RequestMapping(value = "/carpool", method = RequestMethod.GET)
    public ResponseEntity<List<Carpool>> listAllCarpools() {
        List<Carpool> accounts = carpoolService.findAll();
        if (accounts.isEmpty()) {
            return new ResponseEntity<List<Carpool>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Carpool>>(accounts, HttpStatus.OK);
    }

    @RequestMapping(value = "/carpool/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Carpool> getCarpoolById(@PathVariable("id") Long id) {
        System.out.println("Fetching Carpool with id " + id);
        Carpool account = carpoolService.findById(id);
        if (account == null) {
            System.out.println("Carpool with id " + id + " not found");
            return new ResponseEntity<Carpool>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Carpool>(account, HttpStatus.OK);
    }

    @RequestMapping(value = "/carpool", method = RequestMethod.POST)
    public ResponseEntity<Void> createCarpool(@RequestBody Carpool carpool, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Carpool " + carpool.getDriver().getUser().getName());
        carpoolService.updateCarpool(carpool);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/carpool/{id}").buildAndExpand(carpool.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/carpool/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Carpool> updateAdmin(@PathVariable("id") Long id, @RequestBody Carpool carpool) {
        System.out.println("Updating Carpool " + id);

        Carpool current = carpoolService.findById(id);

        if (current == null) {
            System.out.println("Carpool with id " + id + " not found");
            return new ResponseEntity<Carpool>(HttpStatus.NOT_FOUND);
        }

        current = carpool;

        carpoolService.updateCarpool(current);
        return new ResponseEntity<Carpool>(current, HttpStatus.OK);
    }

    @RequestMapping(value = "/carpool/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Carpool> deleteCarpool(@PathVariable("id") Long id) {
        System.out.println("Fetching & Deleting Carpool with id " + id);

        Carpool carpool = carpoolService.findById(id);
        if (carpool == null) {
            System.out.println("Unable to delete. Carpool with id " + id + " not found");
            return new ResponseEntity<Carpool>(HttpStatus.NOT_FOUND);
        }

        carpoolService.deleteCarpool(id);
        return new ResponseEntity<Carpool>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/carpool/addPassenger/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Carpool> addPassenger(@PathVariable("id") Long id, @RequestBody Passenger passenger) {
        System.out.println("Add Passenger " + passenger.getUser().getName() +" to Carpool with id " + id);

        boolean added = carpoolService.addPassenger(id, passenger);
        if (added) {
            return new ResponseEntity<Carpool>(HttpStatus.OK);
        }
        return new ResponseEntity<Carpool>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/carpool/removePassenger/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Carpool> removePassenger(@PathVariable("id") Long id, @RequestBody Passenger passenger) {
        System.out.println("Remove Passenger " + passenger.getUser().getName() +" to Carpool with id " + id);

        boolean removed = carpoolService.removePassenger(id, passenger);
        if (removed) {
            return new ResponseEntity<Carpool>(HttpStatus.OK);
        }
        return new ResponseEntity<Carpool>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/carpool/changeDriver/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Carpool> changeDriver(@PathVariable("id") Long id) {
        System.out.println("Change Driver, Carpool with id " + id);

        boolean canChange = carpoolService.canChangeDriver(id);
        boolean changed = carpoolService.changeDriver(id);
        if (changed) {
            return new ResponseEntity<Carpool>(HttpStatus.OK);
        }
        return new ResponseEntity<Carpool>(HttpStatus.BAD_REQUEST);
    }
}
