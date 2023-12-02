package com.Egg.Inmobiliaria.controllers;

import com.Egg.Inmobiliaria.models.ImageProperty;
import com.Egg.Inmobiliaria.models.ImageUser;
import com.Egg.Inmobiliaria.models.Property;
import com.Egg.Inmobiliaria.repositories.ImagePropertyRepository;
import com.Egg.Inmobiliaria.services.ImagePropertyService;
import com.Egg.Inmobiliaria.services.ImageUserService;
import com.Egg.Inmobiliaria.services.PropertyService;
import com.Egg.Inmobiliaria.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

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
    public ResponseEntity<byte[]> userImage (@PathVariable Long id) {
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

//   @GetMapping("/property/{id}")
//    public ResponseEntity<byte[]> propertyImage(@PathVariable Long id) {
//        Property property = propertyService.getOne(id);
//
//        ImageProperty images = imagePropertyRepository.findFirstByProperty(property);
//
//       System.out.println(images.getName());
//
//            System.out.println(images);
//
//            if (images != null) {
//
//                byte[] image = images.getContainer();
//
//                HttpHeaders headers = new HttpHeaders();
//
//                headers.setContentType(MediaType.IMAGE_JPEG);
//
//                return new ResponseEntity<>(image, headers, HttpStatus.OK);
//            }else {
//
//                System.out.println("No hay imagenes");
//
//                HttpHeaders headers = new HttpHeaders();
//
//                headers.setContentType(MediaType.IMAGE_JPEG);
//
//                ResponseEntity<byte[]> response = new ResponseEntity<>(headers, HttpStatus.OK);
//
//                response.getBody();
//
//                System.out.println(response);
//
//                return null;
//
//            }
//
//}
    @Transactional
    @GetMapping("/property/{id}")
    public ResponseEntity<byte[]> propertyImage(@PathVariable Long id) {
        Property property = propertyService.getOne(id);

        ImageProperty image = imagePropertyRepository.findFirstByProperty(property);

        if (image != null) {
            byte[] imageData = image.getContainer();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } else {
            System.out.println("No hay imágenes");

            // Puedes devolver una imagen predeterminada o un mensaje de error en lugar de null
            // aquí devolveré una imagen predeterminada para demostración
            ClassPathResource defaultImage = new ClassPathResource("static/image/propDefault.png");

            try {
                byte[] defaultImageData = StreamUtils.copyToByteArray(defaultImage.getInputStream());

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);

                return new ResponseEntity<>(defaultImageData, headers, HttpStatus.OK);
            } catch (IOException e) {
                // Manejar la excepción de lectura de la imagen predeterminada
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

}