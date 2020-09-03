package com.oeloaufi.cdcdemo.controlers;

import com.oeloaufi.cdcdemo.model.Employee;
import com.oeloaufi.cdcdemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity getEmployees() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return ResponseEntity.ok().headers(headers).body(employeeService.getAllEmployees());
    }

    @PostMapping("/add")
    public ResponseEntity addEmployee(Employee employee){
        List<Employee> employees = employeeService.addEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employees);
    }
}
