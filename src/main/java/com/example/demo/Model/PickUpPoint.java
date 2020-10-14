package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity

public class PickUpPoint
{
    @Id
    int pickUP_id;
    String place;
    double kmAway;

    // Spring require a empty construkctor
    public PickUpPoint()
    {

    }

    public PickUpPoint(int pickUP_id, String place, double kmAway)
    {
        this.pickUP_id = pickUP_id;
        this.place = place;
        this.kmAway = kmAway;
    }

    public int getPickUP_id() {
        return pickUP_id;
    }

    public void setPickUP_id(int pickUP_id) {
        this.pickUP_id = pickUP_id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getKmAway() {
        return kmAway;
    }

    public void setKmAway(double kmAway) {
        this.kmAway = kmAway;
    }

    public double getDrivePrice()
    {
        return kmAway * 0.7;
    }


    public String getDrivePriceStr()
    {
        double price = kmAway * 0.7;
        return String.format("%.2f",price);
    }
}
