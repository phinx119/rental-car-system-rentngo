package com.group3.rentngo.repository;

import com.group3.rentngo.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Override
    Optional<Customer> findById(Long aLong);

    Customer findByEmail(String email);

    Customer findByPhone(String phone);


}
