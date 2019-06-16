package com.company.paw.graphql.services;

import com.company.paw.graphql.InputTypes.RequestInput;
import com.company.paw.models.Employee;
import com.company.paw.models.Request;
import com.company.paw.repositories.EmployeeRepository;
import com.company.paw.repositories.ImageRepository;
import com.company.paw.repositories.OrganizationRepository;
import com.company.paw.repositories.RequestRepository;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final ConvertService convertService;

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
        Request request = convertService.setRequest(new Request(), input);

        Employee employee = request.getEmployee();
        List<Request> requests = employee.getRequests();
        requests.add(request);
        return request;
    }

    @GraphQLMutation
    public Request editRequest(String requestId, RequestInput input) {
        return convertService.setRequest(requestRepository.findById(requestId).get(), input);
    }

    @GraphQLMutation
    public Request deleteRequest(String requestId) {
        Optional<Request> requestOptional = requestRepository.findById(requestId);
        requestOptional.ifPresent(requestRepository::delete);
        return requestOptional.orElse(null);
    }
}
