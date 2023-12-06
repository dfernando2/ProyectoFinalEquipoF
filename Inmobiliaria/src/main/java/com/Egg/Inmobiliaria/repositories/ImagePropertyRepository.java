package com.Egg.Inmobiliaria.repositories;

import com.Egg.Inmobiliaria.models.ImageProperty;
import com.Egg.Inmobiliaria.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagePropertyRepository extends JpaRepository<ImageProperty, String> {

    @Query ("SELECT ip FROM ImageProperty ip WHERE ip.property = ?1")
    ImageProperty findByProperty(Property property);

    @Query ("DELETE FROM ImageProperty ip WHERE ip.property = ?1")
    void deleteByProperty(Property property);

    @Query("SELECT ip FROM ImageProperty ip WHERE ip.property = ?1 AND ip.id = (SELECT MIN(ip2.id) FROM ImageProperty ip2 WHERE ip2.property = ?1)")
    ImageProperty findFirstByProperty(Property property);

}
