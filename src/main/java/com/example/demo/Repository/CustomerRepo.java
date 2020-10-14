package com.example.demo.Repository;

import com.example.demo.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepo {
    @Autowired // Gør så man har forbindelse til selve databsen.
    JdbcTemplate template;

    //All out SQL, that we run against our DB
    public List<Customer> fetchAll(){
        String sql = "SELECT customer_id,cus_first_name,cus_last_name,driver_licence,street,house_num,city,zip_code FROM address " +
                "JOIN location ON address.location_id = location.location_id " +
                "JOIN customer ON customer.address_id = address.address_id";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class); //laver customer om til objekter med det samme
        return template.query(sql,rowMapper );
    }

    public Customer addCustomer(Customer c){
        //Create metode, hvor vi bruger last_insert_id() til at sikre os alle id'er har samme nummer.
        // Så vi kan joine alle tables rigtigt. Man skal huske parent/child forhold når man opretter.

        String sql1 = "INSERT INTO location (city,zip_code) VALUES (?,?)";
        template.update(sql1, c.getCity(),c.getZip_code());

        String sql2 = "SELECT @location_id := last_insert_id()";
        template.execute(sql2);

        String sql3 = "INSERT INTO address(street,house_num,location_id) VALUES (?,?,@location_id)";
        template.update(sql3,c.getStreet(),c.getHouse_num());

        String sql4 = "SELECT @address_id := last_insert_id()";
        template.execute(sql4);

        String sql5 = "INSERT INTO customer (customer_id,cus_first_name, cus_last_name, driver_licence,address_id) VALUES (@address_id,?,?,?,@address_id)";
        template.update(sql5, c.getCus_first_name(), c.getCus_last_name(), c.getDriver_licence());

        String sql6 = "UPDATE LOCATION SET address_id = @address_id WHERE location_id = @location_id;";
        template.execute(sql6);

        return null;
    }

    public Customer findCustomerById(int customer_id){
        //findCustomerById metode til at finde en bestemt customer.
        String sql ="SELECT customer_id,cus_first_name,cus_last_name,driver_licence,street,house_num,city,zip_code FROM customer " +
                "JOIN address ON customer.address_id = address.address_id " +
                "JOIN location ON address.location_id = location.location_id WHERE customer_id = ?";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        Customer c = template.queryForObject(sql,rowMapper, customer_id);
        return c;
    }

    public Boolean deleteCustomer(int customer_id){
        //DeleteCustomer metode til at slette. Man skal være opmærksom på parent/child forholdet. Da man skal slette parent row først.
        String sql = "DELETE FROM customer WHERE customer_id = ?";
        template.update(sql,customer_id);

        String sql2 = "DELETE FROM address WHERE address_id = ?";
        template.update(sql2,customer_id);

        String sql3 = "DELETE FROM location WHERE address_id = ?";
        return template.update(sql3,customer_id) < 0;
    }

    public Customer updateCustomer(int customer_id, Customer c){
        //updateCustomer metode til at updater en field for customer

        String sql = "UPDATE customer SET cus_first_name = ?, cus_last_name = ?, driver_licence = ? WHERE customer_id = ?";
        template.update(sql,c.getCus_first_name(),c.getCus_last_name(),c.getDriver_licence(),c.getCustomer_id());

        String sql1 = "UPDATE address SET street = ?, house_num = ? WHERE address_id =  ?";
        template.update(sql1,c.getStreet(),c.getHouse_num(),c.getCustomer_id());

        String sql2 = "UPDATE location SET city = ?, zip_code = ? WHERE address_id =  ?";
        template.update(sql2,c.getCity(),c.getZip_code(),c.getCustomer_id());
        return null;
    }
}
