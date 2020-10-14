package com.example.demo.Repository;

import com.example.demo.Model.Cars;
//import com.example.demo.model.Customer;
//import com.example.demo.model.Cars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarsRepo {
    @Autowired
    JdbcTemplate template;

    //metode til at hente alt informationen fra database tablet "cars" og "documentation" der er sat sammen af en
    //inner join på deres primary/foreign key
    public List<Cars> fetchAll(){
        String sql = "SELECT * FROM cars INNER JOIN documentation WHERE cars.car_id = documentation.car_id";
        RowMapper<Cars> rowMapper = new BeanPropertyRowMapper<>(Cars.class);
        return template.query(sql, rowMapper);
    }

    public List<Cars> fetchAvailablePriceGroups(String from, String to)
    {

        String sql = "select d.price_id from documentation d"
                +" where d.car_status = 'Available'"
                +" and d.documentation_id not in"
                +" (select r.documentation_id from Rentals r"
                +" where r.documentation_id is not null"
                +" and (r.from_Date between ? and ?"
                +" or  r.to_Date between ? and ?))"
                +" group by d.price_id"
                +" having count(d.price_id) > 0"
                +" order by d.price_id";

        RowMapper<Cars> rowMapper = new BeanPropertyRowMapper<>(Cars.class);
        return template.query(sql, rowMapper, from, to, from, to);
    }

    //Metode der tilføjer et nyt motorhome til listen over motorhomes ved at indsætte UI ind i db tables et af gangen
    //først cars tablet og derefter documentation
    public Cars addCar(Cars c) {

        String sql1 = "INSERT INTO cars (brand, model, beds) VALUES (?,?,?)";
        template.update(sql1, c.getBrand(), c.getModel(), c.getBeds());

        String sql3 = "SELECT @car_id := last_insert_id()";
        template.execute(sql3);

        String sql2 = "INSERT INTO documentation (car_status, car_registration, mileage, price_id, car_id) VALUES (?,?,?,?,@car_id)";
        template.update(sql2, c.getCar_status(), c.getCar_registration(), c.getMileage(), c.getPrice_id());

        return null;
    }

    //metode til at finde et specefik motorhome via deres documentation id og få alt den nødvendige data
    public Cars findCarById(int documentation_id){
        String sql = "SELECT cars.car_id,brand,model,beds,documentation_id,car_status,car_registration,mileage,price_id FROM documentation " +
                "JOIN cars ON cars.car_id = documentation.car_id WHERE documentation_id = ?";
        RowMapper<Cars> rowMapper = new BeanPropertyRowMapper<>(Cars.class);
        Cars c = template.queryForObject(sql, rowMapper, documentation_id);
        return c;
    }

    //Metode til at slette et motorhome fra documentation tablet først og dernæst cars tablet
    public Boolean deleteCar(int documentation_id){

        String sql = "DELETE FROM documentation WHERE documentation_id = ?";
        template.update(sql, documentation_id);

        String sql2 = "DELETE FROM cars WHERE car_id = ?";
        return template.update(sql2, documentation_id) < 0;
    }

    //Metode til at updatere et motorhome først i cars tablet på car id og derefter documentation på documentation id
    public Cars updateCar(int car_id, Cars c) {
        String sql = "UPDATE cars SET brand = ?, model = ?, beds = ? WHERE car_id = ?";
        template.update(sql, c.getBrand(), c.getModel(), c.getBeds(), c.getCar_id());

        String sql1 = "UPDATE documentation SET car_status = ?, car_registration = ?, mileage = ?, price_id = ? WHERE documentation_id =  ?";
        template.update(sql1, c.getCar_status(), c.getCar_registration(), c.getMileage(), c.getPrice_id(), c.getDocumentation_id());
        return null;
    }
}