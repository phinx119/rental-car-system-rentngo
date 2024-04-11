package com.group3.rentngo.repository;

import com.group3.rentngo.model.entity.CarOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Optional;

@Repository
public interface CarOwnerRepository extends JpaRepository<CarOwner, Long> {
    CarOwner findByEmail(String email);
    
    CarOwner findByPhone(String phone);

    @Override
    Optional<CarOwner> findById(Long aLong);

    CarOwner findByUser_Id(Long id);

    @Transactional
    @Modifying
    @Query("update CarOwner c set c.wallet = ?1 where c.email = ?2")
    int updateWalletByEmail(BigDecimal wallet, String email);

    @Transactional
    @Modifying
    @Query("""
            update CarOwner c set c.name = ?1, c.dateOfBirth = ?2, c.nationalId = ?3, c.phone = ?4, c.email = ?5, c.address = ?6, c.drivingLicense = ?7
            where c.id = ?8""")
    int updateProfile(String name, Date dateOfBirth, String nationalId, String phone, String email, String address, String drivingLicense, Long id);
}
