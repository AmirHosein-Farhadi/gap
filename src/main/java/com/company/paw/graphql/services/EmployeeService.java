package com.company.paw.graphql.services;

import com.company.paw.Repositories.EmployeeRepository;
import com.company.paw.Repositories.ImageRepository;
import com.company.paw.Repositories.OrganizationRepository;
import com.company.paw.Repositories.PositionRepository;
import com.company.paw.graphql.InputTypes.EmployeeInput;
import com.company.paw.models.Employee;
import com.company.paw.models.Image;
import com.company.paw.models.Organization;
import com.company.paw.models.Report;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final OrganizationRepository organizationRepository;
    private final ImageRepository imageRepository;

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
        Employee employee = addInput(employeeInput);
        employeeRepository.save(employee);

        Organization organization = organizationRepository.findById(employeeInput.getOrganizationId()).get();
        if (organization.getEmployees() != null)
            organization.getEmployees().add(employee);
        else {
            organization.setEmployees(Collections.singletonList(employee));
        }
        organizationRepository.save(organization);

        return employee;
    }

    @GraphQLMutation
    public Employee updateEmployee(String id, EmployeeInput employeeInput) {
        return employeeRepository.save(updateInput(id, employeeInput));
    }

    @GraphQLMutation
    public Employee deleteEmployee(String id) {
        Employee employee = employeeRepository.findById(id).get();
        employeeRepository.delete(employeeRepository.findById(id).get());
        return employee;
    }

    private Employee addInput(EmployeeInput input) {
        Employee employee = new Employee();
        employee.setFullName(input.getFullName());
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(input.getBirthDate());
        } catch (Exception ignored) {
        }
        employee.setBirthDate(date);
        employee.setNationalId(input.getNationalId());
        employee.setAddress(input.getAddress());
        employee.setPhoneNumber(input.getPhoneNumber());
        employee.setPosition(positionRepository.findById(input.getPositionId()).orElse(null));
        employee.setOrganization(organizationRepository.findById(input.getOrganizationId()).get());
        if (input.getImageId() != null)
            employee.setImage(imageRepository.findById(input.getImageId()).get());
        return employee;
    }

    private Employee updateInput(String inputId, EmployeeInput input) {
        Employee employee = employeeRepository.findById(inputId).orElse(new Employee());
        if (!input.getFullName().isEmpty())
            employee.setFullName(input.getFullName());
        if (!input.getNationalId().isEmpty())
            employee.setNationalId(input.getNationalId());
        if (!input.getAddress().isEmpty())
            employee.setAddress(input.getAddress());
        if (!input.getPhoneNumber().isEmpty())
            employee.setPhoneNumber(input.getPhoneNumber());
        if (!input.getPositionId().isEmpty())
            employee.setPosition(positionRepository.findById(input.getPositionId()).orElse(null));
        if (!input.getOrganizationId().isEmpty())
            employee.setOrganization(organizationRepository.findById(input.getOrganizationId()).orElse(null));
        if (!input.getImageId().isEmpty())
            employee.setImage(imageRepository.findById(input.getImageId()).get());
        return employee;
    }

    List<Employee> employeesIdToEmployees(List<String> employeesId) {
        return employeesId.stream()
                .map(employee -> employeeRepository.findById(employee).orElse(null))
                .collect(Collectors.toList());
    }
}
