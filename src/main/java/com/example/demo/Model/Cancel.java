package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cancel {
    @Id
    private int cancel_id;
    private int cancel_price;
    private String cancel_Date;
    private int cancel_days;

    // Spring require a empty construkctor
    public Cancel() {
    }

    public Cancel(int cancel_id, int cancel_price, String cancel_Date, int cancel_days) {
        this.cancel_id = cancel_id;
        this.cancel_price = cancel_price;
        this.cancel_Date = cancel_Date;
        this.cancel_days = cancel_days;
    }

    public int getCancel_id() {
        return cancel_id;
    }

    public void setCancel_id(int cancel_id) {
        this.cancel_id = cancel_id;
    }

    public int getCancel_price() {
        return cancel_price;
    }

    public void setCancel_price(int cancel_price) {
        this.cancel_price = cancel_price;
    }

    public String getCancel_Date() {
        return cancel_Date;
    }

    public void setCancel_Date(String cancel_Date) {
        this.cancel_Date = cancel_Date;
    }

    public int getCancel_days() {
        return cancel_days;
    }

    public void setCancel_days(int cancel_days) {
        this.cancel_days = cancel_days;
    }
}
