package com.Egg.Inmobiliaria.controllers;

import com.Egg.Inmobiliaria.models.Offer;
import com.Egg.Inmobiliaria.models.Property;
import com.Egg.Inmobiliaria.models.Usuario;
import com.Egg.Inmobiliaria.repositories.OfferRepository;
import com.Egg.Inmobiliaria.repositories.UserRepository;
import com.Egg.Inmobiliaria.services.OfferService;
import com.Egg.Inmobiliaria.services.PropertyService;
import com.Egg.Inmobiliaria.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.security.Principal;


import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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

    @Autowired
    private OfferRepository offerRep;
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

    @GetMapping("/profile/{idUser}")
    public String profile (HttpSession session, ModelMap modelo){

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        List<Property> propertyList = propertyService.getAllPropertiesByUserId(usuario.getId());

        modelo.addAttribute("inmuebles", propertyList);
        modelo.addAttribute("usuario", usuario );

        if (usuario.getRol().toString().equals("CLIENT")) {
            return "profileCliente.html";
        }else {
            return "profilePropietario.html";
        }
    }

    @GetMapping("/profile/editar/{id}")
    public String profileUpdate (@PathVariable Long id, ModelMap modelo){

        Usuario usuario = userRep.findByIdUser(id);

        modelo.addAttribute("usuario", usuario );


        return "usuarioPropietario_update.html";
    }

    @PostMapping("/profile/editar/{id}")
    public String profileUpdatePOST (ModelMap modelo, @PathVariable Long id, @RequestParam String name,
                @RequestParam String password,
                @RequestParam String password2,
                @RequestParam MultipartFile file) {
            try {
                userService.updateUser(id, name, password, password2, file);
                modelo.put("exito", "Usuario actualizado correctamente");
                return "profilePropietario.html";
            } catch (Exception e) {
                modelo.put("error", e.getMessage());
                return "usuarioPropietario_update.html";
            }
    }

    @GetMapping("/profile/client/{idClient}")
    public String profileClient (HttpSession session, ModelMap modelo, Long idClient){

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        List<Offer> offerList = offerRep.findOfferByUserId(idClient);
        List<Property> propertyLC = new ArrayList <Property>();

        for (Offer offer : offerList){
            propertyLC.add(propertyService.getPropertyById(offer.getProperty().getId()));
        }

        modelo.addAttribute("inmuebles", propertyLC);
        modelo.addAttribute("ofertas", offerList);
        modelo.addAttribute("usuario", usuario );


        return "profileCliente.html";

    }


}
