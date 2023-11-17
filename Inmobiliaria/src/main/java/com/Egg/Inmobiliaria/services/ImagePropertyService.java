package com.Egg.Inmobiliaria.services;

import com.Egg.Inmobiliaria.models.ImageProperty;
import com.Egg.Inmobiliaria.models.Property;
import com.Egg.Inmobiliaria.repositories.ImagePropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImagePropertyService {

    @Autowired
    ImagePropertyRepository imagePropertyRepository;

    @Transactional
    public void create(MultipartFile file, Property property) throws IOException {
        if (!file.isEmpty()) {
            try {
                ImageProperty image = new ImageProperty();
                image.setMime(file.getContentType());
                image.setName(file.getName());
                image.setContainer(file.getBytes());
                image.setProperty(property);
                imagePropertyRepository.save(image);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Transactional
    public ImageProperty update(MultipartFile file, String id) {
        if (!file.isEmpty()) {
            try {
                ImageProperty image = new ImageProperty();
                if (id != null) {
                    Optional<ImageProperty> response = imagePropertyRepository.findById(id);
                    if (response.isPresent()) image = response.get();
                }
                image.setMime(file.getContentType());
                image.setName(file.getName());
                image.setContainer(file.getBytes());
                return imagePropertyRepository.save(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }


}
