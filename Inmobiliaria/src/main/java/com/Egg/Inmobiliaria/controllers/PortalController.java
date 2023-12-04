package com.Egg.Inmobiliaria.controllers;

import com.Egg.Inmobiliaria.models.Property;
import com.Egg.Inmobiliaria.models.Usuario;
import com.Egg.Inmobiliaria.repositories.UserRepository;
import com.Egg.Inmobiliaria.services.PropertyService;
import com.Egg.Inmobiliaria.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
public class PortalController {
    @Autowired
    private UserService userService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private UserRepository userRep;
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
            modelo.put("exito", "Usuario registrado correctamente!");
            return "login.html";
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
    @GetMapping("/contacto")
    public String contacto() {

        return "contacto.html";
    }
}
