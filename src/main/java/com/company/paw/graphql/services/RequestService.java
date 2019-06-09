package com.company.paw.graphql.services;

import com.company.paw.Repositories.*;
import com.company.paw.graphql.InputTypes.RequestInput;
import com.company.paw.models.Employee;
import com.company.paw.models.Image;
import com.company.paw.models.Organization;
import com.company.paw.models.Request;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RequestService {
    private final EmployeeRepository employeeRepository;
    private final OrganizationRepository organizationRepository;
    private final RequestRepository requestRepository;
    private final ImageRepository imageRepository;

    @GraphQLQuery
    public List<Request> allRequests() {
        return requestRepository.findAll();
    }

    @GraphQLQuery
    public Request getRequest(String id) {
        return requestRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public Request addRequest(RequestInput input) {
        return requestRepository.save(addInput(input));
    }

    private Request addInput(RequestInput input) {
        Optional<Organization> organizationOptional = organizationRepository.findById(input.getOrganizationId());
        Optional<Employee> employeeOptional = employeeRepository.findById(input.getEmployeeId());
        Optional<Image> imageOptional = imageRepository.findById(input.getImageId());

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(input.getDateOnImage());
        } catch (Exception ignored) {
        }

        Request request = new Request();
        request.setDescription(input.getDescription());
        request.setTitle(input.getTitle());
        request.setDateOnImage(date);
        request.setEmployee(employeeOptional.orElse(null));
        request.setOrganization(organizationOptional.orElse(null));
        request.setImage(imageOptional.orElse(null));

        return request;
    }
}
