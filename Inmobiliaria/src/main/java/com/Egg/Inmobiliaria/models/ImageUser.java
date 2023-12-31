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
    private String name;
    @Lob @Basic(fetch = FetchType.LAZY)
    private byte[] container;
  
    public ImageUser() {
    }

     public ImageUser(String id, String mime, String name, byte[] container) {
         this.id = id;
         this.mime = mime;
         this.name = name;
         this.container = container;
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

    public String getName() {
        return name;

    }
    public void setName(String name) {
        this.name = name;

    }
    public byte[] getContainer() {
        return container;

    }
    public void setContainer(byte[] container) {
        this.container = container;
    }
}