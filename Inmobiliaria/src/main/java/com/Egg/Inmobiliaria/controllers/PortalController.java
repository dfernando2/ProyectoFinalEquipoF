package com.Egg.Inmobiliaria.controllers;

import com.Egg.Inmobiliaria.exceptions.MiException;
import com.Egg.Inmobiliaria.models.Usuario;
import com.Egg.Inmobiliaria.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class PortalController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {

        return "index.html";
    }

    @GetMapping("/register")
    public String registrar() {
        return "register.html";
    }

    @PostMapping("/registered")
    public String registro(@RequestParam String name, @RequestParam String email, @RequestParam String password, Long dni,
            String password2, ModelMap modelo, MultipartFile file) {

        try {
            userService.create(file, name, email, dni, password, password2);

            modelo.put("exito", "Usuario registrado correctamente!");

            return "index.html";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", name);
            modelo.put("email", email);

            return "register.html";
        }

    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo ) {

        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidos!");
        }

        return "login.html";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENTITY', 'CLIENT', 'BOTHROLE')")
    @GetMapping("/home")
    public String inicio(HttpSession session) {
        
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        
        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        
           return "home.html";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENTITY', 'CLIENT', 'BOTHROLE')")
    @GetMapping("/profile")
    public String profile(ModelMap modelo,HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
         modelo.put("usuario", usuario);
        return "usuario_update.html";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENTITY', 'CLIENT', 'BOTHROLE')")
    @PostMapping("/profile/{id}")
    public String update(MultipartFile file,@PathVariable String id, @RequestParam Long dni, @RequestParam String name,@RequestParam String email,
            @RequestParam String password,@RequestParam String password2, ModelMap modelo) {

        try {
            userService.update(file, id, dni, name, email,
                    password, password2);

            modelo.put("exito", "Usuario actualizado correctamente!");

            return "home.html";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", name);
            modelo.put("email", email);

            return "usuario_update.html";
        }

    }
    
    
}
