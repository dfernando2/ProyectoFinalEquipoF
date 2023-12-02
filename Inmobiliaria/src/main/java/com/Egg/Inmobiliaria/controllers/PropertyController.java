package com.Egg.Inmobiliaria.controllers;

import com.Egg.Inmobiliaria.enums.PropertyStatus;
import com.Egg.Inmobiliaria.enums.PropertyType;
import com.Egg.Inmobiliaria.exceptions.MiException;
import com.Egg.Inmobiliaria.models.ImageProperty;
import com.Egg.Inmobiliaria.models.Offer;
import com.Egg.Inmobiliaria.models.Property;
import com.Egg.Inmobiliaria.models.Usuario;
import com.Egg.Inmobiliaria.services.OfferService;
import com.Egg.Inmobiliaria.services.PropertyService;
import com.Egg.Inmobiliaria.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    @Autowired
    private OfferService offerService;

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
    public String register(@RequestParam(required = false) Long id,
            @RequestParam String address,
            @RequestParam String province,
            String location,
            @RequestParam(value = "surface", defaultValue = "0") Integer surface,
            @RequestParam(value = "bathrooms", defaultValue = "0") Integer bathrooms,
            @RequestParam(value = "bedrooms", defaultValue = "0") Integer bedrooms,
            @RequestParam(value = "price", defaultValue = "0") Double price,
            @RequestParam String description,
            @RequestParam PropertyStatus status,
            @RequestParam Date createDate,
            @RequestParam PropertyType type,
            MultipartFile file,
            ModelMap modelo) {
        /*
         * String address, String province, String location, Integer surface,
         * Integer bathrooms, Integer bedrooms, Double price, String description,
         * PropertyStatus status, Date createDate, PropertyType type,
         * MultipartFile file, String emailUsuario
         */
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String emailUser = auth.getName();
            propertyService.create(address, province, location, surface, bathrooms, bedrooms, price, description,
                    status, createDate, type, file, emailUser);

            modelo.put("exito", "La propiedad fue cargada correctamente! ");

        } catch (MiException ex) {
           List<Usuario> usuarios = userservice.listUser();
          modelo.addAttribute("usuarios", usuarios);
          modelo.put("error", ex.getMessage());
          return "property_form.html"; // volvemos a cargar el formulario.

        }
        return "redirect:/home";
        /*
 modelo.put("error", ex.getMessage());
            modelo.put("address", address);
            modelo.put("province", province);
            modelo.put("location", location);
            modelo.put("surface", surface);
            modelo.put("bathrooms", bathrooms);
            modelo.put("bedrooms", bedrooms);
            modelo.put("price", price);
            modelo.put("description", description);
            modelo.put("status", status);
            modelo.put("createDate", createDate);
            modelo.put("type", type);
            modelo.put("file", file);
            List<Usuario> usuarios = userservice.listUser();
            modelo.addAttribute("usuarios", usuarios);
            modelo.put("error", ex.getMessage());
            return "property_form.html";


         * try {
         * 
         * // modelo.addAttribute("propertyType", type);
         * // System.out.println(type);
         * // traer el usuario que esta logueado
         * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         * 
         * String emailUser = auth.getName();
         * 
         * propertyService.create(address, province, location, surface, bathrooms,
         * bedrooms, price, description, status, createDate,
         * type, file, emailUser);
         * 
         * modelo.put("exito", "La propiedad fue cargada correctamente! ");
         * 
         * } catch (Exception ex) {
         * 
         * List<Usuario> usuarios = userservice.listUser();
         * modelo.addAttribute("usuarios", usuarios);
         * modelo.put("error", ex.getMessage());
         * // return "property_form.html"; // volvemos a cargar el formulario.
         * }
         * return "redirect:/home";
         */
    }

    @GetMapping("/list")
    public String propertyList() {

        return "property_list.html";
    }

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
            @RequestParam(required = false) List<Offer> offers, @RequestParam(required = false) String idUser,
            @RequestParam boolean isRented, @RequestParam boolean isActive,
            MultipartFile file, ModelMap modelo) {

        List<Usuario> usuarios = userservice.listUser();

        modelo.addAttribute("usuarios", usuarios);

        propertyService.update(id, address, province, location, surface, bathrooms,
                bedrooms, price, description, status, createDate,
                type,file);
        return "redirect:../list";

    }

    @PostMapping("/filter")
    public String filterProperty(@RequestParam(required = false) String province,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) int bedrooms,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            MultipartFile file,
            ModelMap modelo) throws Exception {

        modelo.put("properties", propertyService.filteredProperties(province,
                status, type, bedrooms, minPrice, maxPrice, file));
        // modelo.addAttribute("image", image);

        return "home.html";
    }

    @GetMapping("/offer/{idUser}/{idProperty}")
    public String offer(@PathVariable Long idUser, @PathVariable Long idProperty, ModelMap modelo) {

        modelo.put("property", propertyService.getOne(idProperty));

        modelo.addAttribute("usuario", userservice.getOne(idUser));

        List<Property> properties = propertyService.getAllProperties();
        modelo.addAttribute("properties", properties);

        return "offer.html";
    }

    @Transactional
    @PostMapping("/offer/{idUser}/{idProperty}")
    public String offerTransaction(@RequestParam Long idUser, @RequestParam Long idProperty,
            @RequestParam Double price, @RequestParam Integer contact, ModelMap modelo) {

        offerService.createOffer(idProperty, idUser, price, contact);

        // modelo.put("offers", offerService.getAll(idProperty));
        List<Property> properties = propertyService.getAllProperties();

        return "properties";
    }
    // @GetMapping("/offer/transaction/{idUser}/{idProperty}")
    // public String offerCompleted(@PathVariable Long idUser, @PathVariable Long
    // idProperty, ModelMap modelo) {
    //
    // modelo.put("property", propertyService.getOne(idProperty));
    //
    // modelo.addAttribute("usuario", userservice.getOne(idUser));
    //
    // return "offer.html";
    // }

}
