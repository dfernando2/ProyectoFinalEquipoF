package com.Egg.Inmobiliaria.controllers;

import com.Egg.Inmobiliaria.enums.Role;
import com.Egg.Inmobiliaria.exceptions.MiException;
import com.Egg.Inmobiliaria.models.Usuario;
import com.Egg.Inmobiliaria.services.UserService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("")
public class InmobiliariaControlador {

    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String navbar() {
        return "home.html";
    }
    
    @GetMapping("/register")
    public String registro(){
        return "register.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String name, @RequestParam String email, @RequestParam Long dni,
            @RequestParam String password, @RequestParam String password2,
            ModelMap modelo, MultipartFile archive){
        try {
            userService.create(archive, name, email, dni, password, password2);
            modelo.put("Exito", "Usuario cargado correctamente");
            
            return "index.html";
        } catch (MiException ex) {
            modelo.put("ERROR", ex.getMessage());
            modelo.put("Nombre", name);
            modelo.put("Email", email);
            
            return "register.html";
        }
    }
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap model){

        if(error != null){
            model.put("error", "bad credentials");
        }

        return "login";
    }

    @PreAuthorize("hasAnyRole('ROLE_ENTITY', 'ROLE_ADMIN', 'ROLE_CLIENT', 'ROLE_BOTHROLE')")
    @GetMapping("/home")
    public String home(HttpSession session){

        Usuario currentUser = (Usuario) session.getAttribute("usuariosession");

        if(currentUser.getRol().toString().equals("ADMIN")){
            return "home"; // ACA IRIA LA VISTA DASHBOARD
        }else{
            return "home";
        }
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ENTITY', 'ROLE_ADMIN', 'ROLE_CLIENT', 'ROLE_BOTHROLE')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session){
        Usuario user = (Usuario) session.getAttribute("userSession");
        
        modelo.put("Usuario", user);
        
        return "user_modify.html";
    }
    
      @PreAuthorize("hasAnyRole('ROLE_ENTITY', 'ROLE_ADMIN', 'ROLE_CLIENT', 'ROLE_BOTHROLE')")
      @PostMapping("/perfil/{id}")
      public String update(MultipartFile archivo, @PathVariable Long id, @RequestParam String name, @RequestParam String email,
              @RequestParam Long dni, @RequestParam String password, @RequestParam String password2, ModelMap modelo){
          
        try {
            userService.update(archivo, id, dni, name, email, password, password2);
            
            modelo.put("Exito", "Usuario actualizado correctamente");
            
            return "home.html";
        } catch (MiException ex) {
            modelo.put("ERROR", ex.getMessage());
            
            modelo.put("Nombre", name);
            modelo.put("Email", email);
            
            return "user_modify.html";
        }
          
      }
    
    
}
