package com.Egg.Inmobiliaria.controllers;

import com.Egg.Inmobiliaria.enums.PropertyStatus;
import com.Egg.Inmobiliaria.enums.PropertyType;
import com.Egg.Inmobiliaria.exceptions.MiException;
import com.Egg.Inmobiliaria.models.ImageProperty;
import com.Egg.Inmobiliaria.models.Offer;
import com.Egg.Inmobiliaria.models.Property;
import com.Egg.Inmobiliaria.models.User;
import com.Egg.Inmobiliaria.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/property")
public class PropertyController {

    @Autowired
    PropertyService propertyServicio;
    @Autowired
    UserService userservice;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }// realizar la vinculación de datos entre las solicitudes web y los objetos del dominio,
    // pueda manejar de manera personalizada el formato de las fechas, utilizando "yyyy-MM-dd"
    // como formato para las fechas de tipo Date.

    @GetMapping("/record") //localhost:8080/property/record
    public String record(ModelMap modelo) {

        List<User> user = userservice.listUser();

        modelo.addAttribute("users", users);

        return "property_form.html";
    }

    @PostMapping("/register")//localhost:8080/property/register
    public String register(@RequestParam(required = false) Long id, @RequestParam String address, @RequestParam String province,
                           String location, @RequestParam(value = "surface", defaultValue = "0") Integer surface,
                           @RequestParam(value = "bathrooms", defaultValue = "0")Integer bathrooms,
                           @RequestParam(value = "bedrooms", defaultValue = "0")Integer bedrooms,
                           @RequestParam(value = "price", defaultValue = "0")Double price,
                           @RequestParam String description, @RequestParam PropertyStatus status,
                           @RequestParam LocalDate createDate, @RequestParam PropertyType type,
                           @RequestParam(required=false)List<ImageProperty> images,
                           @RequestParam(required=false) List<Offer> offers, @RequestParam (required=false) String idUser,
                           @RequestParam boolean isRented, @RequestParam boolean isActive, ModelMap modelo) {
        try {

//            modelo.addAttribute("propertyType", type);
//            System.out.println(type);

            propertyServicio.createProperty(address, province, location, surface, bathrooms,
                            bedrooms, price, description, status, createDate,
                            type, images, offers, idUser, isRented, isActive);

            modelo.put("exito", "La propiedad fue cargada correctamente!");

        } catch (MiException ex) {
            List<User> users = userservice.listUser();
            modelo.addAttribute("users", users);
            modelo.put("error", ex.getMessage());
            return "property_form.html";  // volvemos a cargar el formulario.
        }
        return "index.html";
    }

    @GetMapping("/list")//localhost:8080/property/list
    public String list(ModelMap modelo) {
        List<Property> properties = propertyServicio.listProperty();
        modelo.addAttribute("properties", properties);

        return "property_list";
    }

    @GetMapping("/update/{id}")//localhost:8080/property/list/{id}
    public String modify(@PathVariable Long id, ModelMap modelo) {

        modelo.put("property", propertyServicio.getOne(id));

        List<User> users = userservice.listUser();

        modelo.addAttribute("users", users);

        return "property_modify.html";
    }
    @PostMapping("/update/{id}")//localhost:8080/property/list/{id}
    public String modify(@RequestParam(required = false) Long id, @RequestParam String address, @RequestParam String province,
                         String location, @RequestParam(value = "surface", defaultValue = "0") Integer surface,
                         @RequestParam(value = "bathrooms", defaultValue = "0")Integer bathrooms,
                         @RequestParam(value = "bedrooms", defaultValue = "0")Integer bedrooms,
                         @RequestParam(value = "price", defaultValue = "0")Double price,
                         @RequestParam String description, @RequestParam PropertyStatus status,
                         @RequestParam LocalDate createDate, @RequestParam PropertyType type,
                         @RequestParam(required=false)List<ImageProperty> images,
                         @RequestParam(required=false) List<Offer> offers, @RequestParam (required=false) String idUser,
                         @RequestParam boolean isRented, @RequestParam boolean isActive, ModelMap modelo) {
        try {
            List<User> users = userservice.listUser();

            modelo.addAttribute("users", users);

            propertyServicio.updateProperty(id, address, province, location, surface, bathrooms,
                    bedrooms, price, description, status, createDate,
                    type, images, offers, idUser, isRented, isActive);

            return "redirect:../list";

        } catch (MiException ex) {
            List<User> users = userservice.listUser();

            modelo.put("error", ex.getMessage());

            modelo.addAttribute("users", users);

            return "property_modify.html";
        }
    }
}
