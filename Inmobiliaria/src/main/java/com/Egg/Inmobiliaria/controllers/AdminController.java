package com.Egg.Inmobiliaria.controllers;

import com.Egg.Inmobiliaria.enums.Role;
import com.Egg.Inmobiliaria.exceptions.MiException;
import com.Egg.Inmobiliaria.models.Usuario;
import com.Egg.Inmobiliaria.repositories.UserRepository;
import com.Egg.Inmobiliaria.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

    @GetMapping("/usuario/editar/{id}")
    public String modify(@PathVariable Long id, ModelMap modelo) {

        modelo.put("usuario", u.getOne(id));

        return "usuario_update.html";
    }

    @Transactional
    @PostMapping("/usuario/editar/{id}")
    public String modified(@RequestParam MultipartFile file,  @PathVariable Long id, @RequestParam String email,
                         @RequestParam String password, @RequestParam String password2, @RequestParam String rol, ModelMap modelo) throws MiException {

        try {
            //check
            System.out.println("Esta tratando de modificar");
            //check
            u.update(file, id, email, password, password2, rol);
            modelo.put("Exito", "El usuario se ha modificado correctamente");
        } catch (MiException e) {
            modelo.put("Error", e.getMessage());
            return "usuario_update.html";
        }
        return "redirect:/dashboard/usuario/inquilinos";
    }


    @GetMapping("/usuario/propietarios")
    public String propietarios(ModelMap modelo) {

        List<Usuario> propietariosList = ur.findAll();

        modelo.addAttribute("usuarios", propietariosList);

        return "propietarios.html";
    }

}