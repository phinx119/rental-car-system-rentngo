package com.group3.rentngo.service.impl;

import com.group3.rentngo.model.dto.ResetPasswordDto;
import com.group3.rentngo.model.dto.SignupDto;
import com.group3.rentngo.model.dto.UserDto;
import com.group3.rentngo.model.entity.*;
import com.group3.rentngo.repository.CarOwnerRepository;
import com.group3.rentngo.repository.CustomerRepository;
import com.group3.rentngo.repository.RoleRepository;
import com.group3.rentngo.repository.UserRepository;
import com.group3.rentngo.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CarOwnerRepository carOwnerRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           CarOwnerRepository carOwnerRepository,
                           CustomerRepository customerRepository,
                           PasswordEncoder passwordEncoder,
                           EmailService emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.carOwnerRepository = carOwnerRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public CustomUserDetails getUserDetail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails;
        }
        return null;
    }

    /**
     * @author phinx
     * @description insert default user - admin
     */
    @Override
    public void saveAdmin(UserDto userDto) {
        // insert admin account first
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setStatus(userDto.isStatus());
        userRepository.save(user);

        // then set role admin for admin
        User updateUser = userRepository.findByEmail(user.getEmail());

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
        // map data from dto to user
        User user = new User();
        user.setEmail(signupDto.getEmail());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        user.setStatus(true);

        // save car owner account
        if (signupDto.getRole().equals("CarOwner")) {
            // check role existed or not and set role
            Role role = roleRepository.findByName("ROLE_CAR_OWNER");
            if (role == null) {
                role = checkRoleExist("ROLE_CAR_OWNER");
            }
            user.setRoles(Arrays.asList(role));

            // insert car owner first
            CarOwner carOwner = new CarOwner();
            carOwner.setName(signupDto.getName());
            carOwner.setEmail(signupDto.getEmail());
            carOwner.setPhone(signupDto.getPhone());
            carOwnerRepository.save(carOwner);

            // then insert user
            CarOwner updateCarOwner = carOwnerRepository.findByEmail(signupDto.getEmail());
            updateCarOwner.setUser(user);
            carOwnerRepository.save(updateCarOwner);
        }

        // save customer account
        if (signupDto.getRole().equals("Customer")) {
            // check role existed or not and set role
            Role role = roleRepository.findByName("ROLE_CUSTOMER");
            if (role == null) {
                role = checkRoleExist("ROLE_CUSTOMER");
            }
            user.setRoles(Arrays.asList(role));

            // insert customer first
            Customer customer = new Customer();
            customer.setName(signupDto.getName());
            customer.setEmail(signupDto.getEmail());
            customer.setPhone(signupDto.getPhone());
            customerRepository.save(customer);

            // then insert user
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
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * @author phinx
     * @description get user list
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * @author phinx
     * @description test send email
     */
    public void sendComplexEmail(String email, String content) {
        Map<String, Object> model = new HashMap<>();
        model.put("email", email);
        model.put("content", "<p>" + content + "</p>");
        emailService.sendEmail(email, "Important Notification", model, "email/template");
    }

    @Override
    public void resetPassword(ResetPasswordDto resetPasswordDto) {
        String encodedPassword = passwordEncoder.encode(resetPasswordDto.getPassword());
        userRepository.updatePasswordByEmail(encodedPassword, resetPasswordDto.getEmail());
    }
}
