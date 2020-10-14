package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Staff {
    @Id
    private int staff_id;
    private String staff_first_name;
    private String staff_last_name;
    private String street;
    private int house_num;
    private String city;
    private int zip_code;
    private int address_id;

    // Spring require a empty construkctor
    public Staff() {
    }

    public Staff(int staff_id, String staff_first_name, String staff_last_name, String street, int house_num, String city, int zip_code, int address_id) {
        this.staff_id = staff_id;
        this.staff_first_name = staff_first_name;
        this.staff_last_name = staff_last_name;
        this.street = street;
        this.house_num = house_num;
        this.city = city;
        this.zip_code = zip_code;
        this.address_id = address_id;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public String getStaff_first_name() {
        return staff_first_name;
    }

    public void setStaff_first_name(String staff_first_name) {
        this.staff_first_name = staff_first_name;
    }

    public String getStaff_last_name() {
        return staff_last_name;
    }

    public void setStaff_last_name(String staff_last_name) {
        this.staff_last_name = staff_last_name;
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
