package com.Egg.Inmobiliaria.repositories;

import com.Egg.Inmobiliaria.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Egg.Inmobiliaria.models.Usuario;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long>{

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Usuario buscarPorEmail(@Param("email") String email);
    
    @Query("SELECT u FROM Usuario u WHERE u.role = :CLIENT")
    List<Usuario> buscarPorInquilino(Role role);
    
    //@Query("SELECT p FROM Property p WHERE p.type = ?1")
    //List<Property> findByType(PropertyType type);
}
