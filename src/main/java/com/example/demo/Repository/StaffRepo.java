package com.example.demo.Repository;

import com.example.demo.Model.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StaffRepo {
    @Autowired
    JdbcTemplate template;


    public List<Staff> fetchAll(){
        String sql = "SELECT staff_id, staff_first_name, staff_last_name, street, house_num, city, zip_code FROM address \n" +
                "JOIN location ON address.location_id = location.location_id \n" +
                "JOIN staff ON staff.address_id = address.address_id";
        RowMapper<Staff> rowMapper = new BeanPropertyRowMapper<>(Staff.class);
        return template.query(sql, rowMapper);
    }
    public Staff addStaff(Staff s){
        String sql1 = "INSERT INTO location (city, zip_code) VALUES (?, ?);";
        template.update(sql1, s.getCity(), s.getZip_code());

        // Copy last inserted id from location and put into temp/memory for later use.
        String sql2 = "SELECT  @location_id := LAST_INSERT_ID()";
        template.execute(sql2);

        // Calls @location_id from memory and insterts into values.
        String sql3 = "INSERT INTO address (street, house_num, location_id) VALUES (?, ?, @location_id);";
        template.update(sql3, s.getStreet(), s.getHouse_num());

        String sql4 = "SELECT @address_id := LAST_INSERT_ID()";
        template.execute(sql4);

        String sql5 = "INSERT INTO staff (staff_id, staff_first_name, staff_last_name, address_id) VALUES (@address_id, ?, ?, @address_id);";
        template.update(sql5, s.getStaff_first_name(), s.getStaff_last_name());

        String sql6 = "UPDATE LOCATION SET address_id = @address_id WHERE location_id = @location_id;";
        template.execute(sql6);
        return null;
    }
    public Staff findStaffByID(int staff_id){
        String sql = "SELECT staff_id, staff_first_name, staff_last_name, street, house_num, city, zip_code\n" +
                "FROM staff\n" +
                "JOIN address\n" +
                "\tON staff.address_id = address.address_id\n" +
                "JOIN location\n" +
                "\tON address.location_id = location.location_id\n" +
                "WHERE staff_id = ?";
        RowMapper<Staff> rowMapper = new BeanPropertyRowMapper<>(Staff.class);
        Staff s = template.queryForObject(sql, rowMapper, staff_id);
        return s;
    }

    public Boolean deleteStaff(int staff_id){
        String sql = "DELETE FROM staff WHERE staff_id = ?";
        template.update(sql,staff_id);

        String sql2 = "DELETE FROM address WHERE address_id = ?";
        template.update(sql2,staff_id);

        String sql3 = "DELETE FROM location WHERE location_id = ?";
        return template.update(sql3,staff_id) < 0;
    }

    public Staff updateStaff(int staff_id, Staff s){
        String sql = "UPDATE staff SET staff_first_name = ?, staff_last_name = ? WHERE staff_id = ?";
        template.update(sql, s.getStaff_first_name(), s.getStaff_last_name(), s.getStaff_id());

        String sql1 = "UPDATE address SET street = ?, house_num = ? WHERE address_id =  ?";
        template.update(sql1,s.getStreet(),s.getHouse_num(),s.getStaff_id());

        String sql2 = "UPDATE location SET city = ?, zip_code = ? WHERE address_id =  ?";
        template.update(sql2,s.getCity(),s.getZip_code(),s.getStaff_id());
        return null;
    }
}
