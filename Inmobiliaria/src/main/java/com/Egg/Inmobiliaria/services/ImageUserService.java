package com.Egg.Inmobiliaria.services;

import com.Egg.Inmobiliaria.entitys.ImageUser;
import com.Egg.Inmobiliaria.repositories.ImageUserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageUserService {
    @Autowired
    private ImageUserRepository imageUserRepository;
    
    
    public ImageUser guardar(MultipartFile archivo) throws Exception{
        if(archivo != null){
            try{
                
                ImageUser imagen = new ImageUser();
                
                imagen.setMime(archivo.getContentType());
                
                imagen.setNombre(archivo.getName());
                
                imagen.setContenido(archivo.getBytes());
                
                return imageUserRepository.save(imagen);
                
            }catch (Exception e){
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    
    public ImageUser actualizar(MultipartFile archivo, String idImageUser) throws Exception{
         if(archivo != null){
            try{
                
                ImageUser imagen = new ImageUser();
                
                if(idImageUser != null){
                    Optional<ImageUser> respuesta = imageUserRepository.findById(idImageUser);
                
                if(respuesta.isPresent()){
                    imagen = respuesta.get();
                }
                
                }
                
                imagen.setMime(archivo.getContentType());
                
                imagen.setNombre(archivo.getName());
                
                imagen.setContenido(archivo.getBytes());
                
                return imageUserRepository.save(imagen);
                
            }catch (Exception e){
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
}
