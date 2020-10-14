package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Rental
{
    @Id
    private int rental_id;
    private int documentation_id;
    private int customer_id;


    //More details about the rental
    private String from_Date;
    private String to_Date;
    private int days_of_rental;
    private double total_price;

    //extras to rent
    private boolean rent_table;
    private boolean rent_chairs;
    private boolean rent_car_seat;
    private boolean rent_bike_rack;
    private boolean rent_bed_linnen;
    private boolean tank_Filled;
    private int overDriven;
    private int pickUP_id;
    private int dropOf_id;

    //secondary key fra bil
    private int mileage;
    //Secondary key fra price_group
    private int price_id;

    // Spring require a empty construkctor
    public Rental()
    {

    }

    public Rental(int rental_id, int documentation_id, int customer_id, String from_Date, String to_Date, int days_of_rental, int total_price, boolean rent_table, boolean rent_chairs, boolean rent_car_seat, boolean rent_bike_rack, boolean rent_bed_linnen, boolean tank_Filled, int overDriven, int mileage, double price, int PickUP_id,int dropOf_id, int price_id)
    {
        this.rental_id = rental_id;
        this.documentation_id = documentation_id;
        this.customer_id = customer_id;
        this.from_Date = from_Date;
        this.to_Date = to_Date;
        this.days_of_rental = days_of_rental;
        this.total_price = total_price;
        this.rent_table = rent_table;
        this.rent_chairs = rent_chairs;
        this.rent_car_seat = rent_car_seat;
        this.rent_bike_rack = rent_bike_rack;
        this.rent_bed_linnen = rent_bed_linnen;
        this.mileage = mileage;
        //this.price = price;
        this.pickUP_id = PickUP_id;
        this.dropOf_id = dropOf_id;
        this.price_id = price_id;
    }


    public int getRental_id()
    {
        return rental_id;
    }

    public void setRental_id(int rental_id)
    {
        this.rental_id = rental_id;
    }

    public int getDocumentation_id()
    {
        return documentation_id;
    }

    public void setDocumentation_id(int documentation_id)
    {
        this.documentation_id = documentation_id;
    }

    public int getCustomer_id()
    {
        return customer_id;
    }

    public void setCustomer_id(int customer_id)
    {
        this.customer_id = customer_id;
    }

    public String getFrom_Date()
    {
        return from_Date;
    }

    public void setFrom_Date(String from_Date)
    {
        this.from_Date = from_Date;
    }

    public String getTo_Date()
    {
        return to_Date;
    }

    public void setTo_Date(String to_Date)
    {
        this.to_Date = to_Date;
    }

    public int getDays_of_rental()
    {
        return days_of_rental;
    }

    public void setDays_of_rental(int days_of_rental)
    {
        this.days_of_rental = days_of_rental;
    }

    public double getTotal_price()
    {
        return total_price;
    }

    public void setTotal_price(double total_price)
    {
        this.total_price = total_price;
    }

    public boolean isRent_table() {
        return rent_table;
    }

    public void setRent_table(boolean rent_table) {
        this.rent_table = rent_table;
    }

    public boolean isRent_chairs()
    {
        return rent_chairs;
    }

    public void setRent_chairs(boolean rent_chairs)
    {
        this.rent_chairs = rent_chairs;
    }

    public boolean isRent_car_seat()
    {
        return rent_car_seat;
    }

    public void setRent_car_seat(boolean rent_car_seat)
    {
        this.rent_car_seat = rent_car_seat;
    }

    public boolean isRent_bike_rack()
    {
        return rent_bike_rack;
    }

    public void setRent_bike_rack(boolean rent_bike_rack)
    {
        this.rent_bike_rack = rent_bike_rack;
    }

    public boolean isRent_bed_linnen()
    {
        return rent_bed_linnen;
    }

    public void setRent_bed_linnen(boolean rent_bed_linnen)
    {
        this.rent_bed_linnen = rent_bed_linnen;
    }

    public boolean isTank_Filled()
    {
        return tank_Filled;
    }

    public void setTank_Filled(boolean tank_Filled)
    {
        this.tank_Filled = tank_Filled;
    }

    public int getOverDriven()
    {
        return overDriven;
    }

    public void setOverDriven(int overDriven)
    {
        this.overDriven = overDriven;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    /*
    public double getPrice() {
        return price;
    }

    public void setPrice(double price_group) {
        this.price = price_group;
    }
    */

    public int getPickUP_id() {
        return pickUP_id;
    }

    public void setPickUP_id(int pickUP_id) {
        this.pickUP_id = pickUP_id;
    }

    public int getDropOf_id() {
        return dropOf_id;
    }

    public void setDropOf_id(int dropOf_id) {
        this.dropOf_id = dropOf_id;
    }

    public int getPrice_id() {
        return price_id;
    }

    public void setPrice_id(int price_id) {
        this.price_id = price_id;
    }
}