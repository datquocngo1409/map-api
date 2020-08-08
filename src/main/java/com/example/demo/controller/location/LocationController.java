package com.example.demo.controller.location;

import com.example.demo.model.location.Location;
import com.example.demo.service.location.LocationService;
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
public class LocationController {
    @Autowired
    public LocationService locationService;

    //API trả về List Location.
    @RequestMapping(value = "/location", method = RequestMethod.GET)
    public ResponseEntity<List<Location>> listAllLocations() {
        List<Location> accounts = locationService.findAll();
        if (accounts.isEmpty()) {
            return new ResponseEntity<List<Location>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Location>>(accounts, HttpStatus.OK);
    }

    //API trả về Location có ID trên url.
    @RequestMapping(value = "/location/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Location> getLocationById(@PathVariable("id") Long id) {
        System.out.println("Fetching Location with id " + id);
        Location account = locationService.findById(id);
        if (account == null) {
            System.out.println("Location with id " + id + " not found");
            return new ResponseEntity<Location>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Location>(account, HttpStatus.OK);
    }

    //API tạo một Admin mới.
    @RequestMapping(value = "/location", method = RequestMethod.POST)
    public ResponseEntity<Void> createLocation(@RequestBody Location location, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Location " + location.getId());
        locationService.updateLocation(location);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/location/{id}").buildAndExpand(location.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //API cập nhật một Admin với ID trên url.
    @RequestMapping(value = "/location/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Location> updateAdmin(@PathVariable("id") Long id, @RequestBody Location location) {
        System.out.println("Updating Location " + id);

        Location current = locationService.findById(id);

        if (current == null) {
            System.out.println("Location with id " + id + " not found");
            return new ResponseEntity<Location>(HttpStatus.NOT_FOUND);
        }

        current = location;

        locationService.updateLocation(current);
        return new ResponseEntity<Location>(current, HttpStatus.OK);
    }

    //API xóa một Admin với ID trên url.
    @RequestMapping(value = "/location/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Location> deleteLocation(@PathVariable("id") Long id) {
        System.out.println("Fetching & Deleting Location with id " + id);

        Location location = locationService.findById(id);
        if (location == null) {
            System.out.println("Unable to delete. Location with id " + id + " not found");
            return new ResponseEntity<Location>(HttpStatus.NOT_FOUND);
        }

        locationService.deleteLocation(id);
        return new ResponseEntity<Location>(HttpStatus.NO_CONTENT);
    }
}
