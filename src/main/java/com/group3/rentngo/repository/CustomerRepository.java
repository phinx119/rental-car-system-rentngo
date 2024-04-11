package com.group3.rentngo.repository;

import com.group3.rentngo.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Override
    Optional<Customer> findById(Long aLong);

    Customer findByEmail(String email);

    Customer findByPhone(String phone);

    @Transactional
    @Modifying
    @Query("update Customer c set c.wallet = ?1 where c.email = ?2")
    int updateWalletByEmail(BigDecimal wallet, String email);

    @Transactional
    @Modifying
    @Query("""
            update Customer c set c.name = ?1, c.dateOfBirth = ?2, c.nationalId = ?3, c.phone = ?4, c.email = ?5, c.address = ?6, c.drivingLicense = ?7
            where c.id = ?8""")
    int updateProfile(String name, Date dateOfBirth, String nationalId, String phone, String email, String address, String drivingLicense, Long id);
}
