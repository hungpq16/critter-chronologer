package com.udacity.jdnd.course3.critter.schedule;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findSchedulesByEmployeesId(Long employeeId);

    List<Schedule> findSchedulesByPetsId(Long petId);
}
