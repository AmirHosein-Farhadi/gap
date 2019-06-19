package com.company.paw.bootstrap;

import com.company.paw.models.*;
import com.company.paw.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class DbSeeder implements CommandLineRunner {
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;
    private final PositionRepository positionRepository;
    private final OrganizationRepository organizationRepository;
    private final WeaponTypeRepository weaponTypeRepository;
    private final WeaponNameRepository weaponNameRepository;

    @Override
    public void run(String... args) {
//        init();
    }

    void init() {
        Optional<State> stateOptional = stateRepository.findByName("تهران");
        Optional<City> cityOptional = cityRepository.findByName("تهران");
        Optional<Organization> organizationOptional = organizationRepository.findByName("وزارت کشور");

        State state = new State("تهران", new LinkedList<>());
        City city = new City("تهران", state);
        if (!cityOptional.isPresent())
            city.setState(state);
        cityRepository.save(city);

        if (!stateOptional.isPresent()) {
            state.addSubCity(city);
            stateRepository.save(state);
        }

        Position position1 = new Position("مدیر کل");
        savePosition(position1);

        Position position2 = new Position("مسئول دفتر");
        savePosition(position2);

        Position position3 = new Position("معاون وزیر");
        savePosition(position3);

        Position position4 = new Position("معاون مرکز");
        savePosition(position4);

        Position position5 = new Position("رئیس اداره");
        savePosition(position5);

        Position position6 = new Position("شخصی");
        savePosition(position6);

        if (!organizationOptional.isPresent()) {
            Organization organization = Organization.builder()
                    .name("وزارت کشور")
                    .state(state)
                    .city(city)
                    .address("میدان جهاد - بلوار فاطمی")
                    .plates(new LinkedList<>())
                    .weapons(new LinkedList<>())
                    .employees(new LinkedList<>())
                    .reports(new LinkedList<>())
                    .equipments(new LinkedList<>())
                    .build();
            organizationRepository.save(organization);
        }

        WeaponType weaponType1 = new WeaponType("کمری", new LinkedList<>());
        saveWeaponType(weaponType1);

        WeaponType weaponType2 = new WeaponType("نیمه سنگین", new LinkedList<>());
        saveWeaponType(weaponType2);

        WeaponType weaponType3 = new WeaponType("سنگین", new LinkedList<>());
        saveWeaponType(weaponType3);


        WeaponName weaponName1 = new WeaponName("استار");
        saveWeaponName(weaponName1, weaponType1);

        WeaponName weaponName2 = new WeaponName("برونینگ");
        saveWeaponName(weaponName2, weaponType1);

        WeaponName weaponName3 = new WeaponName("برتا");
        saveWeaponName(weaponName3, weaponType1);

        WeaponName weaponName4 = new WeaponName("زعاف");
        saveWeaponName(weaponName4, weaponType1);

        WeaponName weaponName5 = new WeaponName("زیگ زائو");
        saveWeaponName(weaponName5, weaponType1);

        WeaponName weaponName6 = new WeaponName("لاما");
        saveWeaponName(weaponName6, weaponType1);

        WeaponName weaponName7 = new WeaponName("ماکاروف");
        saveWeaponName(weaponName7, weaponType1);

        WeaponName weaponName8 = new WeaponName("کلاش");
        saveWeaponName(weaponName8, weaponType2);

        WeaponName weaponName9 = new WeaponName("یوزی");
        saveWeaponName(weaponName9, weaponType2);
    }

    private void savePosition(Position position) {
        Optional<Position> positionOptional = positionRepository.findByTitle(position.getTitle());
        if (!positionOptional.isPresent())
            positionRepository.save(position);
    }

    private void saveWeaponType(WeaponType weaponType) {
        Optional<WeaponType> weaponTypeOptional = weaponTypeRepository.findByName(weaponType.getName());
        if (!weaponTypeOptional.isPresent())
            weaponTypeRepository.save(weaponType);
    }

    private void saveWeaponName(WeaponName weaponName, WeaponType weaponType) {
        Optional<WeaponName> weaponNameOptional = weaponNameRepository.findByName(weaponName.getName());
        if (!weaponNameOptional.isPresent()) {
            weaponNameRepository.save(weaponName);

            LinkedList<WeaponName> weaponNames = weaponType.getWeaponNames();
            weaponNames.add(weaponName);
            weaponType.setWeaponNames(weaponNames);
            weaponTypeRepository.save(weaponType);
        }
    }
}