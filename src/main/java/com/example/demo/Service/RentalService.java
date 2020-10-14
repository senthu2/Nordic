package com.example.demo.Service;

import com.example.demo.Repository.RentalRepo;
import com.example.demo.Model.Cars;
import com.example.demo.Model.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService
{
    @Autowired
    RentalRepo rentalrepo;
    public List<Rental> fetchAll()
    {
        return rentalrepo.fetchAll();
    }

    public Cars chooseCar(String from, String to, int price)
    {
        return rentalrepo.chooseCar(from, to, price);
    }
    public Rental createRental(Rental r)
    {
        return rentalrepo.createRental(r);
    }

    public Rental findRentalById(int id)
    {
        return rentalrepo.findRentalById(id);
    }

    public Boolean deleteRental(int id)
    {
        return rentalrepo.deleteRental(id);
    }


    public Rental returnerBil(int id, Rental r)
    {
        double price = r.getTotal_price();

        System.out.println("returnerbil xxxx id = "+id + " rental.id = "+r.getRental_id());
        System.out.println("Det her er mileage " + r.getMileage() + " days_of_rental "+r.getDays_of_rental());


        //add extra km charge beyond 400km/day
        price += Math.max(0, ((r.getOverDriven()-r.getMileage())-(r.getDays_of_rental()*400))* 1.0);  // 1.0 euro/km



        if (!r.isTank_Filled())
        {
            price+= 70;
        }

        r.setTotal_price(price);
        return rentalrepo.returnerBil(id, r);
    }


    public void setTankPrice(int id, Rental r)
    {
        r.setTotal_price(r.getTotal_price() + 70);

        //return rentalrepo.setTankPrice(id, r);
    }

    /*public void overDriven(Rental r)
    {
        int kmToDrive = 400*r.getDays_of_rental();
        int driven = (r.getOverDriven())-r.getMileage();
        int KmTooMany = driven-kmToDrive;

        if (driven >= kmToDrive)
        {
            r.setTotal_price(r.getTotal_price() + KmTooMany);
        }
        r.setMileage(r.getOverDriven());
    } */

    public double overDriven(Rental r)
    {
        r.setTotal_price(r.getTotal_price()+Math.max(0, (r.getOverDriven()-r.getMileage())-(r.getDays_of_rental()*400)));
        System.out.println("OverDriven = " + r.getOverDriven() + ". Milage = " + r.getMileage() + ". DaysofRental = " + r.getDays_of_rental() + "Hvad er prisen " + r.getTotal_price());
        r.setMileage(r.getOverDriven());
        return r.getTotal_price();
    }

    public Rental getRentalPrice(Rental r)
    {
        return rentalrepo.getRentalPrice(r);
    }
}
