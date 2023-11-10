package com.Egg.Inmobiliaria.services;

import com.Egg.Inmobiliaria.entities.ImageUser;
import com.Egg.Inmobiliaria.entities.User;
import com.Egg.Inmobiliaria.enumerations.Role;
import com.Egg.Inmobiliaria.exceptions.MiException;
import com.Egg.Inmobiliaria.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ImageService imageService;

    @Transactional
    public void register(MultipartFile file, String idUser, String name, String email, Long dni, String password, String password2) throws MiException {
        
        validar(name, email, dni, password, password2);
        
       User user = new User();
       
       user.setName(name);
       user.setEmail(email);
       user.setDni(dni);
       user.setPassword(password);
       user.setRol(Role.BOTHROLE);
       
       ImageUser image = imageService.save(file);
       
       user.setImage(image);
       
       userRepository.save(user);
        
    }
    
    @Transactional
    public void update(MultipartFile file, String idUser, Long dni, String name, String email, String password, String password2) throws MiException {
    
        validar(name, email, dni, password, password2);
        
        Optional<User> answer = userRepository.findById(idUser);
        if (answer.isPresent()) {
        
            User user = answer.get();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setRol(Role.BOTHROLE);
            
            String idImage = null;
            
            if (user.getImage() != null) {
                
                idImage = user.getImage().getId();
                
            }
            
            ImageUser image = imageService.update(file, idImage);
            
            user.setImage(image);
            userRepository.save(user);
        
        }
    }
    
    public User getOne(String id) {
        return userRepository.getOne(id);
    }
    
    @Transactional
    public List<User> userList() {
        
        List<User> users = new ArrayList();
        users = userRepository.findAll();
        
        return users;
    }
    
    @Transactional
    public void cambiarRol(String id) {
        Optional<User> answer = userRepository.findById(id);
        
        if (answer.isPresent()) {
        
            User user = answer.get();
            
            if (user.getRol().equals(Role.BOTHROLE)) {
                user.setRol(Role.ADMIN);
                
            } else if (user.getRol().equals(Role.ADMIN)) {
                user.setRol(Role.BOTHROLE);
            }
        
            
        }
        
    }

    public void validar(String name, String email, Long dni, String password, String password2) throws MiException {

        if (name.isEmpty() || name == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("El email no puede ser nulo o estar vacio");
        }
        if (dni == null) {
            throw new MiException("El dni no puede ser nulo o estar vacio");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }

    }

}
