package com.example.demo.Service;

import com.example.demo.Model.Customer;
import com.example.demo.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//implement the methods from Repo class
public class CustomerService {
    @Autowired //gør så man kan bruge customerRepo classen - skaber en forbindelse
    CustomerRepo customerRepo;

    public List<Customer> fetchAll(){
        return customerRepo.fetchAll();
    }
    public Customer addCustomer(Customer c){
        return customerRepo.addCustomer(c);
    }
    public Customer findCustomerById(int customer_id){
        return customerRepo.findCustomerById(customer_id);
    }
    public Boolean deleteCustomer(int customer_id){
        return customerRepo.deleteCustomer(customer_id);
    }
    public Customer updateCustomer(int customer_id, Customer c){
        return customerRepo.updateCustomer(customer_id,c);
    }
}
