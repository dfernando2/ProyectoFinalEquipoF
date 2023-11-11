package com.Egg.Inmobiliaria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Egg.Inmobiliaria.models.ImageProperty;


@Repository
public interface imagePropertyRepository extends JpaRepository<ImageProperty, String> {
    
   
    
}
