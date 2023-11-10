package com.Egg.Inmobiliaria.models;

import com.Egg.Inmobiliaria.enums.PropertyStatus;
import com.Egg.Inmobiliaria.enums.PropertyType;

import javax.persistence.*;
import java.time.LocalDate;
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
    private PropertyStatus status;
    @Temporal(TemporalType.DATE)
    private LocalDate createDate;
    private PropertyType type;
    @OneToMany
    private List<ImageProperty> images;
    @OneToMany
    private List<Offer> offers;
    @ManyToOne
    private User user;
    private boolean isRented;
    private boolean isActive;

    public Property() {
    }

    public Property(Long id, String address, String province, String location, Integer surface,
                    Integer bathrooms, Integer bedrooms, Double price, String description,
                    PropertyStatus status, LocalDate createDate, PropertyType type, List<ImageProperty> images,
                    List<Offer> offers, User user, boolean isRented, boolean isActive) {
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
        this.images = images;
        this.offers = offers;
        this.user = user;
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

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }

    public List<ImageProperty> getImages() {
        return images;
    }

    public void setImages(List<ImageProperty> images) {
        this.images = images;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
