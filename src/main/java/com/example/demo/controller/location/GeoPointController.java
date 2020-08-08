package com.example.demo.controller.location;

import com.example.demo.model.location.GeoPoint;
import com.example.demo.service.location.GeoPointService;
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
public class GeoPointController {
    @Autowired
    public GeoPointService geoPointService;

    //API trả về List GeoPoint.
    @RequestMapping(value = "/geopoint", method = RequestMethod.GET)
    public ResponseEntity<List<GeoPoint>> listAllGeoPoints() {
        List<GeoPoint> accounts = geoPointService.findAll();
        if (accounts.isEmpty()) {
            return new ResponseEntity<List<GeoPoint>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<GeoPoint>>(accounts, HttpStatus.OK);
    }

    //API trả về GeoPoint có ID trên url.
    @RequestMapping(value = "/geopoint/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeoPoint> getGeoPointById(@PathVariable("id") Long id) {
        System.out.println("Fetching GeoPoint with id " + id);
        GeoPoint account = geoPointService.findById(id);
        if (account == null) {
            System.out.println("GeoPoint with id " + id + " not found");
            return new ResponseEntity<GeoPoint>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<GeoPoint>(account, HttpStatus.OK);
    }

    //API tạo một Admin mới.
    @RequestMapping(value = "/geopoint", method = RequestMethod.POST)
    public ResponseEntity<Void> createGeoPoint(@RequestBody GeoPoint geopoint, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating GeoPoint " + geopoint.getName());
        geoPointService.updateGeoPoint(geopoint);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/geopoint/{id}").buildAndExpand(geopoint.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //API cập nhật một Admin với ID trên url.
    @RequestMapping(value = "/geopoint/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<GeoPoint> updateAdmin(@PathVariable("id") Long id, @RequestBody GeoPoint geopoint) {
        System.out.println("Updating GeoPoint " + id);

        GeoPoint current = geoPointService.findById(id);

        if (current == null) {
            System.out.println("GeoPoint with id " + id + " not found");
            return new ResponseEntity<GeoPoint>(HttpStatus.NOT_FOUND);
        }

        current = geopoint;

        geoPointService.updateGeoPoint(current);
        return new ResponseEntity<GeoPoint>(current, HttpStatus.OK);
    }

    //API xóa một Admin với ID trên url.
    @RequestMapping(value = "/geopoint/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<GeoPoint> deleteGeoPoint(@PathVariable("id") Long id) {
        System.out.println("Fetching & Deleting GeoPoint with id " + id);

        GeoPoint geopoint = geoPointService.findById(id);
        if (geopoint == null) {
            System.out.println("Unable to delete. GeoPoint with id " + id + " not found");
            return new ResponseEntity<GeoPoint>(HttpStatus.NOT_FOUND);
        }

        geoPointService.deleteGeoPoint(id);
        return new ResponseEntity<GeoPoint>(HttpStatus.NO_CONTENT);
    }
}
