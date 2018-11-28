package com.github.ravendcode.hello;

import com.github.ravendcode.hello.entity.Employee;
import com.github.ravendcode.hello.service.EmployeeService;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EmployeeService employeeService = new EmployeeService(Config.getInstance());

//        System.out.println(employeeService.findById(2));

//        Employee emp = new Employee();
//        emp.setId(24);

//        Employee emp = employeeService.findById(23);
//        emp.setFirstName("Bob");
//        emp.setLastName("Ms. Bob");
//        emp.setSalary(new BigDecimal(110_000));
//        employeeService.createOne(emp);
//        employeeService.updateOne(emp);

//        employeeService.deleteOne(emp);

        List<Employee> all = employeeService.getAll();
        all.forEach(System.out::println);
    }
}

