package com.company.paw.graphql.services;

import com.company.paw.Repositories.PositionRepository;
import com.company.paw.models.Position;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;

    @GraphQLQuery
    public List<Position> allPositions() {
        return positionRepository.findAll();
    }

    @GraphQLQuery
    public Position getPosition(String id) {
        return positionRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public Position addPosition(String title) {
        return positionRepository.save(new Position(title));
    }

    @GraphQLMutation
    public Position editPosition(String id, String newTitle) {
        Position position = positionRepository.findById(id).get();
        position.setTitle(newTitle);
        return positionRepository.save(position);
    }

    @GraphQLMutation
    public Position deletePosition(String id) {
        Position position = positionRepository.findById(id).orElse(null);
        positionRepository.delete(position);
        return position;
    }
}
