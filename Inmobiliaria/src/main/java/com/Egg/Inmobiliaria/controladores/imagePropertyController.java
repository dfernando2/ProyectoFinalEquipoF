/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Egg.Inmobiliaria.controladores;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author cosas
 */
@Controller
@RequestMapping("/image")
public class imagePropertyController {
    
    
    
    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenUsuario (@PathVariable String id) {
        
        
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        
        return new ResponseEntity<>(headers, HttpStatus.OK);
        
        
        
    }
    
}
