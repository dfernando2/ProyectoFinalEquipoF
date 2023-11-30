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
import java.util.stream.Collectors;

@Service
public class PropertyService {

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImagePropertyService imagePropertyService;

    public Property getLast() {
        List<Property> properties = new ArrayList();
        properties = propertyRepository.findAll();
        return properties.get(properties.size()-1);
    }


    @Transactional
    public void create(String address, String province, String location, Integer surface,
                       Integer bathrooms, Integer bedrooms, Double price, String description,
                       PropertyStatus status, Date createDate, PropertyType type,
                       List<MultipartFile> files, String emailUsuario) {

        //falta validar

        Optional<Usuario> userAnswer = Optional.ofNullable(userRepository.findByEmail(emailUsuario));

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
        property.setUser(userAnswer.get());
        propertyRepository.save(property);

        for (MultipartFile file : files) {
            try {
                imagePropertyService.create(file, property);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public Property getOne(Long id) {
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
                       PropertyStatus status, Date createDate, PropertyType type, List<MultipartFile> files) {

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
//            property.setOffers(offers);
//            property.setRented(false);
//            property.setActive(true);
            propertyRepository.save(property);

            for (MultipartFile file : files) {
                try {
                    imagePropertyService.create(file, property);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            //Check
            System.out.println("Se estan guardando los cambios");
            //check
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
    public List<Property> getAllProperties() {
      
        return propertyRepository.findAll();
    }

     public List<Property> filteredProperties(String status, String type, String bedrooms, Double minPrice, Double maxPrice, String province) throws MiException {
        //TODO tiene sout para comprobar el if de cada variable para el filtrado
        try {
            List<Property> properties = propertyRepository.findAll();
            System.out.println("Lista completa: " + properties.toString());
            if (!province.isEmpty()) {
                List<Property> list = new ArrayList<>();
                for (Property p : properties) {
                    if (p.getProvince().equals(province)) {
                        list.add(p);
                    }
                }
                properties = list;
                System.out.println("Por provincia:" + properties.toString());
            }

            if (!status.isEmpty()) {
                List<Property> list = new ArrayList<>();
                for (Property p : properties) {
                    if (p.getStatus().toString().equalsIgnoreCase(status)) {
                        list.add(p);
                    }
                }
                properties = list;
                System.out.println("Por estado:" + properties.toString());
            }

            if (!type.isEmpty()) {
                List<Property> list = new ArrayList<>();
                for (Property p : properties) {
                    if (p.getType() == PropertyType.valueOf(type)) {
                        list.add(p);
                    }
                }
                properties = list;
                System.out.println("Por tipo:" + properties.toString());
            }


            if (minPrice != null || maxPrice != null) {
                properties = priceFilter(properties, minPrice, maxPrice);
                System.out.println("Por precio:" + properties.toString());
            }

            System.out.println("Final:" + properties.toString());
            return properties;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    private List<Property> priceFilter(List<Property> properties2, Double minPrice, Double maxPrice) {
        if (minPrice != null && maxPrice != null) {
            List<Property> list = new ArrayList<>();
            for (Property p : properties2) {
                if (p.getPrice() >= minPrice && p.getPrice() <= maxPrice) {
                    list.add(p);
                }
            }
            return list;
        } else if (minPrice != null) {
            List<Property> list = new ArrayList<>();
            for (Property p : properties2) {
                if (p.getPrice() >= minPrice) {
                    list.add(p);
                }
            }
            return list;
        } else if (maxPrice != null) {
            List<Property> list = new ArrayList<>();
            for (Property p : properties2) {
                if (p.getPrice() <= maxPrice) {
                    list.add(p);
                }
            }
            System.out.println("Final:" + properties2.toString());
            return list;
        } else {
            return properties2;
        }

    }

}
