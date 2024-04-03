package com.group3.rentngo.repository;

import com.group3.rentngo.model.entity.CarOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarOwnerRepository extends JpaRepository<CarOwner, Long> {
    CarOwner findByEmail(String email);

    CarOwner findByPhone(String phone);

    CarOwner findByUser_Username(String username);

}
