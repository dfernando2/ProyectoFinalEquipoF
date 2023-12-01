package com.Egg.Inmobiliaria.models;

import com.Egg.Inmobiliaria.enums.PropertyStatus;
import com.Egg.Inmobiliaria.enums.PropertyType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String address;
    private String province;
    private String location;
    private Integer surface;
    private Integer bathrooms;
    private Integer bedrooms;
    private Double price;
    private String description;
    @Enumerated(EnumType.STRING)
    private PropertyStatus status;
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @Enumerated(EnumType.STRING)
    private PropertyType type;
    @OneToMany
    private List<Offer> offers;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Usuario usuario;
    private boolean isRented;
    private boolean isActive;

    public Property() {
    }

    public Property(Long id, String address, String province, String location, Integer surface,
                    Integer bathrooms, Integer bedrooms, Double price, String description,
                    PropertyStatus status, Date createDate, PropertyType type,
                    List<Offer> offers, Usuario usuario, boolean isRented, boolean isActive) {

        this.id = id;
        this.address = address;
        this.province = province;
        this.location = location;
        this.surface = surface;
        this.bathrooms = bathrooms;
        this.bedrooms = bedrooms;
        this.price = price;
        this.description = description;
        this.status = status;
        this.createDate = createDate;
        this.type = type;
        this.offers = offers;
        this.usuario = usuario;
        this.isRented = isRented;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getSurface() {
        return surface;
    }

    public void setSurface(Integer surface) {
        this.surface = surface;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PropertyStatus getStatus() {
        return status;
    }

    public void setStatus(PropertyStatus status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public Usuario getUser() {
        return usuario;
    }

    public void setUser(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
