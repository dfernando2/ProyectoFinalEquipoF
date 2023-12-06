
package com.Egg.Inmobiliaria.services;

import com.Egg.Inmobiliaria.exceptions.MiException;
import com.Egg.Inmobiliaria.models.ImageProperty;
import com.Egg.Inmobiliaria.repositories.imagePropertyRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
public class imagePropertyService {

    @Autowired
    private imagePropertyRepository imagePropertyRepository;

    public ImageProperty guardar(MultipartFile archivo) throws MiException {
        if (archivo != null) {
            try {

                ImageProperty image = new ImageProperty();

                image.setMime(archivo.getContentType());

                image.setName(archivo.getName());

                image.setContainer(archivo.getBytes());

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    public ImageProperty actualizar(MultipartFile archivo, String id) throws MiException {
        if (archivo != null) {

            try {

                ImageProperty image = new ImageProperty();

                if (id != null) {

                    Optional<ImageProperty> respuesta = imagePropertyRepository.findById(id);

                    if (respuesta.isPresent()) {

                        image = respuesta.get();

                    }

                }

                image.setMime(archivo.getContentType());

                image.setName(archivo.getName());

                image.setContainer(archivo.getBytes());
                
                return imagePropertyRepository.save(image);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    
    @Transactional(readOnly = true)
    public List<ImageProperty> listarTodos(){
        return imagePropertyRepository.findAll();
        
    }

}
