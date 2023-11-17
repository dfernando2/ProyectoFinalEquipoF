package com.Egg.Inmobiliaria.services;

import com.Egg.Inmobiliaria.enums.PropertyStatus;
import com.Egg.Inmobiliaria.enums.PropertyType;
import com.Egg.Inmobiliaria.models.ImageProperty;
import com.Egg.Inmobiliaria.models.Offer;
import com.Egg.Inmobiliaria.models.Property;
import com.Egg.Inmobiliaria.models.Usuario;
import com.Egg.Inmobiliaria.repositories.ImagePropertyRepository;
import com.Egg.Inmobiliaria.repositories.PropertyRepository;
import com.Egg.Inmobiliaria.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImagePropertyService imagePropertyService;


    @Transactional
    public void create(String address, String province, String location, Integer surface,
                       Integer bathrooms, Integer bedrooms, Double price, String description,
                       PropertyStatus status, Date createDate, PropertyType type,
                       List<MultipartFile> files) {

        //falta validar

        Property property = new Property();
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
        property.setRented(false);
        property.setActive(true);
        propertyRepository.save(property);

        for (MultipartFile file : files) {
            try {
                imagePropertyService.create(file, property);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public Property getOne(Long id){
        return propertyRepository.getOne(id);
    }
    public List<Property> list() {
        List<Property> properties = new ArrayList();
        properties = propertyRepository.findAll();
        return properties;
    }

    @Transactional
    public void update(Long id, String address, String province, String location, Integer surface,
                       Integer bathrooms, Integer bedrooms, Double price, String description,
                       PropertyStatus status, Date createDate, PropertyType type, List<MultipartFile> images,
                       List<Offer> offers, String idUser, boolean isRented, boolean isActive) {

        Optional<Property> propertyAnswer = propertyRepository.findById(id);

        if (propertyAnswer.isPresent()) {

            Property property = propertyAnswer.get();
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
            property.setOffers(offers);
            property.setRented(isRented);
            property.setActive(isActive);
            propertyRepository.save(property);

        }
    }

    @Transactional
    public void remove(Long id) {
        Optional<Property> propertyAnswer = propertyRepository.findById(id);
        if (propertyAnswer.isPresent()) {
            Property property = propertyAnswer.get();
            property.setActive(false);
            propertyRepository.save(property);
        }
    }

    public void findByType(PropertyType type) {
        List<Property> properties = propertyRepository.findByType(type);
    }

}
