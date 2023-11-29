package com.Egg.Inmobiliaria.controllers;

import com.Egg.Inmobiliaria.enums.PropertyStatus;
import com.Egg.Inmobiliaria.enums.PropertyType;
import com.Egg.Inmobiliaria.models.ImageProperty;
import com.Egg.Inmobiliaria.models.Offer;
import com.Egg.Inmobiliaria.models.Property;
import com.Egg.Inmobiliaria.models.Usuario;
import com.Egg.Inmobiliaria.services.PropertyService;
import com.Egg.Inmobiliaria.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/property")
public class PropertyController {

    @Autowired
    PropertyService propertyService;
    @Autowired
    UserService userservice;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }// realizar la vinculación de datos entre las solicitudes web y los objetos del
    // dominio,
    // pueda manejar de manera personalizada el formato de las fechas, utilizando
    // "yyyy-MM-dd"
    // como formato para las fechas de tipo Date.


    @GetMapping("/record") // localhost:8080/property/record
    public String record(ModelMap modelo) {

        List<Usuario> users = userservice.listUser();

        modelo.addAttribute("users", users);

        return "property_form.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_BOTHROLE', 'ROLE_ADMIN', 'ROLE_ENTITY')")
    @PostMapping("/register") // localhost:8080/property/register
    public String register(@RequestParam(required = false) Long id, @RequestParam String address,
            @RequestParam String province,
            String location, @RequestParam(value = "surface", defaultValue = "0") Integer surface,
            @RequestParam(value = "bathrooms", defaultValue = "0") Integer bathrooms,
            @RequestParam(value = "bedrooms", defaultValue = "0") Integer bedrooms,
            @RequestParam(value = "price", defaultValue = "0") Double price,
            @RequestParam String description, @RequestParam PropertyStatus status,
            @RequestParam Date createDate, @RequestParam PropertyType type, List<MultipartFile> files,
            ModelMap modelo) {
        try {

            // modelo.addAttribute("propertyType", type);
            // System.out.println(type);
            // traer el usuario que esta logueado
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            String emailUser = auth.getName();

            propertyService.create(address, province, location, surface, bathrooms,
                    bedrooms, price, description, status, createDate,
                    type, files, emailUser);

            modelo.put("exito", "La propiedad fue cargada correctamente! ");

        } catch (Exception ex) {

            List<Usuario> usuarios = userservice.listUser();
            modelo.addAttribute("usuarios", usuarios);
            modelo.put("error", ex.getMessage());
            return "property_form.html"; // volvemos a cargar el formulario.
        }
        return "home.html";
    }

    @GetMapping("/list")
    public String propertyList() {

        return "property_list.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_BOTHROLE', 'ROLE_ADMIN', 'ROLE_ENTITY')")
    @PostMapping("/list")
    public String propertyList(ModelMap model) {
        model.put("properties", propertyService.list());
        return "property_list.html";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENTITY', 'BOTHROLE')")
    @GetMapping("/update/{id}") // localhost:8080/property/list/{id}
    public String modify(@PathVariable Long id, ModelMap modelo) {

        modelo.put("property", propertyService.getOne(id));

        List<Usuario> usuarios = userservice.listUser();

        modelo.addAttribute("usuarios", usuarios);

        return "property_modify.html";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ENTITY', 'BOTHROLE')")
    @PostMapping("/update/{id}") // localhost:8080/property/list/{id}
    public String modify(@RequestParam(required = false) Long id, @RequestParam String address,
            @RequestParam String province,
            String location, @RequestParam(value = "surface", defaultValue = "0") Integer surface,
            @RequestParam(value = "bathrooms", defaultValue = "0") Integer bathrooms,
            @RequestParam(value = "bedrooms", defaultValue = "0") Integer bedrooms,
            @RequestParam(value = "price", defaultValue = "0") Double price,
            @RequestParam String description, @RequestParam PropertyStatus status,
            @RequestParam Date createDate, @RequestParam PropertyType type,
            @RequestParam(required = false) List<MultipartFile> images,
            @RequestParam(required = false) List<Offer> offers, @RequestParam(required = false) String idUser,
            @RequestParam boolean isRented, @RequestParam boolean isActive, ModelMap modelo) {

        List<Usuario> usuarios = userservice.listUser();

        modelo.addAttribute("usuarios", usuarios);

        propertyService.update(id, address, province, location, surface, bathrooms,
                bedrooms, price, description, status, createDate,
                type, images);


        return "redirect:../list";

    }

    @PostMapping("/filter")
    public void filterProperty(@RequestParam(required = false) String province,
                                 @RequestParam(required = false) String status,
                                 @RequestParam(required = false) String type,
                                 @RequestParam(required = false) String bedrooms,
                                 @RequestParam(required = false) Double minPrice,
                                 @RequestParam(required = false) Double maxPrice,
                                 ModelMap model) {
        model.put("properties", propertyService.filteredProperties(status, type, bedrooms, minPrice, maxPrice, province));

    }


}
