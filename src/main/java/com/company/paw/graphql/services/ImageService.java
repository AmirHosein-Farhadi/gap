package com.company.paw.graphql.services;

import com.company.paw.models.Image;
import com.company.paw.repositories.ImageRepository;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
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
    public Image getImage(String imageId) {
        return imageRepository.findById(imageId).orElse(null);
    }

    @GraphQLMutation
    public Image deleteImage(String imageId) {
        Optional<Image> imageOptional = imageRepository.findById(imageId);
        imageOptional.ifPresent(imageRepository::delete);
        return imageOptional.orElse(null);
    }

    LinkedList<Image> imagesIdToImages(List<String> imagesId) {
        return imagesId.stream()
                .map(image -> imageRepository.findById(image).orElse(null)).collect(Collectors.toCollection(LinkedList::new));
    }
}
