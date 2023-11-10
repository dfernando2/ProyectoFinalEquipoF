package com.Egg.Inmobiliaria.services;

import java.time.LocalDate;
//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Egg.Inmobiliaria.enums.OfferStatus;
import com.Egg.Inmobiliaria.models.Offer;
import com.Egg.Inmobiliaria.models.Property;
import com.Egg.Inmobiliaria.models.User;
import com.Egg.Inmobiliaria.repositorys.IOfferRepository;

@Service
public class OfferServices {

    @Autowired
    private IOfferRepository offerRepository;

    //@Autowired
    //private UserService userService;

    //@Autowired
    //private PropertyService propertyService;

    //Cambiar property y user por sus ID
    public void createOffer(Property property, User user, Double price){

        Offer offer = new Offer();

        offer.setCreationDate(LocalDate.now());
        offer.setUser(user);
        offer.setPrice(price);
        offer.setProperty(property);
        offer.setOfferStatus(OfferStatus.CLIENT_OFFER);

        offerRepository.save(offer);
    }

    //Cambiar property y user por sus ID
    public void modifyOffer(Property property, User user, Double price){

        //Faltaria el dato de los services property y user
        //Optional<Property> responseProperty = propertyService.findById();

        //Optional<User> responseUser = userService.findById();
    }

    private void deleteOffer(Long offerId){

        //Optional<Offer> responseOffer = offerRepository.findById(offerId);

        //Offer offer = responseOffer.get();

        //offer.setOfferStatus(OfferStatus.INACTIVE_OFFER);
    }
}
    

