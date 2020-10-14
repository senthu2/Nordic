package com.example.demo.Controller;

import com.example.demo.Model.*;
import com.example.demo.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;


import java.util.List;

@Controller
@SessionAttributes("rental")
public class HomeController {
    @Autowired
    StaffService staffService;
    @Autowired
    CustomerService customerService;
    @Autowired
    CarsService carsService;
    @Autowired
    RentalService rentalservice;
    @Autowired
    CancelService cancelService;
    @Autowired
    PickUpService pickUpService;

    @ModelAttribute("rental")
    public Rental getRental()
    {
        System.out.println("laver ny rental");
        return new Rental();
    }

    // Calls for fetch methods to get access to print different model lists on index page
    @GetMapping("/")
    public String index(Model model){
        List<Staff> staffList = staffService.fetchAll(); //Bruger staffService objekt fetchall.
        model.addAttribute("staff", staffList); //mapper den til model, så man kan bruge dem med thymeleaf

        List<Customer> customerList = customerService.fetchAll();
        model.addAttribute("customers", customerList);

        List<Cars> carsList = carsService.fetchAll();
        model.addAttribute("cars", carsList);

        List<Rental> rentalList = rentalservice.fetchAll();
        model.addAttribute("rentals", rentalList);

        List<Cancel> cancelList = cancelService.fetchAll();
        model.addAttribute("cancel", cancelList);

        return "home/index";
    }

    // Create method calls assigned to run in each their create page
    // GetMapping sends user to create page
    @GetMapping("/createStaff")
    public String createStaff(){
        return "home/create/createStaff";
    }

    // PostMaping redirects user from create to index page
    @PostMapping("/createStaff")
    public String createStaff(@ModelAttribute Staff staff){
        staffService.addStaff(staff);
        return "redirect:/";
    }

    @GetMapping("/createCustomer")
    public String createCustomer(){
        return "home/create/createCustomer";
    }

    @PostMapping("/createCustomer")
    public String createCustomer(@ModelAttribute Customer customer){
        customerService.addCustomer(customer);
        return "redirect:/";
    }

    @GetMapping("/createCar")
    public String createCar(){
        return "home/create/createCar";
    }

    @PostMapping("/createCar")
    public String createCar(@ModelAttribute Cars cars){
        carsService.addCar(cars);
        return "redirect:/";
    }

    // Fetching customer and pickUp models and then sends user to createRental page
    @GetMapping("/createRental")
    public String createForm(Model model)
    {
        List<Customer> customerList = customerService.fetchAll();
        model.addAttribute("customers", customerList);

        List<PickUpPoint> PickUpList = pickUpService.fetchAll();
        model.addAttribute("point", PickUpList);
        System.out.println(PickUpList.size());

        return "home/manageRental/createRental";
    }

    // Chcecks if pickUp been choosen from the list or a new point was entered and sends user to chooseCar page
    @PostMapping("/createRental")
    public String create(@ModelAttribute("rental") Rental r, PickUpPoint p)
    {
        System.out.println("New rental start \""+r.getFrom_Date()+"\" pickup.id "+r.getPickUP_id() + " Her kommer et rental id: " + r.getRental_id());

        System.out.println("New pickup place "+p.getPlace() + " afstand fra kontor " + p.getKmAway() + " km. Pickup ID = " + p.getPickUP_id());
        if(p.getPlace().length()>0)
        {
            PickUpPoint pp = new PickUpPoint(0,p.getPlace(), p.getKmAway());
            pickUpService.addPoint(pp);
            r.setPickUP_id(pp.getPickUP_id());
            r.setDropOf_id(pp.getPickUP_id());
        }

        System.out.println("create returnerer confirm siden");
        return "redirect:/chooseCar";  //"redirect:/";
    }

