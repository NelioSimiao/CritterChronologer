package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.EmployeeSkill;
import com.udacity.jdnd.course3.critter.model.Pet;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    Employee getEmployeeById(long EmployeeId);
    List<Employee> getAllEmployees();
    void setAvailability(Set<DayOfWeek> availableDays, long employeeId);
    List<Employee> findEmployeesForService(Set<EmployeeSkill> skills, LocalDate date);
}
