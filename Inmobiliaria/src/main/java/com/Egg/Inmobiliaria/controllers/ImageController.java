package com.Egg.Inmobiliaria.controllers;

import com.Egg.Inmobiliaria.models.ImageProperty;
import com.Egg.Inmobiliaria.models.ImageUser;
import com.Egg.Inmobiliaria.models.Property;
import com.Egg.Inmobiliaria.models.Usuario;
import com.Egg.Inmobiliaria.repositories.ImagePropertyRepository;
import com.Egg.Inmobiliaria.services.ImagePropertyService;
import com.Egg.Inmobiliaria.services.ImageUserService;
import com.Egg.Inmobiliaria.services.PropertyService;
import com.Egg.Inmobiliaria.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Controller
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ImageUserService imageUserService;
    @Autowired
    UserService userService;
    @Autowired
    ImagePropertyService  imagePropertyService;

    @Autowired
    ImagePropertyRepository imagePropertyRepository;

    @Autowired
    PropertyService propertyService;

    @GetMapping("/user/{id}")
    public ResponseEntity<byte[]> userImage (@PathVariable String id) {
        ImageUser imageUser = userService.getOne(id).getImage();

        if (imageUser != null) {

            byte[] image = imageUser.getContainer();

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } else {
            return null;
        }
    }

   
    

   @GetMapping("/property/{id}")
    public ResponseEntity<byte[]> propertyImage(@PathVariable String id) {
        Property property = propertyService.getOne(Long.valueOf(id));

        ImageProperty images = imagePropertyRepository.findByProperty(property);

            System.out.println(images);

            if (images != null) {

                byte[] image = images.getContainer();

                HttpHeaders headers = new HttpHeaders();

                headers.setContentType(MediaType.IMAGE_JPEG);

                return new ResponseEntity<>(image, headers, HttpStatus.OK);
            }else {

                System.out.println("No hay imagenes");

                HttpHeaders headers = new HttpHeaders();

                headers.setContentType(MediaType.IMAGE_JPEG);

                ResponseEntity<byte[]> response = new ResponseEntity<>(headers, HttpStatus.OK);

                response.getBody();

                System.out.println(response);

                return null;

            }

}
}