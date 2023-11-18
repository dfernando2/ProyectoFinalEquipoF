/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Egg.Inmobiliaria.controllers;

import com.Egg.Inmobiliaria.enums.PropertyStatus;
import com.Egg.Inmobiliaria.enums.PropertyType;
import com.Egg.Inmobiliaria.exceptions.MiException;
import com.Egg.Inmobiliaria.models.Property;
import com.Egg.Inmobiliaria.models.Usuario;
import com.Egg.Inmobiliaria.repositories.UserRepository;
import com.Egg.Inmobiliaria.services.PropertyService;
import com.Egg.Inmobiliaria.services.UserService;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author rober
 */
@Controller
@RequestMapping("/Admin")
public class AdminController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/dashboard")
    public String panelAdministrativo(){
        return "panel.html";
    }
    
    @GetMapping("/users")
    public String Listar(ModelMap modelo){
        List<Usuario> users = userService.listUser();
        
        modelo.addAttribute("Usuarios", users);
        
        return "user_list.html";
    }
    
    @GetMapping("/rol_modify/{id}")
    public String cambiaRol(@PathVariable Long id){
        userService.changeRol(id);
        
        return "redirect:/Admin/users";
    }
}
