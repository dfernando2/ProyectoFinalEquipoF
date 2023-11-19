
package com.Egg.Inmobiliaria.controllers;



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
public class imagePropertyController {
    
    
    @GetMapping("/property/{id}")
    public ResponseEntity<byte[]> imageProperty(@PathVariable String id) {
        
        
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        
        return new ResponseEntity<>(headers, HttpStatus.OK);
        
        
        
    }
    
}