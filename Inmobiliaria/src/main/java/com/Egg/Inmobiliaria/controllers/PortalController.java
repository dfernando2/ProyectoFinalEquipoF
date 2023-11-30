package com.Egg.Inmobiliaria.controllers;

import com.Egg.Inmobiliaria.exceptions.MiException;
import com.Egg.Inmobiliaria.models.Property;
import com.Egg.Inmobiliaria.models.Usuario;
import com.Egg.Inmobiliaria.services.PropertyService;
import com.Egg.Inmobiliaria.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/")
public class PortalController {

    @Autowired
    private UserService userService;

    @Autowired
    private PropertyService propertyService;

    @GetMapping("/")
    public String index(Model model) {

        List<Property> properties = propertyService.getAllProperties();
        model.addAttribute("properties", properties);
        return "home.html";
    }

    @GetMapping("/navbar")
    public String navbar() {
        return "home.html";
    }

    @GetMapping("/register")
    public String register() {
        return "register.html";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam String name,
            @RequestParam String email,
            @RequestParam String dni,
            @RequestParam String password,
            @RequestParam String password2,
            @RequestParam String rol,
            @RequestParam MultipartFile file,
            ModelMap modelo) {
        try {
            userService.create(file, name, email, dni, password, password2, rol);
            modelo.put("exito", "Usuario cargado correctamente");
            return "index.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "register.html";
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap model) {

        if (error != null) {
            model.put("error", "Credenciales invalidas");
        }
        return "login";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {

        Usuario currentUser = (Usuario) session.getAttribute("usuariosession");
        
        List<Property> properties = propertyService.getAllProperties();
        model.addAttribute("properties", properties);
        
        if (currentUser.getRol().toString().equals("ADMIN")) {
            return "dashboard";
        } else {
            return "home";
        }

    }
    @GetMapping("/profile")
    public String profile(ModelMap modelo,HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);
        return "profile.html";
    }

}

//    @PreAuthorize("hasAnyRole('ADMIN', 'ENTITY', 'CLIENT', 'BOTHROLE')")
//    @GetMapping("/profile")
//    public String profile(ModelMap modelo,HttpSession session){
//        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
//         modelo.put("usuario", usuario);
//        return " /panelusuario_update.html";
//    }
//
//    @PreAuthorize("hasAnyRole('ADMIN', 'ENTITY', 'CLIENT', 'BOTHROLE')")
//    @PostMapping("/profile/{id}")
//    public String update(MultipartFile file,@PathVariable String id, @RequestParam Long dni, @RequestParam String name,@RequestParam String email,
//            @RequestParam String password,@RequestParam String password2, ModelMap modelo) {
//
//        try {
//            userService.update(file, id, dni, name, email,
//                    password, password2);
//
//            modelo.put("exito", "Usuario actualizado correctamente!");
//
//            return "home.html";
//        } catch (MiException ex) {
//
//            modelo.put("error", ex.getMessage());
//            modelo.put("nombre", name);
//            modelo.put("email", email);
//
//            return "usuario_update.html";
//        }
//
//    }
//
//
//}
