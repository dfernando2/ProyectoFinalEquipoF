package com.Egg.Inmobiliaria.repositories;
import com.Egg.Inmobiliaria.models.ImageUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ImageUserRepository extends JpaRepository<ImageUser, String>{

}