package com.company.paw.graphql;

import com.company.paw.graphql.services.*;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SchemaGenerator {
    private final CityService cityService;
    private final EmployeeService employeeService;
    private final ImageService imageService;
    private final OrganizationService organizationService;
    private final PlateService plateService;
    private final PositionService positionService;
    private final ReportsService reportsService;
    private final RequestService requestService;
    private final StateService stateService;
    private final WeaponTypeService weaponTypeService;
    private final WeaponService weaponService;
    private final WeaponNameService weaponNameService;
    private final EquipmentService equipmentService;

    @Bean
    public GraphQL getGraphQL() {
        GraphQLSchema schema = new GraphQLSchemaGenerator()
                .withResolverBuilders(
                        new AnnotatedResolverBuilder())
                .withOperationsFromSingletons(cityService, employeeService, imageService, organizationService, plateService,
                        positionService, reportsService, requestService, stateService, weaponService, weaponTypeService, weaponNameService,equipmentService)
                .withValueMapperFactory(new JacksonValueMapperFactory())
                .generate();
        return GraphQL.newGraphQL(schema).build();
    }
}
