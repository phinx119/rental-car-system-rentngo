package com.group3.rentngo.repository;

import com.group3.rentngo.model.entity.CarOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface CarOwnerRepository extends JpaRepository<CarOwner, Long> {
    CarOwner findByEmail(String email);
    
    CarOwner findByPhone(String phone);

    CarOwner findByUser_Id(Long id);

    @Transactional
    @Modifying
    @Query("update CarOwner c set c.wallet = ?1 where c.email = ?2")
    int updateWalletByEmail(BigDecimal wallet, String email);



}
