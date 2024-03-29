package com.group3.rentngo.service.impl;

import com.group3.rentngo.repository.CarOwnerRepository;
import com.group3.rentngo.repository.CustomerRepository;
import com.group3.rentngo.repository.RoleRepository;
import com.group3.rentngo.repository.UserRepository;
import com.group3.rentngo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private CarOwnerRepository carOwnerRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           CarOwnerRepository carOwnerRepository,
                           CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.carOwnerRepository = carOwnerRepository;
        this.customerRepository = customerRepository;
    }
}
