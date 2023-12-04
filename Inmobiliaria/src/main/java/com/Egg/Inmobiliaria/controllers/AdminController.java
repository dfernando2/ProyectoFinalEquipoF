package com.Egg.Inmobiliaria.controllers;

import com.Egg.Inmobiliaria.enums.PropertyStatus;
import com.Egg.Inmobiliaria.enums.PropertyType;
import com.Egg.Inmobiliaria.exceptions.MiException;
import com.Egg.Inmobiliaria.models.Property;
import com.Egg.Inmobiliaria.models.Usuario;
import com.Egg.Inmobiliaria.repositories.PropertyRepository;
import com.Egg.Inmobiliaria.repositories.UserRepository;
import com.Egg.Inmobiliaria.services.PropertyService;
import com.Egg.Inmobiliaria.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class AdminController {
    @Autowired
    private UserService u;

    @Autowired
    private UserRepository ur;

    @Autowired
    private PropertyRepository pr;

    @Autowired
    private PropertyService ps;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/panel")
    public String panelAdministrativo() {

        return "dashboard.html";
    }


    @GetMapping("/usuario/inquilinos")
    public String inquilinos(HttpSession session, ModelMap modelo) {

        Usuario currentUser = (Usuario) session.getAttribute("usuariosession");

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
    public String modified(@RequestParam MultipartFile file,  @PathVariable Long id,
                           @RequestParam String email, @RequestParam String password,
                           @RequestParam String password2, @RequestParam String rol,
                           ModelMap modelo) {

        try {
            u.update(file, id, email, password, password2, rol);
            String mensaje = "El usuario fue modificado con exito";
            modelo.put("Exito", mensaje);
            return "dashboard.html";
        }catch (Exception e) {
            String mensaje = "El usuario no fue modificado";
            modelo.put("Error", mensaje);
            return "usuario_update.html";
        }

    }


    @GetMapping("/usuario/propietarios")
    public String propietarios(HttpSession session, ModelMap modelo) {

        Usuario currentUser = (Usuario) session.getAttribute("usuariosession");

        List<Usuario> propietariosList = ur.findAll();

        modelo.addAttribute("usuarios", propietariosList);

        return "propietarios.html";
    }

    @GetMapping("/inmuebles")
    public String inmuebles(HttpSession session, ModelMap modelo) {

        Usuario currentUser = (Usuario) session.getAttribute("usuariosession");

        List<Property> propertyList = pr.findAll();

        modelo.addAttribute("inmuebles", propertyList);
        modelo.addAttribute("usuariosession", currentUser );

        return "inmuebles.html";
    }

    @GetMapping("/inmuebles/editar/{id}")
    public String modifyProperty(@PathVariable Long id, ModelMap modelo) {

        modelo.put("inmueble", ps.getOne(id));

        return "inmueble_update.html";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }// realizar la vinculación de datos entre las solicitudes web y los objetos del
    // dominio, pueda manejar de manera personalizada el formato de las fechas, utilizando
    // "yyyy-MM-dd" como formato para las fechas de tipo Date.
    @Transactional
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
            ps.update(id, address, province, location, surface, bathrooms,
                    bedrooms, price, description, status, createDate,
                    type);
            modelo.put("Exito", "La propiedad se ha modificado correctamente");
        } catch (Exception e) {
            modelo.put("Error", e.getMessage());
            return "inmueble_update.html";
        }
        return "redirect:/dashboard/inmuebles";
    }

    @GetMapping("/usuario/remove/{id}")
    public String darDeBaja(@PathVariable Long id, ModelMap modelo){
        System.out.println(id);
        try {
            u.darDeBaja(id);
            modelo.put("Exito", "El usuariose ha eliminado correctamente");
            return "redirect:/dashboard/usuario/inquilinos";
        } catch (MiException ex) {
            modelo.put("Error", ex.getMessage());
            return "redirect:/dashboard/usuario/inquilinos";
        }

    }

    @Transactional
    @GetMapping("/inmuebles/remove/{id}")
    public String delete(@PathVariable Long id, ModelMap modelo){

        try {
            ps.remove(id);
            modelo.put("Exito", "La propiedad se ha eliminado correctamente");
            return "redirect:/dashboard/inmuebles";
        } catch (MiException ex) {
            modelo.put("Error", ex.getMessage());
            return "redirect:/dashboard/inmuebles";
        }

    }
}
