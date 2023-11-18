package com.Egg.Inmobiliaria.models;

import com.Egg.Inmobiliaria.enums.Role;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String password;
    private Long dni;
    
    @OneToOne
    private ImageUser image;
    
    @Enumerated(EnumType.STRING)
    private Role rol;
    
    @OneToMany
    private List<Property> UserProperties;
    
    public Usuario() {
    }

    public Usuario(Long id, String name, String email,
                String password, Long dni, ImageUser image, Role rol) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.dni = dni;
        this.image = image;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public ImageUser getImage() {
        return image;
    }

    public void setImage(ImageUser image) {
        this.image = image;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public Role getRol() {
        return rol;
    }

    public void setRol(Role rol) {
        this.rol = rol;
    }

}
