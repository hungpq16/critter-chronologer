package com.udacity.jdnd.course3.critter.user.employee;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.utils.IBaseAction;

@Service
@Transactional
public class EmployeeService implements IBaseAction<Employee> {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(long id) {
        return employeeRepository.findById(id).orElseThrow(
                () -> new EmployeeNotFoundException("Employee have " + id + "not found, please type other id!"));
    }

    @Override
    //@Transactional
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    //@Transactional
    public void setDaysAvailable(Set<DayOfWeek> daysAvailable, Long id) {
        Employee employee = this.findById(id);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> findByDaysAvailable(DayOfWeek daysAvailable) {
        return employeeRepository.findByDaysAvailable(daysAvailable);
    }

}
