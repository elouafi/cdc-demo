package com.oeloaufi.cdcdemo.services;

import com.oeloaufi.cdcdemo.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class EmployeeService {

    private  List<Employee> employees;

    {
        Employee employee = Employee.builder().employeeNumber(12L)
                .firstName("Unit")
                .lastName("Testing")
                .build();
        Employee employee1 = Employee.builder().employeeNumber(11L)
                .firstName("Integration")
                .lastName("Testing")
                .build();
        Employee employee2 = Employee.builder().employeeNumber(11L)
                .firstName("Contract")
                .lastName("Testing")
                .build();

        employees = Arrays.asList(employee, employee1, employee2);
    }
    public List<Employee> getAllEmployees(){
        return employees;
    }

    public List<Employee> addEmployee(Employee employee){
        employees.add(employee);
        return employees;
    }
}
