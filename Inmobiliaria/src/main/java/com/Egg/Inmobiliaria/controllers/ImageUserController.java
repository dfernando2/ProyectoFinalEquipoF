package com.Egg.Inmobiliaria.controllers;

import com.Egg.Inmobiliaria.entitys.User;
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
@RequestMapping("/imagenUsuario")
public class ImageUserController {
    @Autowired
    UserService userService;
    
    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imageUser(@PathVariable String id){
        
        User user = userService.getOne(id);
        
        byte[] imagen = user.getImagen().getContenido();
        
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
    
}