    // Fetches list of available cars for user to choose
    @GetMapping("/chooseCar")
    public String chooseCarform(@ModelAttribute("rental") Rental r, Model model)
    {
        System.out.println("Reading biler");
        List<Cars> availableCarList = carsService.fetchAvailablePriceGroups(r.getFrom_Date(), r.getTo_Date());
        model.addAttribute("pgList", availableCarList);
        List<Cars> carList = carsService.fetchAll();
        System.out.println("Biler sz "+carList.size());
        model.addAttribute("carlist", carList);

        return "home/manageRental/chooseCar";
    }

    // Print list with choose option of available cars, calculate price by season and sends user to confirmatio page
    @PostMapping("/chooseCar")
    public String chooseCarsubmit(@ModelAttribute("rental") Rental r, Model model)
    {
        System.out.println("CHOOSE dato fra"+r.getFrom_Date()+" til "+ r.getTo_Date() +" pris gruppe "+ r.getPrice_id());
        Cars c = rentalservice.chooseCar(r.getFrom_Date(), r.getTo_Date(), r.getPrice_id());
        System.out.println("CHOOSE bil id "+c.getDocumentation_id());
        r.setDocumentation_id(c.getDocumentation_id());

        //Udregn pris for bil og saeson
        rentalservice.getRentalPrice(r);
        System.out.println("Rental price "+r.getTotal_price());

        model.addAttribute("car", c);
        return "redirect:/confirm";
    }

    // Finds all choosen pickUP/Of, car and customer data
    @GetMapping("/confirm")
    public String confirmForm(@ModelAttribute("rental") Rental r, Model model)
    {
        System.out.println("Confirm-get new rental start \""+r.getFrom_Date()+"\" pickup.id "+r.getPickUP_id() + " Her kommer et rental id: " + r.getRental_id());        model.addAttribute("pickUp", pickUpService.findPointById(r.getPickUP_id()));
        model.addAttribute("pickUp", pickUpService.findPointById(r.getPickUP_id()));
        model.addAttribute("dropOf", pickUpService.findPointById(r.getDropOf_id()));
        model.addAttribute("car", carsService.findCarById(r.getDocumentation_id()));
        model.addAttribute("customer", customerService.findCustomerById(r.getCustomer_id()));
        return "home/manageRental/confirm";
    }

    // Create rental and redirect to index page
    @PostMapping("/confirm")
    public String confirmSubmit(@ModelAttribute("rental") Rental r, SessionStatus status)//, PickUpPoint p)
    {
        System.out.println("Confirm-post rental start \""+r.getFrom_Date()+"\" pickup.id "+r.getPickUP_id() + " Her kommer et rental id: " + r.getRental_id());
        rentalservice.createRental(r);
        status.setComplete();
        return "redirect:/";
    }

    // viewOne methods to find by id and print all detils about choosen object
    @GetMapping("/viewOneStaff/{staff_id}")
    public String viewOneStaff(@PathVariable("staff_id") int staff_id, Model model){
        model.addAttribute("staff", staffService.findStaffByID(staff_id));
        return "home/viewOne/viewOneStaff";
    }
    @GetMapping("/viewOneCustomer/{customer_id}")
    public String viewOne(@PathVariable("customer_id") int customer_id, Model model) {
        model.addAttribute("customer", customerService.findCustomerById(customer_id));
        return "home/viewOne/viewOneCustomer";
    }
    @GetMapping("/viewOneCar/{documentation_id}")
    public String viewOneCar(@PathVariable("documentation_id") int documentation_id, Model model) {
        model.addAttribute("cars", carsService.findCarById(documentation_id));
        return "home/viewOne/viewOneCar";
    }
    @GetMapping("/viewOne/{rental_id}")
    public String viewOneRental(@PathVariable("rental_id") int rental_id, Model model)
    {
        Rental r = rentalservice.findRentalById(rental_id);
        model.addAttribute("rent", r);
        model.addAttribute("pickUp", pickUpService.findPointById(r.getPickUP_id()));
        model.addAttribute("dropOf", pickUpService.findPointById(r.getDropOf_id()));
        model.addAttribute("car", carsService.findCarById(r.getDocumentation_id()));
        model.addAttribute("customer", customerService.findCustomerById(r.getCustomer_id()));
        return "home/viewOne/viewOneRental";
    }

