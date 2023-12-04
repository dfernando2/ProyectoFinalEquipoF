package com.Egg.Inmobiliaria.controllers;

import com.Egg.Inmobiliaria.enums.PropertyStatus;
import com.Egg.Inmobiliaria.enums.PropertyType;
import com.Egg.Inmobiliaria.exceptions.MiException;
import com.Egg.Inmobiliaria.models.ImageProperty;
import com.Egg.Inmobiliaria.models.Offer;
import com.Egg.Inmobiliaria.models.Property;
import com.Egg.Inmobiliaria.models.Usuario;
import com.Egg.Inmobiliaria.repositories.PropertyRepository;
import com.Egg.Inmobiliaria.repositories.UserRepository;
import com.Egg.Inmobiliaria.services.ImagePropertyService;
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
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/property")
public class PropertyController {

    @Autowired
    PropertyService propertyService;
    @Autowired
    UserService userservice;
    @Autowired
    private OfferService offerService;
    @Autowired
    private ImagePropertyService imagePropertyService;
    @Autowired
    private PropertyRepository  propertyRepository;
    @Autowired
    private UserRepository userRepository;

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
//            return "property_form.html"; // volvemos a cargar el formulario.
        }
        return "redirect:/home";
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

        return "inmueble_update.html";
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
            @RequestParam boolean isRented, @RequestParam boolean isActive, ModelMap modelo) {

        List<Usuario> usuarios = userservice.listUser();

        modelo.addAttribute("usuarios", usuarios);

        propertyService.update(id, address, province, location, surface, bathrooms,
                bedrooms, price, description, status, createDate,
                type);
        return "redirect:../list";

    }

        @PostMapping("/filter")
    public String filterProperty(@RequestParam(required = false) String province,
                                 @RequestParam(required = false) String status,
                                 @RequestParam(required = false) String type,
                                 @RequestParam(required = false) int bedrooms,
                                 @RequestParam(required = false) Double minPrice,
                                 @RequestParam(required = false) Double maxPrice,
                                 ModelMap modelo) throws Exception {

        modelo.put("properties", propertyService.filteredProperties(province,
                status, type, bedrooms, minPrice, maxPrice));


        return "home.html";
    }

    @GetMapping("/offer/{idUser}/{idProperty}")
    public String offer(@PathVariable Long idUser, @PathVariable Long idProperty, ModelMap modelo) {

        modelo.put("property", propertyService.getOne(idProperty));

        modelo.addAttribute("usuario", userservice.getOne(idUser));

        return "offer.html";
    }

