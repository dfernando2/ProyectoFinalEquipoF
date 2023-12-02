package com.Egg.Inmobiliaria.services;

import com.Egg.Inmobiliaria.exceptions.MiException;
import com.Egg.Inmobiliaria.models.ImageProperty;
import com.Egg.Inmobiliaria.models.Property;
import com.Egg.Inmobiliaria.repositories.ImagePropertyRepository;
import com.Egg.Inmobiliaria.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImagePropertyService {
    @Autowired
    ImagePropertyRepository imagePropertyRepository;
    @Autowired
    PropertyRepository propertyRepository;

    @Transactional
    public void create(MultipartFile file, Property property) throws IOException {
        if (!file.isEmpty()) {
            try {
                ImageProperty image = new ImageProperty();
                image.setMime(file.getContentType());
                image.setName(file.getName());
                image.setContainer(file.getBytes());
                
                imagePropertyRepository.save(image);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Transactional
    public ImageProperty update(MultipartFile file, String IdImagen) {
        if (file != null) {
            try {
                
                ImageProperty imagen = new ImageProperty();
                
                if (IdImagen != null) {
                    Optional<ImageProperty> respuesta = imagePropertyRepository.findById(IdImagen);
                    
                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }
                
                imagen.setMime(file.getContentType());
                
                imagen.setName(file.getName());
                
                imagen.setContainer(file.getBytes());
                
                return imagePropertyRepository.save(imagen);
                
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
       
       
       
        /*
        Optional<ImageProperty> response = Optional.ofNullable(imagePropertyRepository.findByProperty(property));
        if (response.isPresent()) {
            imagePropertyRepository.deleteByProperty(property);
        }
        ImageProperty image = new ImageProperty();
        image.setMime(file.getContentType());
        image.setName(file.getName());
        try {
            image.setContainer(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
      
        imagePropertyRepository.save(image); */
    }


    public byte[] getImageBytesById(String id) {
        Optional<ImageProperty> response = imagePropertyRepository.findById(id);
        if (response.isPresent()) return response.get().getContainer();
        return null; //si no encuentra la imagen retorna null.
    }

    public ImageProperty getOne(String id) {

        Optional<ImageProperty> answer = imagePropertyRepository.findById(id);

        if (answer.isPresent()) {
            System.out.println(answer.get().toString());
            return answer.get();
        } else {
            System.out.println("la propiedad no tiene foto");
            return null;
        }
    }

    public Optional<ImageProperty> findByProperty(Property property) {
        return Optional.ofNullable((ImageProperty) imagePropertyRepository.findByProperty(property));


    }

    @Transactional
    public void deleteByIdProperty(ImageProperty imageProp) {

    }
    public ImageProperty save(MultipartFile archivo) throws MiException {

        if (archivo != null) {

            try {

                ImageProperty image = new ImageProperty();

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
}
