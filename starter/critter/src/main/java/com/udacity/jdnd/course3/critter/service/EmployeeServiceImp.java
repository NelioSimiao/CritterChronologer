package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@Transactional
public class EmployeeServiceImp implements  EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(long EmployeeId) {
        return employeeRepository.findById(EmployeeId).orElseThrow(()-> new NoSuchElementException( "Employee not be found"));

    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public void setAvailability(Set<DayOfWeek> availableDays, long employeeId) {
        Employee employee = this.getEmployeeById(employeeId);

        if(employee.getDaysAvailable()==null){
            employee.setDaysAvailable(availableDays);
        }
        Set<DayOfWeek> daysAvailable = employee.getDaysAvailable();

        daysAvailable.addAll(availableDays);
        employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findEmployeesForService(Set<EmployeeSkill> skills, LocalDate date) {
        List<Employee> employeesForService = new ArrayList<>();
        List<Employee> allEmployeeByDayOfWeek = employeeRepository.findAllEmployeeByDayOfWeek(date.getDayOfWeek());
       for(Employee employee : allEmployeeByDayOfWeek) {
         if(employee.getSkills().containsAll(skills))  {
             employeesForService.add(employee);
         }

        }
        return employeesForService;
    }
}
