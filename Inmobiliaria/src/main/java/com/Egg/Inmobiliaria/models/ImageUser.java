package com.Egg.Inmobiliaria.models;

 

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

 @Entity
public class ImageUser {
     @Id
     @GeneratedValue(generator = "uuid")
     @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String mime;
    private String nombre;
    @Lob @Basic(fetch = FetchType.LAZY)
    private byte[] contenido;
    public ImageUser() {
    }

     public ImageUser(String id, String mime, String nombre, byte[] contenido) {
         this.id = id;
         this.mime = mime;
         this.nombre = nombre;
         this.contenido = contenido;
     }

     public String getId() {
        return id;
    }
    public void setId(String id) {

        this.id = id;

    }
    public String getMime() {

        return mime;

    }
    public void setMime(String mime) {

        this.mime = mime;

    }

    public String getNombre() {

        return nombre;

    }
    public void setNombre(String nombre) {

        this.nombre = nombre;

    }
    public byte[] getContenido() {

        return contenido;

    }
    public void setContenido(byte[] contenido) {

        this.contenido = contenido;

    }
}