package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer {
    @Id
    private int customer_id;
    private String cus_first_name;
    private String cus_last_name;
    private int driver_licence;
    private String street;
    private int house_num;
    private String city;
    private int zip_code;
    private int address_id;

    // Spring require a empty construkctor
    public Customer() {
    }

    public Customer(int customer_id, String cus_first_name, String cus_last_name, int driver_licence, String street, int house_num, String city, int zip_code, int address_id) {
        this.customer_id = customer_id;
        this.cus_first_name = cus_first_name;
        this.cus_last_name = cus_last_name;
        this.driver_licence = driver_licence;
        this.street = street;
        this.house_num = house_num;
        this.city = city;
        this.zip_code = zip_code;
        this.address_id = address_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCus_first_name() {
        return cus_first_name;
    }

    public void setCus_first_name(String cus_first_name) {
        this.cus_first_name = cus_first_name;
    }

    public String getCus_last_name() {
        return cus_last_name;
    }

    public void setCus_last_name(String cus_last_name) {
        this.cus_last_name = cus_last_name;
    }

    public int getDriver_licence() {
        return driver_licence;
    }

    public void setDriver_licence(int driver_licence) {
        this.driver_licence = driver_licence;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouse_num() {
        return house_num;
    }

    public void setHouse_num(int house_num) {
        this.house_num = house_num;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip_code() {
        return zip_code;
    }

    public void setZip_code(int zip_code) {
        this.zip_code = zip_code;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }
}

