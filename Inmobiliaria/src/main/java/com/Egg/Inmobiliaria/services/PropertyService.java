package com.Egg.Inmobiliaria.services;

import com.Egg.Inmobiliaria.enums.PropertyStatus;
import com.Egg.Inmobiliaria.enums.PropertyType;
import com.Egg.Inmobiliaria.models.ImageProperty;
import com.Egg.Inmobiliaria.models.Offer;
import com.Egg.Inmobiliaria.models.Property;
import com.Egg.Inmobiliaria.models.User;
import com.Egg.Inmobiliaria.repositories.PropertyRepository;
import com.Egg.Inmobiliaria.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public void createProperty(String address, String province, String location, Integer surface,
                               Integer bathrooms, Integer bedrooms, Double price, String description,
                               PropertyStatus status, LocalDate createDate, PropertyType type, List<ImageProperty> images,
                               List<Offer> offers, String idUser, boolean isRented, boolean isActive){

        Optional <Property> propertyResponse = propertyRepository.findById(id);
        Optional<User> userResponse = userRepository.findById(idUser);

        User user = new User();

        if(userResponse.isPresent()){

            user = userResponse.get();
        }



        Property property = new Property();
        property.setId(id);
        property.setAddress(address);
        property.setProvince(province);
        property.setLocation(location);
        property.setSurface(surface);
        property.setBathrooms(bathrooms);
        property.setBedrooms(bedrooms);
        property.setPrice(price);
        property.setDescription(description);
        property.setStatus(status);
        property.setCreateDate(createDate);
        property.setType(type);
        property.setImages(images);
        property.setOffers(offers);
        property.setUser(user);
        property.setRented(isRented);
        property.setActive(isActive);

        propertyRepository.save(property);


    }


    public List<Property> listProperty() {
        List<Property> properties = new ArrayList();
        properties = propertyRepository.findAll();
        return properties;
    }

    @Transactional
    public void updateProperty(Long id, String address, String province, String location, Integer surface,
                               Integer bathrooms, Integer bedrooms, Double price, String description,
                               PropertyStatus status, LocalDate createDate, PropertyType type, List<ImageProperty> images,
                               List<Offer> offers, String idUser, boolean isRented, boolean isActive) {

        Optional<Property> propertyResponse = propertyRepository.findById(id);
        Optional<User> userResponse = userRepository.findById(idUser);

        User user = new User();

        if (userResponse.isPresent()) {

            user = userResponse.get();
        }

        if (propertyResponse.isPresent()) {

            Property property = propertyResponse.get();
            property.setAddress(address);
            property.setProvince(province);
            property.setLocation(location);
            property.setSurface(surface);
            property.setBathrooms(bathrooms);
            property.setBedrooms(bedrooms);
            property.setPrice(price);
            property.setDescription(description);
            property.setStatus(status);
            property.setCreateDate(createDate);
            property.setType(type);
            property.setImages(images);
            property.setOffers(offers);
            property.setUser(user);
            property.setRented(isRented);
            property.setActive(isActive);

            propertyRepository.save(property);

        }
    }

    public Property getOne(Long id){
        return propertyRepository.getOne(id);
    }

    @Transactional
    public void removeProperty() {
        Optional<Property> propertyResponse = propertyRepository.findById(id);
        if (propertyResponse.isPresent()) {
            Property property = propertyResponse.get();
            property.setActive(false);
            propertyRepository.save(property);
        }
    }

    public void findByType(PropertyType type) {
        List<Property> properties = propertyRepository.findByType(type);

    }

}
