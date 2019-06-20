package com.company.paw.graphql.services;

import com.company.paw.graphql.InputTypes.EmployeeInput;
import com.company.paw.models.Employee;
import com.company.paw.models.Organization;
import com.company.paw.repositories.EmployeeRepository;
import com.company.paw.repositories.OrganizationRepository;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
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
        return employeeRepository.save(convertService.setEmployee(new Employee(), employeeInput));
    }

    @GraphQLMutation
    public Employee updateEmployee(String employeeId, EmployeeInput employeeInput) {
        Employee employee;
        if (employeeRepository.findById(employeeId).isPresent())
            employee = employeeRepository.findById(employeeId).get();
        else
            return null;

        return employeeRepository.save(convertService.setEmployee(employee, employeeInput));
    }

    @GraphQLMutation
    public Employee deleteEmployee(String employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        employeeOptional.ifPresent(employeeRepository::delete);
        return employeeOptional.orElse(null);
    }

    @GraphQLMutation
    public Employee editEmployeeBullet(String employeeId, int numberOfBullets) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        Organization organization;
        if (employeeOptional.isPresent()) {
            employeeOptional.get().setBullets(employeeOptional.get().getBullets() + numberOfBullets);
            organization = employeeOptional.get().getOrganization();
            organization.setBulletsQuantity(organization.getBulletsQuantity() + (numberOfBullets * -1));
            organizationRepository.save(organization);
            return employeeRepository.save(employeeOptional.get());
        } else
            return null;
    }
}
