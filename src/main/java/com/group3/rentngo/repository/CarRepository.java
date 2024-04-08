package com.group3.rentngo.repository;

import com.group3.rentngo.model.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    public List<Car> findByCarOwner_Id(Long id);

    @Override
    Optional<Car> findById(Long id);
}
