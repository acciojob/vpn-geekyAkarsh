package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String originalIp;

    private String maskedIp;

    private Boolean connected;


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Connection> connectionList = new ArrayList<>();


    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Country country;


    @ManyToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<ServiceProvider> serviceProviderList = new ArrayList<>();



    public User(){}

    public User(String userName, String password, String originalIp, String maskedIp, Boolean connected) {
        this.username = userName;
        this.password = password;
        this.originalIp = originalIp;
        this.maskedIp = maskedIp;
        this.connected = connected;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getOriginalIp() {
        return originalIp;
    }

    public void setOriginalIp(String originalIp) {
        this.originalIp = originalIp;
    }

    public String getMaskedIp() {
        return maskedIp;
    }

    public void setMaskedIp(String maskedIp) {
        this.maskedIp = maskedIp;
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }

    public void setConnectionList(List<Connection> connectionList) {
        this.connectionList = connectionList;
    }

    public Country getOriginalCountry() {
        return country;
    }

    public void setOriginalCountry(Country country) {
        this.country = country;
    }

    public List<ServiceProvider> getServiceProviderList() {
        return serviceProviderList;
    }

    public void setServiceProviderList(List<ServiceProvider> serviceProviderList) {
        this.serviceProviderList = serviceProviderList;
    }
}
