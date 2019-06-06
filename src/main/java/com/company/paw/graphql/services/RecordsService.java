package com.company.paw.graphql.services;

import com.company.paw.Repositories.RecordRepository;
import com.company.paw.models.Record;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecordsService {
    private final RecordRepository recordRepository;

    @GraphQLQuery
    public List<Record> allRecords() {
        return recordRepository.findAll();
    }

    @GraphQLQuery
    public Record getRecord(String id) {
        return recordRepository.findById(id).orElse(null);
    }
}
