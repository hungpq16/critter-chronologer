package com.udacity.jdnd.course3.critter.user.customer;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.utils.IBaseAction;

@Service
@Transactional
public class CustomerService implements IBaseAction<Customer> {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(long id) {
        return customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException("Customer have " + id + "not found, please type other id!"));
    }

    @Override
    //@Transactional
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

}