    // Delete methods, delete choosen object by id
    @GetMapping("/deleteStaff/{staff_id}")
    public String deleteStaff(@PathVariable("staff_id") int staff_id){
        boolean del = staffService.deleteStaff(staff_id);
        if (del){
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
    @GetMapping("/deleteCustomer/{customer_id}")
    public String deleteCustomer(@PathVariable("customer_id") int customer_id){
        boolean deleted =customerService.deleteCustomer(customer_id);
        if(deleted){
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
    @GetMapping("/deleteCar/{documentation_id}")
    public String deleteCar(@PathVariable("documentation_id") int documentation_id){
        boolean deleted =carsService.deleteCar(documentation_id);
        if(deleted){
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
    @GetMapping("/deleteRental/{rental_id}")
    public String deleteRental(@PathVariable("rental_id") int rental_id){
        boolean deleted =rentalservice.deleteRental(rental_id);
        if(deleted){
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }

    // Update method finds choosen object by id
    @GetMapping("/updateStaff/{staff_id}")
    public String update(@PathVariable("staff_id") int staff_id, Model model){
        model.addAttribute("staff", staffService.findStaffByID(staff_id));
        return "home/update/updateStaff";
    }

    // Updates changes and redirects to index page
    @PostMapping("/updateStaffUp")
    public String updateStaff(@ModelAttribute Staff staff){
        staffService.updateStaff(staff.getStaff_id(), staff);
        return "redirect:/";
    }
    @GetMapping("/updateCustomer/{customer_id}")
    public String updateCustomer(@PathVariable("customer_id") int customer_id, Model model){
        model.addAttribute("customer", customerService.findCustomerById(customer_id));
        return "home/update/updateCustomer";
    }
    @PostMapping("/updateCustomerUp")
    public String updateCustomerUp(@ModelAttribute Customer customer){
        customerService.updateCustomer(customer.getCustomer_id(), customer);
        return "redirect:/";
    }

    @GetMapping("/updateCar/{car_id}")
    public String updateCar(@PathVariable("car_id") int car_id, Model model){
        model.addAttribute("cars", carsService.findCarById(car_id));
        return "home/update/updateCar";
    }
    @PostMapping("/updateCarUp")
    public String updateCarUp(@ModelAttribute Cars cars){
        carsService.updateCar(cars.getCar_id(), cars);
        return "redirect:/";
    }

    // cancelRental finds rental by id to print data about choosen one, calculate cancelation procentage,
    // update price and fetches again to print updated data
    @GetMapping("/cancelRental/{rental_id}")
    public String cancelRental(@PathVariable("rental_id") int rental_id, Model model, @ModelAttribute Cancel cancel, @ModelAttribute Rental rental){
        model.addAttribute("rental", rentalservice.findRentalById(rental_id));
        cancelService.createCancel(rental.getRental_id(), cancel, rental);
        model.addAttribute("rentalUP", rentalservice.findRentalById(rental_id));

        return "home/manageRental/cancelRental";
    }

    // Delete choosen for cancel rental
    @RequestMapping("/cancelRentalUp/{rental_id}")
    public String cancelRentalUp(@PathVariable("rental_id") int rental_id) {
        boolean deleted = rentalservice.deleteRental(rental_id);
        if (deleted) {
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/returnering/{rental_id}")
    public String returnering(@PathVariable("rental_id") int id, Model model)
    {
        Rental r = rentalservice.findRentalById(id);
        System.out.println("RENTAL returnering id "+r.getRental_id()+" milage "+r.getMileage());
        model.addAttribute("rental", r);


        Cars c = carsService.findCarById(r.getDocumentation_id());
        model.addAttribute("car", c);
        return "home/manageRental/returnering";
    }

    @PostMapping("/returnerBil/")
    public String returnerBil(@ModelAttribute("rental") Rental r)
    {
        System.out.println("RetunerBil " + r.getRental_id());
        rentalservice.returnerBil(r.getRental_id(),r);

        return "redirect:/";
    }
}