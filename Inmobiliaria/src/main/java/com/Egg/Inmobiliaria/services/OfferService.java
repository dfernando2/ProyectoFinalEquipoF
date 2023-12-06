package com.Egg.Inmobiliaria.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.Egg.Inmobiliaria.models.Usuario;
import com.Egg.Inmobiliaria.repositories.OfferRepository;
import com.Egg.Inmobiliaria.repositories.PropertyRepository;
import com.Egg.Inmobiliaria.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Egg.Inmobiliaria.enums.OfferStatus;
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

    public void createOffer(Long idProperty, Long idUser, Double price, Integer contact) {

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

    public List<Offer> getAllOffersByUserId(Long idUser) {
        return offerRepository.findByUsuarioId(idUser);
    }

    public Offer getOne(Long id) {
        return offerRepository.getOne(id);
    }

    public void acceptOfferStatus(Long idOffer) {
        System.out.println("Este es el id de la oferta a cambiar" + idOffer);
        Offer offer = getOne(idOffer);
        System.out.println("Esta es la oferta a cambiar" + offer);
        offer.setOfferStatus(OfferStatus.ENTITY_ACCEPTED);
        offerRepository.save(offer);

    }
    public void rejectOfferStatus(Long idOffer) {
        System.out.println("Este es el id de la oferta a cambiar" + idOffer);
        System.out.println("Este es el id de la oferta a cambiar" + idOffer);
        Offer offer = getOne(idOffer);
        System.out.println("Esta es la oferta a cambiar" + offer);
            offer.setOfferStatus(OfferStatus.ENTITY_REJECTED);
            offerRepository.save(offer);
        }

    public List<Offer> getAllOffersByProperty(Long id) {
        return offerRepository.findByPropertyId(id);
    }

      TODO esta es la ultima funcionalidad que no esta funcionando
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
    

