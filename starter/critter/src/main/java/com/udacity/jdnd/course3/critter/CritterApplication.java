package com.udacity.jdnd.course3.critter;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerService;

/**
 * Launches the Spring application. Unmodified from starter code.
 */
@SpringBootApplication
public class CritterApplication {
    
    @Autowired
    private CustomerService customerService;

	public static void main(String[] args) {
		SpringApplication.run(CritterApplication.class, args);
	}
	
    @PostConstruct
    private void postConstruct() {
        // uncomment line code below if exist NotFoundExecption
//        Customer customer = new Customer();
//        customer.setName("user");
//        customer.setNotes("user");
//        customer.setPhoneNumber("0123456");
//        customerService.save(customer);
    }

}
