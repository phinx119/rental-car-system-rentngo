package com.group3.rentngo.service;

import com.group3.rentngo.model.dto.UpdateProfileDto;
import com.group3.rentngo.model.entity.Customer;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Optional<Customer> findCustomerById(Long customerId);

    Customer findCustomerByEmail(String email);

    Customer findCustomerByPhone(String phone);

    List<Customer> findAll();

    void updateProfile(UpdateProfileDto updateProfileDto) throws ParseException;

    void updateWallet(String email, String totalPrice);

    UpdateProfileDto getDtoFromCustomer(Customer customer);
}
