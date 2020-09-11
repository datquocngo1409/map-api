package com.example.demo.controller.user;

import com.example.demo.model.user.Driver;
import com.example.demo.model.user.User;
import com.example.demo.service.user.DriverService;
import com.example.demo.service.user.UserService;
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
public class DriverController {
    @Autowired
    public DriverService driverService;
    @Autowired
    private UserService userService;

    //API trả về List Driver.
    @RequestMapping(value = "/driver", method = RequestMethod.GET)
    public ResponseEntity<List<Driver>> listAllDrivers() {
        List<Driver> accounts = driverService.findAll();
        if (accounts.isEmpty()) {
            return new ResponseEntity<List<Driver>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        for (Driver d : accounts) {
            d.setUser(null);
        }
        return new ResponseEntity<List<Driver>>(accounts, HttpStatus.OK);
    }

    //API trả về Driver có ID trên url.
    @RequestMapping(value = "/driver/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Driver> getDriverById(@PathVariable("id") Long id) {
        System.out.println("Fetching Driver with id " + id);
        Driver account = driverService.findById(id);
        if (account == null) {
            System.out.println("Driver with id " + id + " not found");
            return new ResponseEntity<Driver>(HttpStatus.NOT_FOUND);
        }
        account.setUser(null);
        return new ResponseEntity<Driver>(account, HttpStatus.OK);
    }

    //API tạo một Admin mới.
    @RequestMapping(value = "/driver/{userId}", method = RequestMethod.POST)
    public ResponseEntity<Void> createDriver(@RequestBody Driver driver, UriComponentsBuilder ucBuilder, @PathVariable("userId") Long userId) {
        User user = userService.findById(userId);
        driver.setUser(user);
        System.out.println("Creating Driver " + driver.getUser().getName());
        driverService.updateDriver(driver);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/driver/{id}").buildAndExpand(driver.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //API cập nhật một Admin với ID trên url.
    @RequestMapping(value = "/driver/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Driver> updateAdmin(@PathVariable("id") Long id, @RequestBody Driver driver) {
        System.out.println("Updating Driver " + id);

        Driver current = driverService.findById(id);

        if (current == null) {
            System.out.println("Driver with id " + id + " not found");
            return new ResponseEntity<Driver>(HttpStatus.NOT_FOUND);
        }

        current = driver;

        driverService.updateDriver(current);
        current.setUser(null);
        return new ResponseEntity<Driver>(current, HttpStatus.OK);
    }

    //API xóa một Admin với ID trên url.
    @RequestMapping(value = "/driver/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Driver> deleteDriver(@PathVariable("id") Long id) {
        System.out.println("Fetching & Deleting Driver with id " + id);

        Driver driver = driverService.findById(id);
        if (driver == null) {
            System.out.println("Unable to delete. Driver with id " + id + " not found");
            return new ResponseEntity<Driver>(HttpStatus.NOT_FOUND);
        }

        driverService.deleteDriver(id);
        return new ResponseEntity<Driver>(HttpStatus.NO_CONTENT);
    }
}
