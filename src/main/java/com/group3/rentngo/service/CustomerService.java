package com.group3.rentngo.service;

import com.group3.rentngo.model.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer findCustomerByEmail(String email);

    Customer findCustomerByPhone(String phone);

    List<Customer> findAll();
}
