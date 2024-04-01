package com.group3.rentngo.service;

import com.group3.rentngo.model.entity.Customer;

public interface CustomerService {
    Customer findCustomerByEmail(String email);

    Customer findCustomerByPhone(String phone);
}
