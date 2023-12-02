package com.Egg.Inmobiliaria.repositories;

import com.Egg.Inmobiliaria.models.ImageProperty;
import com.Egg.Inmobiliaria.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImagePropertyRepository extends JpaRepository<ImageProperty, String> {

    @Query ("SELECT ip FROM ImageProperty ip WHERE ip.id = ?1")
    ImageProperty findByProperty(Property property);

    @Query ("DELETE FROM ImageProperty ip WHERE ip.id = ?1")
    void deleteByProperty(Property property);

   
    

}
