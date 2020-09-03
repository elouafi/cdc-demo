package com.oelouafi.demo.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oelouafi.demo.interceptors.LoggingRequestInterceptor;
import com.oelouafi.demo.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeConsumerService {
    @Value("${employee_api.host:localhost}")
    private String employeeApiHost;
    @Value("${employee_api.port:8080}")
    private String employeeApiPort;

    @Autowired
    private RestTemplate restTemplate;

    public List getEmployees(){
        return restTemplate.getForObject("http://"+employeeApiHost+":"+employeeApiPort+"/employees", List.class);
    }

    public boolean addEmployee(Long employeeNumber, String firstName, String lastName) throws URISyntaxException, JsonProcessingException {
        Employee employee  = new Employee();
        employee.setEmployeeNumber(employeeNumber);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        ObjectMapper objectMapper = new ObjectMapper();
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(employee),headers);
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new LoggingRequestInterceptor());
        restTemplate.setInterceptors(interceptors);
        restTemplate.exchange("http://"+employeeApiHost+":"+employeeApiPort+"/employees/add",HttpMethod.POST,entity,Void.class);
        return  true;
    }

}
