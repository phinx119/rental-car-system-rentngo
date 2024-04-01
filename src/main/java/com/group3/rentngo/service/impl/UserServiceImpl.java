package com.group3.rentngo.service.impl;

import com.group3.rentngo.model.dto.SignupDto;
import com.group3.rentngo.model.entity.CarOwner;
import com.group3.rentngo.model.entity.Customer;
import com.group3.rentngo.model.entity.Role;
import com.group3.rentngo.model.entity.User;
import com.group3.rentngo.repository.CarOwnerRepository;
import com.group3.rentngo.repository.CustomerRepository;
import com.group3.rentngo.repository.RoleRepository;
import com.group3.rentngo.repository.UserRepository;
import com.group3.rentngo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private CarOwnerRepository carOwnerRepository;
    private CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           CarOwnerRepository carOwnerRepository,
                           CustomerRepository customerRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.carOwnerRepository = carOwnerRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(SignupDto signupDto) {
        User user = new User();
        user.setUsername(signupDto.getUsername());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        // save car owner account
        if (signupDto.getRole().equals("CarOwner")) {
            Role role = roleRepository.findByName("ROLE_CAR_OWNER");
            if (role == null) {
                role =checkRoleExist("ROLE_CAR_OWNER");
            }
            user.setRoles(Arrays.asList(role));

            CarOwner carOwner = new CarOwner();
            carOwner.setEmail(signupDto.getEmail());
            carOwner.setPhone(signupDto.getPhone());
            carOwnerRepository.save(carOwner);

            CarOwner updateCarOwner = carOwnerRepository.findByEmail(signupDto.getEmail());
            updateCarOwner.setUser(user);
            carOwnerRepository.save(updateCarOwner);
        }

        // save customer account
        if (signupDto.getRole().equals("Customer")) {
            Role role = roleRepository.findByName("ROLE_CUSTOMER");
            if (role == null) {
                role = checkRoleExist("ROLE_CUSTOMER");
            }
            user.setRoles(Arrays.asList(role));

            Customer customer = new Customer();
            customer.setEmail(signupDto.getEmail());
            customer.setPhone(signupDto.getPhone());
            customerRepository.save(customer);

            Customer updateCustomer = customerRepository.findByEmail(signupDto.getEmail());
            updateCustomer.setUser(user);
            customerRepository.save(updateCustomer);
        }
    }

    private Role checkRoleExist(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
