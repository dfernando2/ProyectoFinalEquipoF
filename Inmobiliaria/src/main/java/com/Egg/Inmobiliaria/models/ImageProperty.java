package com.Egg.Inmobiliaria.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ImageProperty {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String mime;

    private String name;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Property property;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] container;

    public ImageProperty() {
    }

    public ImageProperty(String id, String mime, String name, Property property, byte[] container) {
        this.id = id;
        this.mime = mime;
        this.name = name;
        this.property = property;
        this.container = container;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
