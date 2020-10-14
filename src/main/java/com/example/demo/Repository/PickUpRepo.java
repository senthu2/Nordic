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

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.util.List;

@Repository
public class PickUpRepo
{
    @Autowired
    JdbcTemplate template;

    public List<PickUpPoint> fetchAll()
    {
        String sql = "SELECT * from NMP.PickUpPoints";
        RowMapper<PickUpPoint> rowMapper = new BeanPropertyRowMapper<>(PickUpPoint.class);
        return template.query(sql, rowMapper);
    }

    public PickUpPoint addPoint(PickUpPoint p)
    { //lambda expression til at bruge objektet med det samme
        KeyHolder kh = new GeneratedKeyHolder();

        template.update(connection -> {
                                            PreparedStatement ps = connection
                                                    .prepareStatement("INSERT INTO PickUpPoints(place, kmAway) VALUES (?,?)",Statement.RETURN_GENERATED_KEYS);
                                            ps.setString(1, p.getPlace());
                                            ps.setString(2, ""+p.getKmAway());

                                            return ps;
                                        }
                        ,kh);

        int pid = ((BigInteger)kh.getKey()).intValue();
        System.out.println("pickup name \""+p.getPlace()+"\" id "+pid);
        p.setPickUP_id(pid);
        return p;
    }

    public PickUpPoint findPointById(int PickUP_id)
    {
        String sql = "SELECT * FROM PickUpPoints where PickUP_id = ?";
        RowMapper<PickUpPoint> rowMapper = new BeanPropertyRowMapper<>(PickUpPoint.class);
        PickUpPoint p = template.queryForObject(sql, rowMapper, PickUP_id);
        return p;
    }

}
