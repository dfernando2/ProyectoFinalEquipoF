package com.Egg.Inmobiliaria.services;

import com.Egg.Inmobiliaria.models.ImageUser;
import com.Egg.Inmobiliaria.enums.Role;
import com.Egg.Inmobiliaria.exceptions.MiException;
import com.Egg.Inmobiliaria.models.Usuario;
import com.Egg.Inmobiliaria.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageUserService imageUserService;

    @Transactional
    public void create(MultipartFile file, String name, String email,
                       Long dni, String password, String password2) {
//    public void create(String name, String email,
//                       Long dni, String password, String password2) {
        try {
            validate(name, email, dni, password, password2);

            Usuario user = new Usuario();

            user.setName(name);
            user.setEmail(email);
            user.setDni(dni);
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            user.setRol(Role.BOTHROLE);

            ImageUser imageUser = imageUserService.create(file);
            user.setImage(imageUser);

            userRepository.save(user);
        } catch (MiException e) {
            throw new RuntimeException(e);
        }
    }

    public Usuario getOne(Long id) {
        return userRepository.getOne(id);
    }

    public List<Usuario> listUser() {

        List<Usuario> users = new ArrayList<>();
        users = userRepository.findAll();

        return users;
    }

    @Transactional

    public void update(MultipartFile file, String id, Long dni, String name, String email,
                       String password, String password2) throws Exception {

        if (file != null) {
            try {
                Optional<Usuario> answer = userRepository.findById(Long.valueOf(id));

                if (answer.isPresent()) {

                    Usuario user = answer.get();
                    user.setName(name);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setRol(Role.BOTHROLE);

                    ImageUser imageUser = imageUserService.create(file);
                    user.setImage(imageUser);

                    userRepository.save(user);
                }
            } catch (Exception e) {

            }
        }
    }

    @Transactional
    public void changeRol(Long id) {

        Optional<Usuario> answer = userRepository.findById(id);

        if (answer.isPresent()) {

            Usuario user = answer.get();

            if (user.getRol().equals(Role.BOTHROLE)) {
                user.setRol(Role.ADMIN);

            } else if (user.getRol().equals(Role.ADMIN)) {
                user.setRol(Role.BOTHROLE);
            }
        }
    }

    public void validate(String name, String email, Long dni, String password, String password2, Role rol) throws MiException {

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
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//
//        Usuario usuario = userRepository.findByEmail(email);
//
//        if (usuario != null) {
//
//            List<GrantedAuthority> permisos = new ArrayList();
//
//            String role = "ROLE_" + usuario.getRol().toString();
//
//            GrantedAuthority p = new SimpleGrantedAuthority(role);
//
//            permisos.add(p);
//
//            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//
//            HttpSession session = attr.getRequest().getSession(true);
//
//            session.setAttribute("usuariosession", usuario);
//
//            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
//        } else {
//            throw new UsernameNotFoundException("Usuario: " + email + " no encontrado" );
//        }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario user = null;

        if (username.contains("@")) {
            user = userRepository.findByEmail(username);
        } else {
            user = userRepository.findByDni(Long.valueOf(username));
        }

        if (user != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();

            String role = "ROLE_" + user.getRol().toString();

            GrantedAuthority p = new SimpleGrantedAuthority(role);
            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", user);

            return new User(user.getEmail(), user.getPassword(), permisos);

        } else {
            throw new UsernameNotFoundException("Usuario: " + username + " no encontrado" );
        }
    }
}