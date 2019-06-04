package com.company.paw.bootstrap;

import com.company.paw.Repositories.EmployeeRepository;
import com.company.paw.models.Employee;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DbSeeder implements CommandLineRunner {
    private final EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) {
        init();
    }

    private void init() {
        Employee employee = new Employee("Amir", "966666", "1", "aaaaaa", "091899999", true);
        employeeRepository.save(employee);
    }
}
