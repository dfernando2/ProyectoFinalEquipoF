package com.Egg.Inmobiliaria.services;
import com.Egg.Inmobiliaria.models.ImageUser;
import com.Egg.Inmobiliaria.enums.Role;
import com.Egg.Inmobiliaria.exceptions.MiException;
import com.Egg.Inmobiliaria.models.Property;
import com.Egg.Inmobiliaria.models.User;
import com.Egg.Inmobiliaria.repositories.ImageUserRepository;
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
    private ImageUserService imageUserService;
    @Autowired
    private ImageUserRepository imageUserRepository;
    @Transactional
    public void create(MultipartFile file, Long id, String name, String email,
                         Long dni, String password, String password2) throws MiException {

        validate(name, email, dni, password, password2);

        Optional <User> userResponse = userRepository.findById(id);

        User user = new User();
       
        user.setName(name);
        user.setEmail(email);
        user.setDni(dni);
        user.setPassword(password);
        user.setRol(Role.BOTHROLE);


        ImageUser imageUser = imageUserService.create(file);
        user.setImage(imageUser);

        userRepository.save(user);
    }

    public User getOne(Long id) {
        return userRepository.getOne(id);
    }

    public List<User> listUser() {

        List<User> users = new ArrayList();
        users = userRepository.findAll();

        return users;
    }
    @Transactional
    public void update(MultipartFile file, Long id, Long dni, String name, String email,
                       String password, String password2) throws MiException {
    
        validate(name, email, dni, password, password2);
        
        Optional<User> answer = userRepository.findById(id);

        if (answer.isPresent()) {
        
            User user = answer.get();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setRol(Role.BOTHROLE);
            
            ImageUser imageUser = imageUserService.create(file);
            user.setImage(imageUser);

            userRepository.save(user);
        }
    }

    @Transactional
    public void changeRol(Long id) {
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

    public void validate(String name, String email, Long dni, String password, String password2) throws MiException {

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
