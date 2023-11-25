package com.Egg.Inmobiliaria.services;

import com.Egg.Inmobiliaria.exceptions.MiException;
import com.Egg.Inmobiliaria.models.ImageUser;
import com.Egg.Inmobiliaria.models.Property;
import com.Egg.Inmobiliaria.models.Usuario;
import com.Egg.Inmobiliaria.repositories.ImageUserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
public class ImageUserService {
    @Autowired
    private ImageUserRepository imageUserRepository;

    @Transactional
    public ImageUser create(MultipartFile file, Usuario usuario) throws MiException {

        if (file != null) {
            try {

                ImageUser image = new ImageUser();

                image.setMime(file.getContentType());

                image.setName(file.getName());

                image.setContainer(file.getBytes());

                return imageUserRepository.save(image);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

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
    public ImageUser update(MultipartFile archivo, String idImageUser) throws MiException, IOException {
        if (archivo != null) {
            ImageUser image = new ImageUser();

            if (idImageUser != null) {
                Optional<ImageUser> answer = imageUserRepository.findById(idImageUser);

                if (answer.isPresent()) {
                    image = answer.get();
                }

            }
            image.setMime(archivo.getContentType());

            image.setName(archivo.getName());

            image.setContainer(archivo.getBytes());

            return imageUserRepository.save(image);

        }
        return null;
    }

    @Transactional
    public void remove(String idImageUser) {
        Optional<ImageUser> imageUserAnswer = imageUserRepository.findById(idImageUser);
        if (imageUserAnswer.isPresent()) {
            imageUserRepository.deleteById(idImageUser);
        }
    }
}