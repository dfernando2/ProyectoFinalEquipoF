package com.Egg.Inmobiliaria.controllers;

import com.Egg.Inmobiliaria.models.ImageProperty;
import com.Egg.Inmobiliaria.models.ImageUser;
import com.Egg.Inmobiliaria.services.ImagePropertyService;
import com.Egg.Inmobiliaria.services.ImageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ImageUserService imageUserService;
    @Autowired
    ImagePropertyService  imagePropertyService;

    @GetMapping("/user/{id}")
    public ResponseEntity<byte[]> userImage (@PathVariable String id){
        ImageUser imageUser = imageUserService.getOne(id);

        byte[] image= imageUser.getContainer();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(image,headers, HttpStatus.OK);
    }

    @GetMapping("/property/{id}")
    public ResponseEntity<byte[]> propertyImage (@PathVariable String id){

        ImageProperty imageProperty = imagePropertyService.getOne(id);

        byte[] image= imageProperty.getContainer();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(image,headers, HttpStatus.OK);
    }

}