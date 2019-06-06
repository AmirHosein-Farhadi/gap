package com.company.paw.graphql.services;

import com.company.paw.Repositories.RequestRepository;
import com.company.paw.models.Request;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;

    @GraphQLQuery
    public List<Request> allRequests() {
        return requestRepository.findAll();
    }

    @GraphQLQuery
    public Request getRequest(String id) {
        return requestRepository.findById(id).orElse(null);
    }
}
