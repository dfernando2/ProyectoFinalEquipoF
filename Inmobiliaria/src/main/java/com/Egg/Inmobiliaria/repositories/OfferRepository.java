package com.Egg.Inmobiliaria.repositories;

import com.Egg.Inmobiliaria.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Egg.Inmobiliaria.models.Offer;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long>{

    //traer las ofertas desde el repositorio por id de usuario
    @Query ("SELECT o FROM Offer o WHERE o.usuario.id = ?1")
    List<Offer> findOfferByUserId(Long id);

//    @Query ("SELECT o FROM Offer o WHERE o.property.id = ?1")
//    List<Offer> findOfferByPropId(Long id);
////    List<Property> findPropById(Long id);

}
