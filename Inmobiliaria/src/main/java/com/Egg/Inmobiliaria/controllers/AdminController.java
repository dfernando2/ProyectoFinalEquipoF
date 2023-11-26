package com.Egg.Inmobiliaria.controllers;

import com.Egg.Inmobiliaria.enums.Role;
import com.Egg.Inmobiliaria.exceptions.MiException;
import com.Egg.Inmobiliaria.models.Usuario;
import com.Egg.Inmobiliaria.repositories.UserRepository;
import com.Egg.Inmobiliaria.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/panel")
    public String panelAdministrativo() {

        return "dashboard.html";
    }


    @GetMapping("/usuario/inquilinos")
    public String inquilinos(ModelMap modelo) {

        List<Usuario> inquilinoList = ur.findAll();

        modelo.addAttribute("usuarios", inquilinoList);

        return "inquilinos.html";
    }

    @GetMapping("/usuario/editar/{dni}")
    public String editar (@RequestParam String dni, ModelMap modelo) {

        modelo.put("usuario", u.getOneDni(dni));

        return "usuario_update.html";
    }


    @PostMapping("/usuario/editar/{dni}")
    public String editar2 (@PathVariable(required = false) MultipartFile file,  Long id, String dni, String name, String email,
                           String password, String password2, String rol, ModelMap modelo) {
        try {
            u.update(file, id, dni, name, email, password, password2, rol);
            modelo.put("Exito", "El usuario se ha modificado correctamente");
        } catch (MiException e) {
            modelo.put("Error", e.getMessage());
            return "usuario_update.html";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "dashboard.html";
    }

    @GetMapping("/usuarios/propietarios")
    public String propietarios(ModelMap modelo) {

        List<Usuario> propietariosList = ur.findAll();

        modelo.addAttribute("usuarios", propietariosList);

        return "propietarios.html";
    }

//    @GetMapping("/usuario/remove")
//    public String bajaUsuario (@PathVariable(required = false) String dni, ModelMap modelo) {
//
//        modelo.put("inquilino", u.getOneDni(dni));
//
//        return "usuario_update.html";
//    }
//
//    @PostMapping("/usuario/remove")
//    public String bajUsuario2 (@PathVariable(required = false) MultipartFile file, Long id, String dni, String name, String email,
//                          String password, String password2, String rol, ModelMap modelo) {
//        try {
//            u.update(file, id, dni, name, email, password, password2, rol);
//            modelo.put("Exito", "El usuario se ha modificado correctamente");
//        } catch (MiException e) {
//            modelo.put("Error", e.getMessage());
//            return "usuario_update.html";
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return "dashboard.html";
//    }





}