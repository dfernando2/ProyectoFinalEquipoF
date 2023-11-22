package com.Egg.Inmobiliaria.repositories;

import com.Egg.Inmobiliaria.models.ImageProperty;
import com.Egg.Inmobiliaria.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImagePropertyRepository extends JpaRepository<ImageProperty, String> {

    //Traer imagen por objeto property
    @Query ("SELECT i FROM ImageProperty i WHERE i.property = ?1")
    ImageProperty findByProperty(Optional<Property> property);

}
