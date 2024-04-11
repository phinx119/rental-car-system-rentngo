package com.group3.rentngo.repository;

import com.group3.rentngo.model.entity.CarImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarImageRepository extends JpaRepository<CarImage, Long> {


    @Query("SELECT MAX(ci.id) FROM CarImage ci")
    Long findMaxId();
}
