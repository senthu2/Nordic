package com.example.demo.Service;

import com.example.demo.Repository.CarsRepo;
import com.example.demo.Repository.PickUpRepo;
import com.example.demo.Model.Cars;
import com.example.demo.Model.PickUpPoint;
import com.example.demo.Model.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PickUpService
{
    @Autowired
    PickUpRepo pickuprepo;


        public List<PickUpPoint> fetchAll()
        {
            return pickuprepo.fetchAll();
        }

       public PickUpPoint addPoint(PickUpPoint p)
        {
            return pickuprepo.addPoint(p);
        }

        public PickUpPoint findPointById(int PickUP_id){

            return pickuprepo.findPointById(PickUP_id);
        }

        /*public Boolean deletePoint(int PickUP_id){

            return pickuprepo.deletePoint(PickUP_id);
        }*/

        /*public PickUpPoint updatePoint(int PickUP_id, PickUpPoint p){

            return pickuprepo.updatePoint(PickUP_id, p);
        }*/

        public void calcPrice(Rental r, PickUpPoint p)
        {
            double price = p.getKmAway()*0.7;
            r.setTotal_price(r.getTotal_price() + price);
        }

}
