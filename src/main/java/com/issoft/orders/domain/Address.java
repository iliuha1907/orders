package com.issoft.orders.domain;

public class Address {

    private final String country;
    private final String city;
    private final String street;
    private final Integer houseNumber;
    private final Integer building;

    public Address(String country, String city, String street, Integer houseNumber, Integer building) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.building = building;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public Integer getBuilding() {
        return building;
    }
}
