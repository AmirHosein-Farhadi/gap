package com.company.paw.graphql.services;

import com.company.paw.graphql.InputTypes.RequestInput;
import com.company.paw.models.Request;
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
        return requestRepository.findByReportNotNull();
    }

    @GraphQLQuery
    public List<Request> unhandeledRequests() {
        return requestRepository.findByReportIsNull();
    }

    @GraphQLQuery
    public List<Request> employeeRequests(String id) {
        return requestRepository.findByEmployeeId(id);
    }

    @GraphQLQuery
    public Request getRequest(String id) {
        return requestRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public Request addRequest(RequestInput input) {
        return requestRepository.save(convertService.setRequest(new Request(), input));
    }

    @GraphQLMutation
    public Request editRequest(String requestId, RequestInput input) {
        Request request;
        if (requestRepository.findById(requestId).isPresent())
            request = requestRepository.findById(requestId).get();
        else
            return null;

        return requestRepository.save(convertService.setRequest(request, input));
    }

    @GraphQLMutation
    public Request deleteRequest(String requestId) {
        Optional<Request> requestOptional = requestRepository.findById(requestId);
        requestOptional.ifPresent(requestRepository::delete);
        return requestOptional.orElse(null);
    }
}
