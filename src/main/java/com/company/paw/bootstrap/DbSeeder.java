package com.company.paw.bootstrap;

import com.company.paw.Repositories.*;
import com.company.paw.models.City;
import com.company.paw.models.State;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DbSeeder implements CommandLineRunner {
    private final EmployeeRepository employeeRepository;
    private final RecordRepository recordRepository;
    private final RequestRepository requestRepository;
    private final OrganizationRepository organizationRepository;
    private final WeaponRepository weaponRepository;
    private final PlateRepository plateRepository;
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    @Override
    public void run(String... args) {
        init();
    }

    private void init() {
//        Position position = new Position("Boss");

        State state = new State();
        City city = new City("ewggweg", state);
        state.setName("gggggg");
        state.addSubCity(city);
//        Employee employee = Employee.builder()
//                .fullName("Amir")
//                .employeeId("222")
//                .address("333")
//                .nationalId("234")
//                .phoneNumber("8787878")
//                .position(position)
//                .build();
//        Weapon product1 = new Weapon();
//        Plate product2 = new Plate();
//        List<Product> products = new ArrayList<>();
//        products.add(product1);
//        products.add(product2);
//        Organization organization = Organization.builder()
//                .address("ffwqafwaf")
//                .city(city)
//                .state(state)
//                .name("fwfaf")
//                .products(products)
//                .build();
//
//        Record record = new Record();
//        record.setDescription("ewewgwe");
//        record.setOrganization(organization);
//        record.setProduct(product1);
//        record.setStatus(true);
//        record.setTime(new Date());
//
//        Request request = new Request();
//        request.setDescription("regrege");
//        request.setOrganization(organization);
//        request.setTitle("ewgfgweg");
//
//        employee.setOrganization(organization);
//
//        product1.setCurrentUser(employee);
//        product1.setOrganization(organization);
//        product1.setSerial("Fwefwwhwh");
//        product1.setRequest(request);
//
//        product2.setCurrentUser(employee);
//        product2.setOrganization(organization);
//        product2.setSerial("Fweewgregwgwgfwwhwh");
//        product2.setRequest(request);


//        stateRepository.save(state);
//        cityRepository.save(city);
//        employeeRepository.save(employee);
//        weaponRepository.save(product1);
//        plateRepository.save(product2);
//        organizationRepository.save(organization);
//        recordRepository.save(record);
//        requestRepository.save(request);
    }
}
