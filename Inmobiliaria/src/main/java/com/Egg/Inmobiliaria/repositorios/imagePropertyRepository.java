/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Egg.Inmobiliaria.repositorios;

import com.Egg.Inmobiliaria.entidades.ImageProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cosas
 */
@Repository
public interface imagePropertyRepository extends JpaRepository<ImageProperty, String> {
    
   
    
}
