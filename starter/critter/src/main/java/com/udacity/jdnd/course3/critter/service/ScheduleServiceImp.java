package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScheduleServiceImp implements  ScheduleService{

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> findScheduleByPetId(long petId) {
        return scheduleRepository.findAllByPets_Id(petId);
    }

    @Override
    public List<Schedule> findScheduleByCustomerId(long customerId) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isPresent()){
            List<Pet> pets = customer.get().getPets();
            for (Pet pet : pets) {
                schedules.addAll(scheduleRepository.findAllByPets_Id(pet.getId()));
            }
        }
        return schedules;
    }

    @Override
    public List<Schedule> findScheduleByEmployeeId(long employeeId) {
        return scheduleRepository.findAllByEmployees_Id(employeeId);
    }

    @Override
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }
}
