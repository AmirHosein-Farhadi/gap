package com.company.paw.graphql.services;

import com.company.paw.Repositories.EmployeeRepository;
import com.company.paw.Repositories.ImageRepository;
import com.company.paw.Repositories.OrganizationRepository;
import com.company.paw.Repositories.RequestRepository;
import com.company.paw.graphql.InputTypes.RequestInput;
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
    public List<Request> handeledRequests() {
        List<Request> requests = requestRepository.findAll();
        for (Request request : requests)
            if (request.getReport() == null)
                requests.remove(request);
        return requests;
    }

    @GraphQLQuery
    public List<Request> unhandeledRequests() {
        List<Request> requests = requestRepository.findAll();
        for (Request request : requests)
            if (request.getReport() != null)
                requests.remove(request);
        return requests;
    }

    @GraphQLQuery
    public Request getRequest(String id) {
        return requestRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public Request addRequest(RequestInput input) {
        return requestRepository.save(addInput(input));
    }

    @GraphQLMutation
    public Request editRequest(String requestId, RequestInput input) {
        return requestRepository.save(editInput(requestId, input));
    }

    @GraphQLMutation
    public Request deleteRequest(String requestId) {
        Optional<Request> requestOptional = requestRepository.findById(requestId);
        requestOptional.ifPresent(requestRepository::delete);
        return requestOptional.orElse(null);
    }

    private Request addInput(RequestInput input) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(input.getDateOnImage());
        } catch (Exception ignored) {
        }
        Request request = new Request();
        request.setDescription(input.getDescription());
        request.setTitle(input.getTitle());
        request.setDateOnImage(date);
        request.setEmployee(employeeRepository.findById(input.getEmployeeId()).get());
        request.setOrganization(organizationRepository.findById(input.getOrganizationId()).get());
        request.setImage(imageRepository.findById(input.getImageId()).get());
        return request;
    }

    private Request editInput(String requestId, RequestInput input) {
        Request request = requestRepository.findById(requestId).get();
        if (input.getDescription() != null) {
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy/MM/dd").parse(input.getDateOnImage());
            } catch (Exception ignored) {
            }
            request.setDateOnImage(date);
        }
        if (input.getDescription() != null)
            request.setDescription(input.getDescription());
        if (input.getTitle() != null)
            request.setTitle(input.getTitle());
        if (input.getEmployeeId() != null)
            request.setEmployee(employeeRepository.findById(input.getEmployeeId()).get());
        if (input.getOrganizationId() != null)
            request.setOrganization(organizationRepository.findById(input.getOrganizationId()).get());
        if (input.getImageId() != null)
            request.setImage(imageRepository.findById(input.getImageId()).get());

        return request;
    }
}
