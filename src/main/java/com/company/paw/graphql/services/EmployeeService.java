package com.company.paw.graphql.services;

import com.company.paw.graphql.InputTypes.EmployeeInput;
import com.company.paw.models.Employee;
import com.company.paw.models.Organization;
import com.company.paw.repositories.EmployeeRepository;
import com.company.paw.repositories.OrganizationRepository;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final OrganizationRepository organizationRepository;
    private final ConvertService convertService;

    @GraphQLQuery
    public List<Employee> allEmployees() {
        return employeeRepository.findAll();
    }

    @GraphQLQuery
    public Employee getEmployee(String id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public Employee addEmployee(EmployeeInput employeeInput) {
        Employee employee = convertService.setEmployee(new Employee(), employeeInput);
        employee.setReports(Collections.emptyList());
        employee.setWeapons(Collections.emptyList());
        employee.setPlates(Collections.emptyList());
        employeeRepository.save(employee);

        Organization organization = organizationRepository.findById(employeeInput.getOrganizationId()).get();
        organization.getEmployees().add(employee);
        organizationRepository.save(organization);

        return employee;
    }

    @GraphQLMutation
    public Employee updateEmployee(String employeeId, EmployeeInput employeeInput) {
        return employeeRepository.save(
                convertService.setEmployee(employeeRepository.findById(employeeId).get(), employeeInput)
        );
    }

    @GraphQLMutation
    public Employee deleteEmployee(String employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        employeeOptional.ifPresent(employeeRepository::delete);
        return employeeOptional.orElse(null);
    }
}
