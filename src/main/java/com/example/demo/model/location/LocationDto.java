package com.example.demo.model.location;

public class LocationDto {
    Long id;
    String title;
    Coordinates coordinates;
    boolean isHome;

    public LocationDto() {
    }

    public LocationDto(Long id, String title, Coordinates coordinates, boolean isHome) {
        this.id = id;
        this.title = title;
        this.coordinates = coordinates;
        this.isHome = isHome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isHome() {
        return isHome;
    }

    public void setHome(boolean home) {
        isHome = home;
    }
}
