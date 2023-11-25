package com.Egg.Inmobiliaria.controllers;

import com.Egg.Inmobiliaria.enums.Role;
import com.Egg.Inmobiliaria.models.Usuario;
import com.Egg.Inmobiliaria.repositories.UserRepository;
import com.Egg.Inmobiliaria.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard")
public class AdminController {
    @Autowired
    private UserService u;

    @Autowired
    private UserRepository ur;
    
    @GetMapping("/panel")
    public String panelAdministrativo() {
    
        return "dashboard.html";
    }


    @GetMapping("/inquilinos")
    public String inquilinos(ModelMap modelo) {

        List<Usuario> inquilinoList = ur.findAll();

        modelo.addAttribute("inquilinos", inquilinoList);

        return "inquilinos.html";
    }

    @GetMapping("/propietarios")
    public String propietarios(ModelMap modelo) {
        
        List<Usuario> propietarioList = ur.findAll();

        modelo.addAttribute("propietarios", propietarioList);

        
        return "propietarios.html";
    }

    

}