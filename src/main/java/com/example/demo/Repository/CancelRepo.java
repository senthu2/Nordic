package com.example.demo.Repository;

import com.example.demo.Model.Cancel;
import com.example.demo.Model.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CancelRepo {
    @Autowired
    JdbcTemplate template;

    public List<Cancel> fetchAll(){
        String sql = "SELECT * FROM cancel";
        RowMapper<Cancel> rowMapper = new BeanPropertyRowMapper<>(Cancel.class);
        return template.query(sql, rowMapper);
    }

    public Cancel createCancel(int rental_id, Cancel ca, Rental r)
    {
        // Search and copy price and from_date from rentals into cancel.
        String sql = "INSERT INTO cancel(cancel_price, cancel_days)\n" +
                "SELECT total_price, from_Date FROM rentals\n" +
                "WHERE rental_id = ?;";
        template.update(sql, rental_id);

        // Copy last inserted id from cancel and put into temp/memory for later use.
        String sqlL = "SELECT @cancel_id := last_insert_id()";
        template.execute(sqlL);

        // Set current date into cancel_date field.
        String sql1 = "UPDATE cancel\n" +
                "SET cancel_Date = CURDATE()\n" +
                "WHERE cancel_id = @cancel_id";
        template.execute(sql1);

        // Calculate amount of days between the current date and rental from_date and set into cancel_days.
        String sql2 = "UPDATE cancel\n" +
                "SET cancel_days = DATEDIFF(cancel_days,CURDATE())\n" +
                "WHERE cancel_id = @cancel_id";
        template.execute(sql2);

        // Next 4 strings calculate and change cancel price, price procentage depends on amount of days cancelation happens before rental day.
        // If cancel 50 days or more, cancel price = 20%
        String sql3 = "UPDATE cancel \n" +
                "SET cancel_price = cancel_price / 100 * 20 \n" +
                "WHERE (cancel_id = @cancel_id \n" +
                "AND (cancel_days > 49)); \n";
        template.execute(sql3);

        // If cancel between 49 and 16 days, cancel price = 50%
                String sql4 = "UPDATE cancel \n" +
                "SET cancel_price = cancel_price / 100 * 50 \n" +
                "WHERE (cancel_id = @cancel_id \n" +
                "AND (cancel_days > 15) \n" +
                "AND (cancel_days < 50)); \n";
        template.execute(sql4);

        // If cancel between 15 and 2 days, cancel price = 80%
            String sql5 = "UPDATE cancel\n" +
                "SET cancel_price = cancel_price / 100 * 80 \n" +
                "WHERE (cancel_id = @cancel_id \n" +
                "AND (cancel_days > 1) \n" +
                "AND (cancel_days < 14)); \n";
        template.execute(sql5);

        // If cancel same or one day before, cancel price = 95%
            String sql6 = "UPDATE cancel\n" +
                "SET cancel_price = cancel_price / 100 * 95 \n" +
                "WHERE (cancel_id = @cancel_id \n" +
                "AND (cancel_days < 2));";
        template.execute(sql6);

        // Copy calculated price back into rentals.
        // (that makes it easier to print the price in cancelation percipt and hold to the same primary key)
        String sql7 = "UPDATE rentals \n" +
                "SET total_price = (SELECT cancel_price FROM cancel WHERE cancel_id = @cancel_id) \n" +
                "WHERE rental_id = ?;";
        template.update(sql7, rental_id);

        return null;
    }

    // Selects last inserted id to always find the last created cancel
    public Cancel findCancelById(int cancel_id)
    {
        String sqlL = "SELECT @cancel_id := last_insert_id()";
        template.execute(sqlL);
        String sql = "SELECT * FROM cancel where cancel_id = @cancel_id";
        RowMapper<Cancel> rowMapper = new BeanPropertyRowMapper<>(Cancel.class);
        Cancel ca = template.queryForObject(sql, rowMapper,cancel_id);
        return ca;
    }

    public Boolean deleteCancel(int cancel_id)
    {
        String sql = "DELETE FROM cancel WHERE cancel_id = ?";
        return template.update(sql, cancel_id) < 0;
    }
}
