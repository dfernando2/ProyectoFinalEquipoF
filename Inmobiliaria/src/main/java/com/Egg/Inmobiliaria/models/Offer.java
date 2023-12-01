package com.Egg.Inmobiliaria.models;
import java.util.Date;
import javax.persistence.*;

import com.Egg.Inmobiliaria.enums.OfferStatus;

@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @ManyToOne
    private Property property;
    @ManyToOne
    private Usuario usuario;
    private Double price;

    private Integer contact;
    private OfferStatus offerStatus;

    public Offer() {
    }

    public Offer(Long id, Date creationDate, Property property, Usuario usuario, Double price,
                 Integer contact, OfferStatus offerStatus) {
        this.id = id;
        this.creationDate = creationDate;
        this.property = property;
        this.usuario = usuario;
        this.price = price;
        this.contact = contact;
        this.offerStatus = offerStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getContact() {
        return contact;
    }

    public void setContact(Integer contact) {
        this.contact = contact;
    }

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }
}
