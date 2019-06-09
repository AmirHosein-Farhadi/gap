package com.company.paw.graphql.services;

import com.company.paw.Repositories.ImageRepository;
import com.company.paw.models.Image;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    @GraphQLQuery
    public List<Image> allImages() {
        return imageRepository.findAll();
    }

    @GraphQLQuery
    public Image getImage(String id) {
        return imageRepository.findById(id).orElse(null);
    }

    @GraphQLMutation
    public Image addImage(String name) {
        return imageRepository.save(new Image(name, "path"));
    }

    public List<Image> imagesIdToImages(List<String> imagesId) {
        return imagesId.stream()
                .map(image -> imageRepository.findById(image).orElse(null))
                .collect(Collectors.toList());
    }
}
