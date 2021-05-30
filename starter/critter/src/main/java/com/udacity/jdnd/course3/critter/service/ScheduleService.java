package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Schedule;

import java.util.List;

public interface ScheduleService {
    Schedule saveSchedule(Schedule schedule);
    List<Schedule> findScheduleByPetId(long petId);
    List<Schedule> findScheduleByCustomerId(long customerId);
    List<Schedule> findScheduleByEmployeeId(long employeeId);
    List<Schedule> getAllSchedules();
}
