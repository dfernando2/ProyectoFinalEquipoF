package com.Egg.Inmobiliaria.services;

import com.Egg.Inmobiliaria.models.ImageUser;
import com.Egg.Inmobiliaria.enums.Role;
import com.Egg.Inmobiliaria.exceptions.MiException;
import com.Egg.Inmobiliaria.models.Usuario;
import com.Egg.Inmobiliaria.repositories.UserRepository;

import java.io.IOException;
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
                       String dni, String password, String password2, String rol) throws Exception {

        validate(name, email, dni, password, password2, rol);

        Usuario usuario = new Usuario();

        usuario.setName(name);
        usuario.setEmail(email);
        usuario.setDni(dni);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));

        if (rol.equals("Comprador")) {
            usuario.setRol(Role.CLIENT);
        } else if (rol.equals("Vendedor")) {
            usuario.setRol(Role.ENTITY);
        } else if (rol.equals("Comprador y vendedor")) {
            usuario.setRol(Role.BOTHROLE);
        }

        ImageUser imageUser = imageUserService.create(file, usuario);
        usuario.setImage(imageUser);

        userRepository.save(usuario);

    }

    public Usuario getOne(Long id) {
        return userRepository.getOne(id);
    }

    public Usuario getOneDni(String dni) {
        return userRepository.findByDni(dni);
    }

    public List<Usuario> listUser() {

        List<Usuario> users = new ArrayList<>();
        users = userRepository.findAll();

        return users;
    }

    @Transactional
    public void update(MultipartFile file, Long id, String email,
                         String password, String password2, String rol) throws Exception {
//TODO agarrar este try para mostrar en pantalla
//        if (password.isEmpty() || password == null || password.length() <= 5) {
//            throw new MiException("La contraseña no puede estar vacía y debe tener más de 5 dígitos");
//        }
//        if (!password.equals(password2)) {
//            throw new MiException("Las contraseñas ingresadas deben ser iguales");
//        }

        Optional<Usuario> answer = userRepository.findById(id);

        String name = answer.get().getName();
        String dni = answer.get().getDni();

        validate(name, email, dni, password, password2, rol);

        try {


            Usuario usuario = answer.get();
            if (!email.isEmpty()) {
                usuario.setEmail(email);
            }
            usuario.setPassword(null);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));

            if (rol.equals("Comprador")) {
                usuario.setRol(Role.CLIENT);
            } else if (rol.equals("Vendedor")) {
                usuario.setRol(Role.ENTITY);
            } else if (rol.equals("Comprador y vendedor")) {
                usuario.setRol(Role.BOTHROLE);
            }
            if (file != null) {
                ImageUser imageUser = imageUserService.update(file, answer.get().getImage().getId());
                usuario.setImage(imageUser);
            }
            userRepository.save(usuario);
            }
        catch (MiException e) {
            throw new MiException(e.getMessage());

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

    public void validate(String name, String email, String dni, String password, String password2, String rol) throws MiException {

        if (name.isEmpty() || name == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacio");

        }
        if (rol == null || rol.isEmpty()) {
            throw new MiException("El rol no puede ser nulo o estar vacio");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("El email no puede ser nulo o estar vacio");
        }
        if (dni == null || dni.isEmpty()) {
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
            user = userRepository.findByDni(username);
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
            throw new UsernameNotFoundException("Usuario: " + username + " no encontrado");
        }
    }
}