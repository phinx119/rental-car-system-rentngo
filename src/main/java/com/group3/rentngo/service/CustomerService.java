package com.group3.rentngo.service;

import com.group3.rentngo.model.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Optional<Customer> findCustomerById(Long customerId);
    Customer findCustomerByEmail(String email);
    Customer findCustomerByPhone(String phone);
    List<Customer> findAll();
    void updateWallet(String email, String totalPrice);
}
