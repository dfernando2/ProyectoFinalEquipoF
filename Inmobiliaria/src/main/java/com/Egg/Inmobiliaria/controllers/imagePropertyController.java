
package com.Egg.Inmobiliaria.controllers;


import com.Egg.Inmobiliaria.models.ImageProperty;
import com.Egg.Inmobiliaria.models.Property;
import com.Egg.Inmobiliaria.repositories.ImagePropertyRepository;
import com.Egg.Inmobiliaria.repositories.PropertyRepository;
import com.Egg.Inmobiliaria.services.ImagePropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;


@Controller
@RequestMapping("/image")
public class imagePropertyController {


    @Autowired
    private ImagePropertyService imageService;
    @Autowired
    private ImagePropertyRepository imageRepository;
    @Autowired
    private PropertyRepository propertyRepository;

    @GetMapping("/property/{id}")
    public ResponseEntity<byte[]> imageProperty(@PathVariable Long idProperty) {

        Optional <Property> property = propertyRepository.findById(idProperty);

        Optional<ImageProperty> image = Optional.ofNullable(imageRepository.findByProperty(property));

        if (image.isPresent()) {

            // Obtener la imagen desde tu servicio (imageService) basado en el ID
            byte[] imageBytes = imageService.getImageBytesById(image.get().getId());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }


    }
}

