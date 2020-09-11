package com.example.demo.model.location;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_Location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String type = "GeoPoint";

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    private boolean isHome;

    @NotNull
    public Location() {
    }

    public Location(String type, @NotNull double latitude, @NotNull double longitude, @NotNull boolean isHome) {
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isHome = isHome;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longtitude) {
        this.longitude = longtitude;
    }

    public boolean isHome() {
        return isHome;
    }

    public void setHome(boolean home) {
        isHome = home;
    }
}
