package com.Egg.Inmobiliaria.services;

import java.util.Date;
import java.util.Optional;
//import java.util.Optional;

import com.Egg.Inmobiliaria.repositories.PropertyRepository;
import com.Egg.Inmobiliaria.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Egg.Inmobiliaria.enums.OfferStatus;
import com.Egg.Inmobiliaria.models.Offer;
import com.Egg.Inmobiliaria.models.Property;
import com.Egg.Inmobiliaria.models.User;
import com.Egg.Inmobiliaria.repositories.OfferRepository;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;


    public void createOffer(String idProperty, String idUser, Double price){

        Optional<Property> answerProperty = propertyRepository.findById(Long.valueOf(idProperty));
        Optional<User> answerUser = userRepository.findById(Long.valueOf(idUser));
        Property property = new Property();
        User user = new User();
        if(answerProperty.isPresent()){
            property = answerProperty.get();
        }
        if(answerUser.isPresent()){
            user = answerUser.get();
        }

        Offer offer = new Offer();
        offer.setCreationDate(new Date());
        offer.setUser(user);
        offer.setPrice(price);
        offer.setProperty(property);
        offer.setOfferStatus(OfferStatus.CLIENT_OFFER);

        offerRepository.save(offer);
    }
    public void update(String id, String idProperty, String idUser, Double price){

        Optional<Property> answerProperty = propertyRepository.findById(Long.valueOf(idProperty));
        Optional<User> answerUser = userRepository.findById(Long.valueOf(idUser));
        Optional<Offer> answerOffer = offerRepository.findById(Long.valueOf(id));

        Property property = new Property();
        User user = new User();
        if(answerProperty.isPresent()){
            property = answerProperty.get();
        }
        if(answerUser.isPresent()){
            user = answerUser.get();
        }

        Offer offer = new Offer();
        if(answerOffer.isPresent()){
            offer = answerOffer.get();
            offer.setCreationDate(new Date());
            offer.setUser(user);
            offer.setPrice(price);
            offer.setProperty(property);
            offer.setOfferStatus(OfferStatus.CLIENT_OFFER);

            offerRepository.save(offer);
        }
    }
    private void remove(String idOffer) {

        Optional<Offer> answerOffer = offerRepository.findById(Long.valueOf(idOffer));
        if (answerOffer.isPresent()) {
            Offer offer = answerOffer.get();
            offer.setOfferStatus(OfferStatus.INACTIVE_OFFER);
            offerRepository.save(offer);
        }
    }
}
    

