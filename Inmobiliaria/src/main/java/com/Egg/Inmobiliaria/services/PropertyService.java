package com.Egg.Inmobiliaria.services;

import com.Egg.Inmobiliaria.enums.PropertyStatus;
import com.Egg.Inmobiliaria.enums.PropertyType;
import com.Egg.Inmobiliaria.exceptions.MiException;
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
    public void remove(Long id) throws MiException{
        Optional<Property> propertyAnswer = propertyRepository.findById(id);
        if (propertyAnswer.isPresent()) {
            
            propertyRepository.deleteById(id);
        }
    }

    public void findByType(PropertyType type) {
        List<Property> properties = propertyRepository.findByType(type);
    }
    public List<Property> getAllProperties() {
      
        return propertyRepository.findAll();
    }
    
    public void validate(String address, String province, String location, Integer surface,
                       Integer bathrooms, Integer bedrooms, Double price, String description,
                       PropertyStatus status, Date createDate, PropertyType type,
                       List<MultipartFile> files) throws MiException {
        
        if (address.isEmpty() || address == null) {
            throw new MiException("Debe ingresar una direccion");
        }
        if (province.isEmpty() || province == null) {
            throw new MiException("Debe ingresar una provincia");
        }
        if (location.isEmpty() || location == null) {
            throw new MiException("Debe ingresar una localidad");
        }
        if (surface == null) {
            throw new MiException("La superficie de la propiedad debe estar especificada");
        }
        if (bathrooms == null) {
            throw new MiException("Especifique la cantidad de baños por favor");
        }
        if (bedrooms == null) {
            throw new MiException("Especifique la cantidad de habitaciones por favor");
        }
        if (price == null) {
            throw new MiException("Indique el precio esperado");
        }
        if (description.isEmpty() || description == null) {
            throw new MiException("Indique una descripcion de la propiedad deseada");
        }
        if (createDate == null) {
            throw new MiException("Especifique la fecha de adquisicion");
        }
        if (files == null) {
            throw new MiException("La imagen es obligatoria");
        }
    
}

}
