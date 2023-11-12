package com.Egg.Inmobiliaria.models;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


@Entity
public class ImageProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String mime;
    private String name;
    @ManyToOne
    private Property property;
    @Lob @Basic(fetch = FetchType.LAZY)
    private byte[] container;

    public ImageProperty() {
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

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public byte[] getContainer() {
        return container;
    }

    public void setContainer(byte[] container) {
        this.container = container;
    }
    
    
    
    
    
}
