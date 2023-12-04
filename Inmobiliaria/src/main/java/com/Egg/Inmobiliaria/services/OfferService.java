package com.Egg.Inmobiliaria.services;

import java.util.Date;
import java.util.Optional;
//import java.util.Optional;

import com.Egg.Inmobiliaria.models.Usuario;
import com.Egg.Inmobiliaria.repositories.OfferRepository;
import com.Egg.Inmobiliaria.repositories.PropertyRepository;
import com.Egg.Inmobiliaria.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Egg.Inmobiliaria.enums.OfferStatus;
import com.Egg.Inmobiliaria.exceptions.MiException;
import com.Egg.Inmobiliaria.models.Offer;
import com.Egg.Inmobiliaria.models.Property;


@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;


    public void createOffer(Long idProperty, Long idUser, Double price, Integer contact) throws MiException {

        validate(price, contact);
        Optional<Property> answerProperty = propertyRepository.findById(idProperty);
        Optional<Usuario> answerUser = userRepository.findById(idUser);
        Property property = new Property();
        Usuario usuario = new Usuario();
        if (answerProperty.isPresent()) {
            property = answerProperty.get();
        }
        if (answerUser.isPresent()) {
            usuario = answerUser.get();
        }

        Offer offer = new Offer();
        offer.setCreationDate(new Date());
        offer.setUsuario(usuario);
        if (price != null) {
            offer.setPrice(price);
        }
        if (contact != null) {
            offer.setContact(contact);
        }
        offer.setProperty(property);
        offer.setOfferStatus(OfferStatus.CLIENT_OFFER);

        offerRepository.save(offer);
    }

    public void validate(Double price, Integer contact) throws MiException {

        if (price == 0 || price == null) {
            throw new MiException("La oferta no puede ser nula o estar vacia");

        }
        if (contact == 0 || contact == null) {
            throw new MiException("El número de teléfono no puede ser nulo o estar vacio");

        }
        
    }

//    public void update(String id, String idProperty, Long idUser, Double price, int contact) {
//
//        Optional<Property> answerProperty = propertyRepository.findById(Long.valueOf(idProperty));
//        Optional<Usuario> answerUser = userRepository.findById(idUser);
//        Optional<Offer> answerOffer = offerRepository.findById(Long.valueOf(id));
//
//        Property property = new Property();
//
//        Usuario usuario = new Usuario();
//
//        if (answerProperty.isPresent()) {
//            property = answerProperty.get();
//        }
//        if (answerUser.isPresent()) {
//            usuario = answerUser.get();
//        }
//
//        Offer offer = new Offer();
//        if (answerOffer.isPresent()) {
//            offer = answerOffer.get();
//            offer.setCreationDate(new Date());
//            offer.setUsuario(usuario);
//            if (price != null) {
//                offer.setPrice(price);
//            }
//            if (contact != 0) {
//                offer.setContact(contact);
//            }
//            offer.setProperty(property);
//            offer.setOfferStatus(OfferStatus.CLIENT_OFFER);
//
//            offerRepository.save(offer);
//        }
//    }
//
//    private void remove(String idOffer) {
//
//        Optional<Offer> answerOffer = offerRepository.findById(Long.valueOf(idOffer));
//        if (answerOffer.isPresent()) {
//            Offer offer = answerOffer.get();
//            offer.setOfferStatus(OfferStatus.INACTIVE_OFFER);
//            offerRepository.save(offer);
//        }
//    }
}
    

