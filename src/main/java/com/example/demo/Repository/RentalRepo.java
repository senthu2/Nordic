package com.example.demo.Repository;

import com.example.demo.Model.Cars;
import com.example.demo.Model.PickUpPoint;
import com.example.demo.Model.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RentalRepo
{
    @Autowired
    JdbcTemplate template;
    public List<Rental> fetchAll()
    {
        String sql = "select r.*, d.mileage, d.price_id, r.total_price, DATEDIFF(r.to_Date,r.from_Date)+1 as days_of_rental"
                + " from NMP.Rentals r"
                + " inner join NMP.documentation d  on d.documentation_id = r.documentation_id"
                + " inner join NMP.price_group g on d.price_id = g.price_id"
                +    " , NMP.seasons s"
                +  " where r.from_Date between s.from_Date and s.to_Date";


        RowMapper<Rental> rowmapper = new BeanPropertyRowMapper<>(Rental.class);
        return template.query(sql, rowmapper);
    }



    public Cars chooseCar(String from, String to, int price_id)
    {
        //leje af bil med dato og pris_id som parametre
        String sql = " select d.* from NMP.documentation d\n" +
                " where true\n" +
                " and d.car_status = 'Available'\n" +
                " and d.price_id = ?\n" +
                " and d.documentation_id not in\n" +
                " (select r.documentation_id from Rentals r\n" +
                " where r.documentation_id is not null\n" +
                "  and (r.from_Date between ? and ?\n" +
                "  or  r.to_Date between ? and ?))\n" +
                " order by d.mileage \n" +
                " limit 1 ";

        RowMapper<Cars> rowmapper = new BeanPropertyRowMapper<>(Cars.class);
        return template.queryForObject(sql, rowmapper, price_id, from, to, from, to);
    }

    public Rental getRentalPrice(Rental r)
    {
        System.out.println("PRICE sql price.id "+r.getPrice_id()+" dato "+ r.getFrom_Date()+" pick "+r.getPickUP_id()+" drop "+ r.getDropOf_id());
        String sql = "SELECT ((pg.price*s.price_factor)*(DATEDIFF(?, ?) + 1)+(pup.kmAway*0.7)+(dop.kmAway*0.7)) \n" +
                "as price from NMP.price_group pg, NMP.seasons s, NMP.PickUpPoints pup, NMP.PickUpPoints dop \n" +
                "where pg.price_id = ? and ? between s.from_Date and s.to_Date and pup.pickUP_id = ? and dop.pickUP_id = ? \n";
        double price = template.queryForObject(sql, Double.class,  r.getTo_Date(),r.getFrom_Date(),r.getPrice_id(),r.getFrom_Date(), r.getPickUP_id(), r.getDropOf_id());
        r.setTotal_price(price);
        System.out.println(r.getPrice_id());

        return r;
    }


    public Rental createRental(Rental r)
    {
        String sql = "insert into Rentals (rental_id, documentation_id, customer_id, from_Date,to_Date, total_price, rent_table, rent_chairs, rent_car_seat, rent_bike_rack, rent_bed_linnen, pickUP_id, dropOf_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        System.out.println("XXXX rental.id "+r.getRental_id()+" customer.id "+r.getCustomer_id()+" dokId = " + r.getDocumentation_id() + " fra dato " + r.getFrom_Date());
        template.update(sql, r.getRental_id(), r.getDocumentation_id(),r.getCustomer_id(), r.getFrom_Date(),r.getTo_Date(), r.getTotal_price(), r.isRent_table(), r.isRent_chairs(), r.isRent_car_seat(), r.isRent_bike_rack(), r.isRent_bed_linnen(), r.getPickUP_id(), r.getDropOf_id());
        return r;
    }

    public Rental findRentalById(int id)
    {
        String sql = "select r.*, d.mileage, DATEDIFF(r.to_Date,r.from_Date)+1 as days_of_rental" +
                " from NMP.Rentals r\n"
                + " inner join NMP.documentation d\n"
                + " on r.documentation_id = d.documentation_id"
                + " where rental_id = ?";
        RowMapper<Rental> rowmapper = new BeanPropertyRowMapper<>(Rental.class);
        Rental r = template.queryForObject(sql, rowmapper,id);
        return r;
    }

    public Boolean deleteRental(int id)
    {
        String sql = "DELETE FROM Rentals WHERE rental_id = ?";
        return template.update(sql, id) > 0;
    }

    public Rental returnerBil(int id, Rental r)
    {
        String sql = "UPDATE documentation set mileage = ? where documentation_id = ?";
        template.update(sql, r.getOverDriven(), r.getDocumentation_id());

        sql = "UPDATE Rentals set tank_Filled = ?, overDriven = ?, total_price = ? where rental_id = ?";
        template.update(sql, r.isTank_Filled(),r.getOverDriven(), r.getTotal_price(), r.getRental_id());
        System.out.println("THEAs SQL har  overdriven = "+r.getOverDriven()+" tank_Filled = " + r.isTank_Filled() + " og doc.id = "+ r.getDocumentation_id());

        return r;
    }



}