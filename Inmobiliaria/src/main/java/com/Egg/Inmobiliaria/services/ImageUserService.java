package com.Egg.Inmobiliaria.services;

import com.Egg.Inmobiliaria.exceptions.MiException;
import com.Egg.Inmobiliaria.models.ImageUser;
import com.Egg.Inmobiliaria.models.Property;
import com.Egg.Inmobiliaria.models.Usuario;
import com.Egg.Inmobiliaria.repositories.ImageUserRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
public class ImageUserService {
    @Autowired
    private ImageUserRepository imageUserRepository;

    @Transactional
    public ImageUser create(MultipartFile file, Usuario usuario) throws MiException {
        if (file != null && !file.isEmpty()) {
            try {
                ImageUser image = new ImageUser();

                image.setMime(file.getContentType());
                image.setName(file.getOriginalFilename());
                image.setContainer(file.getBytes());

                return imageUserRepository.save(image);
            } catch (IOException e) {
                // Manejar la excepción de lectura del archivo
                e.printStackTrace();
                throw new MiException("Error al procesar el archivo de imagen", e);
            }
        } else {
            System.out.println("No hay imagen");

            try {
                // Cargar la imagen predeterminada desde el directorio de recursos
                ClassPathResource defaultImageResource = new ClassPathResource("static/image/userDefault.png");

                ImageUser defaultImage = new ImageUser();
                defaultImage.setMime(Files.probeContentType(defaultImageResource.getFile().toPath()));
                defaultImage.setName("userDefault.png");
                defaultImage.setContainer(Files.readAllBytes(defaultImageResource.getFile().toPath()));

                return imageUserRepository.save(defaultImage);
            } catch (IOException e) {
                // Manejar la excepción de lectura de la imagen predeterminada
                e.printStackTrace();
                throw new MiException("Error al cargar la imagen predeterminada");
            }
        }
    }

//        @Transactional
//        public ImageUser create(MultipartFile file, Usuario usuario) throws MiException {
//
//        if (file != null) {
//            try {
//
//                ImageUser image = new ImageUser();
//
//                image.setMime(file.getContentType());
//
//                image.setName(file.getName());
//
//                image.setContainer(file.getBytes());
//
//                return imageUserRepository.save(image);
//
//            } catch (Exception e) {
//                System.err.println(e.getMessage());
//            }
//        }
//        return null;
//    }

    public List<ImageUser> list() {
        List<ImageUser> images = new ArrayList();
        images = imageUserRepository.findAll();
        return images;
    }
  
    public ImageUser getOne(String id) {

        Optional<ImageUser> answer = imageUserRepository.findById(id);

        if (answer.isPresent()) {
            System.out.println(answer.get().toString());
            return answer.get();
        } else {
            System.out.println("el usuario no tiene foto");
            return null;
        }
    }

    @Transactional
    public ImageUser update(MultipartFile file, String idImageUser) throws MiException {

        ImageUser image = new ImageUser();

        if (idImageUser != null) {
            Optional<ImageUser> answer = imageUserRepository.findById(idImageUser);

            if (answer.isPresent()) {
                image = answer.get();
            }

        }
        image.setMime(file.getContentType());

        image.setName(file.getName());

        try {
            image.setContainer(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return imageUserRepository.save(image);
    }

    @Transactional
    public void remove(String idImageUser) {
        Optional<ImageUser> imageUserAnswer = imageUserRepository.findById(idImageUser);
        if (imageUserAnswer.isPresent()) {
            imageUserRepository.deleteById(idImageUser);
        }
    }
}

