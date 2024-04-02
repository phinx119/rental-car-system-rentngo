package com.group3.rentngo.service.impl;

import com.group3.rentngo.model.dto.SignupDto;
import com.group3.rentngo.model.dto.UserDto;
import com.group3.rentngo.model.entity.CarOwner;
import com.group3.rentngo.model.entity.Customer;
import com.group3.rentngo.model.entity.Role;
import com.group3.rentngo.model.entity.User;
import com.group3.rentngo.repository.*;
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

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           CarOwnerRepository carOwnerRepository,
                           CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.carOwnerRepository = carOwnerRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * @author phinx
     * @description insert default user - admin
     */
    @Override
    public void saveAdmin(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setStatus(userDto.isStatus());
        userRepository.save(user);

        User updateUser = userRepository.findByUsername(user.getUsername());

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = checkRoleExist("ROLE_ADMIN");
        }
        updateUser.setRoles(Arrays.asList(role));
        userRepository.save(updateUser);
    }

    /**
     * @author phinx
     * @description insert new user with suitable role
     */
    @Override
    public void saveUser(SignupDto signupDto) {
        User user = new User();
        user.setUsername(signupDto.getUsername());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        user.setStatus(true);

        // save car owner account
        if (signupDto.getRole().equals("CarOwner")) {
            Role role = roleRepository.findByName("ROLE_CAR_OWNER");
            if (role == null) {
                role = checkRoleExist("ROLE_CAR_OWNER");
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

    /**
     * @author phinx
     * @description check role existed or not
     */
    private Role checkRoleExist(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }

    /**
     * @author phinx
     * @description get user detail by username
     */
    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
