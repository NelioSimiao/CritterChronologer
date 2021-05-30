package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.model.*;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;


    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleService.saveSchedule(toSchedule(scheduleDTO));
       return  toScheduleDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {

        List<Schedule> scheduleList = scheduleService.getAllSchedules();
        return  toScheduleDTOs(scheduleList);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> scheduleByPetId = scheduleService.findScheduleByPetId(petId);
        return  toScheduleDTOs(scheduleByPetId);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedule = scheduleService.findScheduleByEmployeeId(employeeId);
        return  toScheduleDTOs(schedule);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> scheduleByCustomerId = scheduleService.findScheduleByCustomerId(customerId);
        return  toScheduleDTOs(scheduleByCustomerId);
    }

    private Schedule toSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        if (scheduleDTO.getPetIds().size()!= 0) {
            for(Long pedId : scheduleDTO.getPetIds()){
                Pet pet = petService.getPetById(pedId);
                schedule.getPets().add(pet);
            }
        }
        if (scheduleDTO.getEmployeeIds().size()!= 0) {
            for(Long employeeId : scheduleDTO.getEmployeeIds()){
                Employee employee = employeeService.getEmployeeById(employeeId);
                employee.setSkills(scheduleDTO.getActivities());
                schedule.getEmployees().add(employee);
            }
        }

        return schedule;
    }

    private ScheduleDTO toScheduleDTO(Schedule schedule ) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Long> petIds = new ArrayList<>();
        if(schedule.getPets().size() != 0){
            for(Pet pet : schedule.getPets()){
                petIds.add(pet.getId());
            }
        }
        List<Long> employeeIds = new ArrayList<>();
        if(schedule.getEmployees().size() != 0){
            for(Employee employee : schedule.getEmployees()){
                employeeIds.add(employee.getId());
                scheduleDTO.getActivities().addAll(employee.getSkills());
            }
        }


        scheduleDTO.setPetIds(petIds);
        scheduleDTO.setEmployeeIds(employeeIds);
        return scheduleDTO;
    }


    private List<ScheduleDTO> toScheduleDTOs(List<Schedule> scheduleList) {
        List<ScheduleDTO> scheduleArrayList = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            ScheduleDTO scheduleDTO = toScheduleDTO(schedule);
            scheduleArrayList.add(scheduleDTO);
        }
        return scheduleArrayList;
    }


}



