package com.Egg.Inmobiliaria.repositories;

import com.Egg.Inmobiliaria.enums.PropertyType;
import com.Egg.Inmobiliaria.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("SELECT p FROM Property p WHERE p.type = ?1")
    List<Property> findByType(PropertyType type);

}
