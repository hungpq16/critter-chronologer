package com.udacity.jdnd.course3.critter.schedule;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.exception.ScheduleNotFoundException;
import com.udacity.jdnd.course3.critter.utils.IBaseAction;

@Service
public class ScheduleService implements IBaseAction<Schedule> {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public Schedule findById(long id) {
        return scheduleRepository.findById(id).orElseThrow(
                () -> new ScheduleNotFoundException("Schedule have " + id + "not found, please type other id!"));
    }

    @Override
    @Transactional
    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    List<Schedule> findByEmployeesId(Long employeeId) {
        return scheduleRepository.findSchedulesByEmployeesId(employeeId);
    }

    List<Schedule> findSchedulesByPetsId(Long petId) {
        return scheduleRepository.findSchedulesByPetsId(petId);
    }

}
