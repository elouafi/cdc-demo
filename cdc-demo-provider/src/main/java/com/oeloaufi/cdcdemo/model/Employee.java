package com.oeloaufi.cdcdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private String firstName;
    private String lastName;
    private Long employeeNumber;
}
