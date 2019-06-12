package com.company.paw.graphql.services;

import com.company.paw.Repositories.*;
import com.company.paw.graphql.InputTypes.EmployeeInput;
import com.company.paw.models.Employee;
import com.company.paw.models.Organization;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final OrganizationRepository organizationRepository;
    private final WeaponRepository weaponRepository;
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
        organization.getEmployees().add(employee);
        organizationRepository.save(organization);

        return employee;
    }

    @GraphQLMutation
    public Employee updateEmployee(String employeeId, EmployeeInput employeeInput) {
        return employeeRepository.save(updateInput(employeeId, employeeInput));
    }

    @GraphQLMutation
    public Employee deleteEmployee(String employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        employeeOptional.ifPresent(employeeRepository::delete);
        return employeeOptional.orElse(null);
    }

    private Employee addInput(EmployeeInput input) {
        Date birthDate = null;
        Date employeeExpirationDate = null;
        try {
            birthDate = new SimpleDateFormat("yyyy/MM/dd").parse(input.getBirthDate());
            employeeExpirationDate = new SimpleDateFormat("yyyy/MM/dd").parse(input.getEmployeeCardExpirationDate());
        } catch (Exception ignored) {
        }
        Employee employee = new Employee();
        employee.setFullName(input.getFullName());
        employee.setBirthDate(birthDate);
        employee.setEmployeeCardExpirationDate(employeeExpirationDate);
        employee.setEmployeeCardNumber(input.getEmployeeCardNumber());
        employee.setNationalId(input.getNationalId());
        employee.setAddress(input.getAddress());
        employee.setPhoneNumber(input.getPhoneNumber());
        employee.setPosition(positionRepository.findById(input.getPositionId()).get());
        employee.setOrganization(organizationRepository.findById(input.getOrganizationId()).get());
        employee.setBisim(weaponRepository.findById(input.getBisimId()).orElse(null));
        employee.setSpray(weaponRepository.findById(input.getSprayId()).orElse(null));
        employee.setShocker(weaponRepository.findById(input.getShockerId()).orElse(null));
        if (input.getImageId() != null)
            employee.setImage(imageRepository.findById(input.getImageId()).get());
        if (input.getEmployeeCardImageId() != null)
            employee.setEmployeeCardImage(imageRepository.findById(input.getEmployeeCardImageId()).get());

        employee.setReports(Collections.emptyList());
        employee.setWeapons(Collections.emptyList());
        employee.setPlates(Collections.emptyList());
        return employee;
    }

    private Employee updateInput(String employeeId, EmployeeInput input) {
        Date birthDate = null;
        Date employeeExpirationDate = null;
        try {
            birthDate = new SimpleDateFormat("yyyy/MM/dd").parse(input.getBirthDate());
            employeeExpirationDate = new SimpleDateFormat("yyyy/MM/dd").parse(input.getEmployeeCardExpirationDate());
        } catch (Exception ignored) {
        }
        Employee employee = employeeRepository.findById(employeeId).get();
        if (birthDate != null)
            employee.setBirthDate(birthDate);
        if (employeeExpirationDate != null)
            employee.setEmployeeCardExpirationDate(employeeExpirationDate);
        if (input.getEmployeeCardNumber() != null)
            employee.setEmployeeCardNumber(input.getEmployeeCardNumber());
        if (input.getFullName() != null)
            employee.setFullName(input.getFullName());
        if (input.getNationalId() != null)
            employee.setNationalId(input.getNationalId());
        if (input.getAddress() != null)
            employee.setAddress(input.getAddress());
        if (input.getPhoneNumber() != null)
            employee.setPhoneNumber(input.getPhoneNumber());
        if (input.getPositionId() != null)
            employee.setPosition(positionRepository.findById(input.getPositionId()).orElse(null));
        if (input.getImageId() != null)
            employee.setImage(imageRepository.findById(input.getImageId()).get());
        if (input.getEmployeeCardImageId() != null)
            employee.setEmployeeCardImage(imageRepository.findById(input.getEmployeeCardImageId()).get());
        if (input.getBisimId() != null)
            employee.setBisim(weaponRepository.findById(input.getBisimId()).orElse(null));
        if (input.getSprayId() != null)
            employee.setSpray(weaponRepository.findById(input.getSprayId()).orElse(null));
        if (input.getShockerId() != null)
            employee.setShocker(weaponRepository.findById(input.getShockerId()).orElse(null));
        return employee;
    }

    List<Employee> employeesIdToEmployees(List<String> employeesId) {
        return employeesId.stream()
                .map(employee -> employeeRepository.findById(employee).orElse(null))
                .collect(Collectors.toList());
    }
}
