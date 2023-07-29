package com.udacity.jdnd.course3.critter.user;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.customer.CustomerService;
import com.udacity.jdnd.course3.critter.user.employee.Employee;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeService;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeSkill;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into
 * separate user and customer controllers would be fine too, though that is not
 * part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private PetService petService;

    private final EmployeeService employeeService;

    private final CustomerService customerService;

    public UserController(EmployeeService employeeService, CustomerService customerService) {
        this.employeeService = employeeService;
        this.customerService = customerService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        // throw new UnsupportedOperationException();
        Customer customer = this.mapperDTOToEntity(customerDTO);
        customer = customerService.save(customer);
        return this.mapperEntityToDTO(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        // throw new UnsupportedOperationException();
        List<Customer> customers = customerService.findAll();
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        for (Customer customer : customers) {
            customerDTOs.add(this.mapperEntityToDTO(customer));
        }
        return customerDTOs;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        // throw new UnsupportedOperationException();
        Pet pet = petService.findById(petId);
        return this.mapperEntityToDTO(pet.getCustomer());
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        // throw new UnsupportedOperationException();
        Employee employee = this.mapperDTOToEntity(employeeDTO);
        employee = employeeService.save(employee);
        return this.mapperEntityToDTO(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.findById(employeeId);
        return this.mapperEntityToDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        // throw new UnsupportedOperationException();
        employeeService.setDaysAvailable(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        // throw new UnsupportedOperationException();
        DayOfWeek daysAvailable = employeeDTO.getDate().getDayOfWeek();
        Set<EmployeeSkill> employeeSkills = employeeDTO.getSkills();
        List<Employee> lstEmployee = employeeService.findByDaysAvailable(daysAvailable);
        List<EmployeeDTO> lstEmployeeDTO = new ArrayList<>();
        for (Employee employee : lstEmployee) {
            if (employee.getSkills().containsAll(employeeSkills)) {
                lstEmployeeDTO.add(this.mapperEntityToDTO(employee));
            }
        }
        return lstEmployeeDTO;
    }

    private CustomerDTO mapperEntityToDTO(Customer customer) {
        if (customer == null) {
            return null;
        }
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);

        List<Long> petIds = null;
        Set<Pet> pets = customer.getPets();
        if (pets != null) {
            petIds = new ArrayList<>();
            for (Pet pet : pets) {
                petIds.add(pet.getId());
            }
        }
        customerDTO.setPetIds(petIds);

        return customerDTO;
    }

    private Customer mapperDTOToEntity(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return null;
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);

        Set<Pet> pets = null;
        List<Long> petIds = customerDTO.getPetIds();
        if (petIds != null) {
            pets = new HashSet<>();
            for (Long petId : petIds) {
                pets.add(petService.findById(petId));
            }
        }
        customer.setPets(pets);

        return customer;
    }

    private EmployeeDTO mapperEntityToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    private Employee mapperDTOToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

}
