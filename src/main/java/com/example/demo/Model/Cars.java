package com.example.demo.Model;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cars {
    @Id
    private int car_id;
    private String brand;
    private String model;
    private int beds;

    private int documentation_id;
    private String car_status;
    private String car_registration;
    private int mileage;
    private int price_id;

    // Spring require a empty construkctor
    public Cars(){

    }

    public Cars(int car_id, String brand, String model, int beds, int documentation_id, String car_status, String car_registration, int mileage, int price_id) {
        this.car_id = car_id;
        this.brand = brand;
        this.model = model;
        this.beds = beds;
        this.documentation_id = documentation_id;
        this.car_status = car_status;
        this.car_registration = car_registration;
        this.mileage = mileage;
        this.price_id = price_id;
    }

    public int getPrice_id() {
        return price_id;
    }

    public void setPrice_id(int price_id) {
        this.price_id = price_id;
    }

    public int getCar_id() {

        return car_id;
    }

    public void setCar_id(int car_id) {

        this.car_id = car_id;
    }

    public String getBrand() {

        return brand;
    }

    public void setBrand(String brand) {

        this.brand = brand;
    }

    public String getModel() {

        return model;
    }

    public void setModel(String model) {

        this.model = model;
    }

    public int getBeds() {

        return beds;
    }

    public void setBeds(int beds) {

        this.beds = beds;
    }

    public int getDocumentation_id() {

        return documentation_id;
    }

    public void setDocumentation_id(int documentation_id) {

        this.documentation_id = documentation_id;
    }

    public String getCar_status() {

        return car_status;
    }

    public void setCar_status(String car_status) {

        this.car_status = car_status;
    }

    public String getCar_registration() {

        return car_registration;
    }

    public void setCar_registration(String car_registration) {

        this.car_registration = car_registration;
    }

    public int getMileage() {

        return mileage;
    }

    public void setMileage(int mileage) {

        this.mileage = mileage;
    }
}

