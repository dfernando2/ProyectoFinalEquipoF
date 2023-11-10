package com.Egg.Inmobiliaria.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Egg.Inmobiliaria.models.Offer;

@Repository
public interface IOfferRepository extends JpaRepository<Offer, Long>{
    
}
