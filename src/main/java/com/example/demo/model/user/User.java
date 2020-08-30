package com.example.demo.model.user;


import com.example.demo.model.location.GeoPoint;
import com.example.demo.model.location.Location;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String role;

    private String name;

    private Date birthDay;

    private Long age;

    private String type;

    private String phone;

    private String email;

    private String idNumber;

    private String avatar;

    private String token;

    @OneToOne
    private GeoPoint homeAddress;

    @OneToOne
    private GeoPoint officeAddress;

    @ManyToMany
    private List<User> friends;

    @ManyToMany
    private List<User> familyMembers;

    @ManyToMany
    private List<User> colleagues;

    private boolean isDriver;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String name, Date birthDay, String phone, String email, String avatar, GeoPoint homeAddress, GeoPoint officeAddress, boolean isDriver) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.birthDay = birthDay;
        this.phone = phone;
        this.email = email;
        this.avatar = avatar;
        this.homeAddress = homeAddress;
        this.officeAddress = officeAddress;
        this.isDriver = isDriver;
    }

    public User(String username, String password, String role, String name, Date birthDay, Long age, String type, String phone, String email, String idNumber, String avatar, String token, GeoPoint homeAddress, GeoPoint officeAddress, boolean isDriver) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.birthDay = birthDay;
        this.age = age;
        this.type = type;
        this.phone = phone;
        this.email = email;
        this.idNumber = idNumber;
        this.avatar = avatar;
        this.token = token;
        this.homeAddress = homeAddress;
        this.officeAddress = officeAddress;
        this.isDriver = isDriver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isDriver() {
        return isDriver;
    }

    public void setDriver(boolean driver) {
        isDriver = driver;
    }

    public GeoPoint getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(GeoPoint homeAddress) {
        this.homeAddress = homeAddress;
    }

    public GeoPoint getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(GeoPoint officeAddress) {
        this.officeAddress = officeAddress;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<User> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(List<User> familyMembers) {
        this.familyMembers = familyMembers;
    }

    public List<User> getColleagues() {
        return colleagues;
    }

    public void setColleagues(List<User> colleagues) {
        this.colleagues = colleagues;
    }
}
