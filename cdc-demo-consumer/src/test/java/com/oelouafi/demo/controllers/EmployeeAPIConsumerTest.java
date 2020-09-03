package com.oelouafi.demo.controllers;


import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ibm.icu.impl.Assert;
import com.oelouafi.demo.services.EmployeeConsumerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@PactTestFor(providerName = "employee-provider-demo", port = "8080")
public class EmployeeAPIConsumerTest {

    @Autowired
    private EmployeeConsumerService employeeConsumerService;

    @Pact(provider = "employee-provider-demo",consumer = "employee-consumer-demo")
    RequestResponsePact checkEmployeesList(PactDslWithProvider pactDslWithProvider){
        Map<String,String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return pactDslWithProvider
                //.given("test demo CDC") -- state
                .uponReceiving("ConsumerDemo Test interaction")
                .path("/employees")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body("[{\"firstName\":\"Unit\",\"lastName\":\"Testing\",\"employeeNumber\":12}]")
                .toPact();
    }

    @Pact(provider = "employee-provider-demo",consumer = "employee-consumer-demo")
    RequestResponsePact checkAddEmployee(PactDslWithProvider pactDslWithProvider){
        Map<String,String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return pactDslWithProvider
                .uponReceiving("Employee add interaction")
                .method("POST")
                .path("/employees/add")
                .body("{\"firstName\":\"Integration\",\"lastName\":\"Testing\",\"employeeNumber\":1}")
                .willRespondWith()
                .status(201)
                .headers(headers)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "checkEmployeesList")
    void test(){
        Assertions.assertNotNull(employeeConsumerService.getEmployees());
    }

    @Test
    @PactTestFor(pactMethod = "checkAddEmployee")
    void testEmployeesCount() throws URISyntaxException, JsonProcessingException {
        Assertions.assertTrue(employeeConsumerService.addEmployee(1L,"Integration","Testing"));
    }


}