//    @Transactional
//    @PostMapping("/offer/{idUser}/{idProperty}")
//    public String offerTransaction(@PathVariable Long idUser, @PathVariable Long idProperty,
//                                   @RequestParam(required = false) Double price, @RequestParam(required = false) Integer contact, ModelMap modelo) {
//        Optional<Usuario> usuarioAnswer = userRepository.findById(idUser);
//
//        Usuario usuario = usuarioAnswer.get();
//
//        if(usuario.getRol().toString().equals("CLIENT")){
//            offerService.createOffer(idProperty, idUser, price, contact);
//            String mensaje = "La oferta se realizo con exito";
//            modelo.addAttribute("mensajeExito", mensaje);
//        }else if (usuario.getRol().toString().equals("ENTITY")){
//            String mensaje= "Solo los clientes pueden realizar esta accion";
//            modelo.addAttribute("mensajeError", mensaje);
//         }else {
//            String mensaje= "No se pudo cargar la oferta, pongase en contacto con el Staff de Mr House";
//            modelo.addAttribute("mensajeError", mensaje);
//        }
//        return "offer.html";
//    }


    @Transactional
    @PostMapping("/offer/{idUser}/{idProperty}")
    public String offerTransaction(@PathVariable Long idUser, @PathVariable Long idProperty,
                                   @RequestParam(required = false) Double price, @RequestParam(required = false) Integer contact, ModelMap modelo) {

        offerService.createOffer(idProperty, idUser, price, contact);
        List<Offer> offers = offerService.getAllOffersByUserId(idUser);
        modelo.put("inmuebles", propertyService.getOne(idUser));
        modelo.put("offers", offers);


        return "home.html";
    }

    @GetMapping("/offer/status/cliente/{idUser}")
    public String offerStatusClient(@PathVariable Long idUser, ModelMap modelo) {
        List<Offer> offers = offerService.getAllOffersByUserId(idUser);
        List<Property> inmueblesUser = new ArrayList<>();
        for (Offer offer: offers
             ) {
            inmueblesUser = propertyService.getAllPropertiesByUserId(offer.getUsuario().getId());
        }
        System.out.println(inmueblesUser);
        modelo.put("offers", offers);
        return "solicitudesCliente.html";
    }
    @GetMapping("/offer/status/propietario/{idUser}")
    public String offerStatusEntity(@PathVariable Long idUser, ModelMap modelo) {
        List<Property> inmueblesUser = propertyService.getAllPropertiesByUserId(idUser);
        List<Offer> offers = new ArrayList<>();
        for (Property  inmueble: inmueblesUser
             ) {
            offers = offerService.getAllOffersByProperty(inmueble.getId());
        }

        System.out.println(inmueblesUser);
        modelo.put("offers", offers);
        return "solicitudesPropietario.html";
    }


    @PostMapping("/offer/aceptar/{idOffer}")
    public String offerAccepted(ModelMap modelo, @PathVariable Long idOffer) {
        offerService.acceptOfferStatus(idOffer);
        return "home.html";
    }


    @PostMapping("/offer/rechazar/{idOffer}")
    public String offerRejected(ModelMap modelo, @PathVariable Long idOffer) {
        offerService.rejectOfferStatus(idOffer);
        return "home.html";
    }



    @GetMapping("/inmuebles/{idUser}")
    public String inmuebles(HttpSession session, ModelMap modelo) {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        List<Property> propertyList = propertyService.getAllPropertiesByUserId(usuario.getId());

        modelo.addAttribute("inmuebles", propertyList);
        modelo.addAttribute("usuario", usuario );

        return "inmueblesPropietario.html";
    }

    @GetMapping("/inmuebles/editar/{id}")
    public String modifyProperty(@PathVariable Long id, ModelMap modelo) {

        modelo.put("inmueble", propertyService.getOne(id));
        return "inmueblePropietario_update.html";
    }
    @PostMapping("/inmuebles/editar/{id}")
    public String modifiedProperty(@PathVariable Long id, @RequestParam(required = false) String address,
                                   @RequestParam(required = false) String province, @RequestParam(required = false) String location,
                                   @RequestParam(value = "surface", defaultValue = "0", required = false) Integer surface,
                                   @RequestParam(value = "bathrooms", defaultValue = "0", required = false) Integer bathrooms,
                                   @RequestParam(value = "bedrooms", defaultValue = "0", required = false) Integer bedrooms,
                                   @RequestParam(value = "price", defaultValue = "0", required = false) Double price,
                                   @RequestParam(required = false) String description, @RequestParam(required = false) PropertyStatus status,
                                   @RequestParam(required = false) Date createDate, @RequestParam(required = false) PropertyType type,
                                   ModelMap modelo) {

        try {
            propertyService.update(id, address, province, location, surface, bathrooms,
                    bedrooms, price, description, status, createDate,
                    type);
            modelo.put("Exito", "La propiedad se ha modificado correctamente");
            Usuario  usuario = propertyRepository.getById(id).getUser();
            modelo.put("inmuebles", propertyService.getAllPropertiesByUserId(usuario.getId()));
        } catch (Exception e) {
            System.out.println("no se pudo guargar la propiedad po: " + e.getMessage());
            modelo.put("Error", e.getMessage());
            return "inmueble_update.html";
        }
        System.out.println("Se pudo guargar la propiedad");
        return "inmueblesPropietario.html";
    }
    @Transactional
    @GetMapping("/inmuebles/remove/{id}")
    public String delete(@PathVariable Long id, ModelMap modelo){


        try {
            propertyService.remove(id);
            modelo.put("Exito", "La propiedad se ha eliminado correctamente");
            return "redirect:/property/inmuebles/" + id;
        } catch (MiException ex) {
            modelo.put("Error", ex.getMessage());
            return "redirect:/property/inmuebles/" + id;
        }

    }

}
