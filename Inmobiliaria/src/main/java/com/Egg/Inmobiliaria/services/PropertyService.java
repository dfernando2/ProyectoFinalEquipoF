package com.Egg.Inmobiliaria.services;

import com.Egg.Inmobiliaria.enums.PropertyStatus;
import com.Egg.Inmobiliaria.enums.PropertyType;
import com.Egg.Inmobiliaria.exceptions.MiException;
import com.Egg.Inmobiliaria.models.ImageProperty;
import com.Egg.Inmobiliaria.models.Property;
import com.Egg.Inmobiliaria.models.Usuario;
import com.Egg.Inmobiliaria.repositories.ImagePropertyRepository;
import com.Egg.Inmobiliaria.repositories.PropertyRepository;
import com.Egg.Inmobiliaria.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service
public class PropertyService {

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImagePropertyService imagePropertyService;

    @Autowired
    ImagePropertyRepository imagePropertyRepository;

    public Property getLast() {
        List<Property> properties = new ArrayList();
        properties = propertyRepository.findAll();
        return properties.get(properties.size() - 1);
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

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public List<Property> list() {
        List<Property> properties = new ArrayList();
        properties = propertyRepository.findAll();
        return properties;
    }

    public List<Property> getAllPropertiesByUserId(Long idUser) {
        return propertyRepository.findByUserId(idUser);
    }

    @Transactional
    public void update(Long id, String address, String province, String location, Integer surface,
                       Integer bathrooms, Integer bedrooms, Double price, String description,
                       PropertyStatus status, Date createDate, PropertyType type) {

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
        }
    }


    @Transactional
    public void remove(Long id) throws MiException {
        Optional<Property> propertyAnswer = propertyRepository.findById(id);

        if(propertyAnswer !=  null){
            Property property = propertyAnswer.get();
            // Eliminar todas las ImageProperty asociadas
            ImageProperty imageProperty = imagePropertyRepository.findByProperty(property);
            imagePropertyRepository.delete(imageProperty);
            // Eliminar la Property después de eliminar las ImageProperty
            propertyRepository.delete(property);
        }else{
            throw new MiException("No se encontró la propiedad con el id " + id);
        }
    }

    public List<Property> filteredProperties(String province, String status, String type, int bedrooms, Double
            minPrice, Double maxPrice) throws Exception {

        List<Property> properties = propertyRepository.findAll();
        System.out.println("Lista completa: " + properties.toString());

        try {
            if (!province.equals("null")) {
                List<Property> list1 = new ArrayList<>();
                for (Property p : properties) {
                    if (p.getProvince().equalsIgnoreCase(province)) {
                        list1.add(p);
                    }
                }
                properties = list1;
                System.out.println("Por provincia:" + properties.toString());
            }

            if (!status.equals("null")) {
                List<Property> list2 = new ArrayList<>();
                for (Property p : properties) {
                    if (p.getStatus().toString().equalsIgnoreCase(status)) {
                        list2.add(p);
                    }
                }
                properties = list2;
                System.out.println("Por estado:" + properties.toString());

            }

            if (!type.equals("null")) {
                List<Property> list3 = new ArrayList<>();
                for (Property p : properties) {
                    if (p.getType().toString().equalsIgnoreCase(type)) {
                        list3.add(p);
                    }
                }
                properties = list3;
                System.out.println("Por tipo:" + properties.toString());
            }
            if (bedrooms != 0) {
                List<Property> list4 = new ArrayList<>();
                for (Property p : properties) {
                    if (p.getBedrooms() == bedrooms) {
                        list4.add(p);
                    }
                }
                properties = list4;
                System.out.println("Por habitaciones:" + properties.toString());
            }

            if ((!((minPrice) == null)) || (!((maxPrice) == null))) {
                List<Property> list5 = priceFilter(properties, minPrice, maxPrice);
                properties = list5;
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
        if ((!((minPrice) == null)) && (!((maxPrice) == null))) {
            List<Property> list = new ArrayList<>();
            for (Property p : properties2) {
                if (p.getPrice() >= minPrice && p.getPrice() <= maxPrice) {
                    list.add(p);
                }
            }
            return list;
        } else if (!((minPrice) == null)) {
            List<Property> list = new ArrayList<>();
            for (Property p : properties2) {
                if (p.getPrice() >= minPrice) {
                    list.add(p);
                }
            }
            return list;
        } else if (!((maxPrice) == null)) {
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
