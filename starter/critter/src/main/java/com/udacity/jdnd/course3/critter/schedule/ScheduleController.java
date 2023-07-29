package com.udacity.jdnd.course3.critter.schedule;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerService;
import com.udacity.jdnd.course3.critter.user.employee.Employee;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeService;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private PetService petService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        // throw new UnsupportedOperationException();
        Schedule schedule = this.mapperDTOToEntity(scheduleDTO);
        schedule = scheduleService.save(schedule);
        return this.mapperEntityToDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        // throw new UnsupportedOperationException();
        List<Schedule> schedules = scheduleService.findAll();
        return this.mapperListEntityToListDTO(schedules);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        // throw new UnsupportedOperationException();
        List<Schedule> schedules = scheduleService.findSchedulesByPetsId(petId);
        return this.mapperListEntityToListDTO(schedules);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        // throw new UnsupportedOperationException();
        List<Schedule> schedules = scheduleService.findByEmployeesId(employeeId);
        return this.mapperListEntityToListDTO(schedules);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        // throw new UnsupportedOperationException();
        Customer customer = customerService.findById(customerId);
        List<ScheduleDTO> lstScheduleDTO = null;
        if (customer.getPets() != null) {
            lstScheduleDTO = new ArrayList<>();
            for (Pet pet : customer.getPets()) {
                List<Schedule> schedulesByPetsId = scheduleService.findSchedulesByPetsId(pet.getId());
                lstScheduleDTO.addAll(this.mapperListEntityToListDTO(schedulesByPetsId));
            }
        }
        return lstScheduleDTO;
    }

    private ScheduleDTO mapperEntityToDTO(Schedule schedule) {
        if (schedule == null) {
            return null;
        }

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Long> petIds = new ArrayList<>();
        if (schedule.getPets() != null || !schedule.getPets().isEmpty()) {
            schedule.getPets().forEach(pet -> petIds.add(pet.getId()));
        }
        scheduleDTO.setPetIds(petIds);

        List<Long> employeeIds = new ArrayList<>();
        if (schedule.getEmployees() != null || !schedule.getEmployees().isEmpty()) {
            schedule.getEmployees().forEach(employee -> employeeIds.add(employee.getId()));
        }
        scheduleDTO.setEmployeeIds(employeeIds);

        return scheduleDTO;
    }

    private Schedule mapperDTOToEntity(ScheduleDTO scheduleDTO) {
        if (scheduleDTO == null) {
            return null;
        }
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        List<Pet> pets = new ArrayList<>();
        if (scheduleDTO.getPetIds() != null || !scheduleDTO.getPetIds().isEmpty()) {
            scheduleDTO.getPetIds().forEach(petId -> pets.add(petService.findById(petId)));
        }
        schedule.setPets(pets);

        List<Employee> employees = new ArrayList<>();
        if (scheduleDTO.getEmployeeIds() != null || !scheduleDTO.getEmployeeIds().isEmpty()) {
            scheduleDTO.getEmployeeIds().forEach(employeeId -> employees.add(employeeService.findById(employeeId)));
        }
        schedule.setEmployees(employees);

        return schedule;
    }

    private List<ScheduleDTO> mapperListEntityToListDTO(List<Schedule> schedules) {
        List<ScheduleDTO> ScheduleDTOs = new ArrayList<>();
        schedules.forEach(schedule -> ScheduleDTOs.add(this.mapperEntityToDTO(schedule)));
        return ScheduleDTOs;
    }
}
