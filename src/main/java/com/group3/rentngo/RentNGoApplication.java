package com.group3.rentngo;

import com.group3.rentngo.model.dto.SignupDto;
import com.group3.rentngo.model.entity.Role;
import com.group3.rentngo.repository.CustomerRepository;
import com.group3.rentngo.repository.RoleRepository;
import com.group3.rentngo.service.CarOwnerService;
import com.group3.rentngo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RentNGoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentNGoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(RoleRepository roleRepository,
                                               CustomerRepository customerRepository,
                                               UserService userService, CarOwnerService carOwnerService) {
        return runner -> {
            Role roleAdmin = roleRepository.findByName("ROLE_ADMIN");
            if (roleAdmin == null) {
                Role role1 = new Role();
                role1.setName("ROLE_ADMIN");
                roleRepository.save(role1);
            }

            Role roleCarOwner = roleRepository.findByName("ROLE_CAR_OWNER");
            if (roleCarOwner == null) {
                Role role2 = new Role();
                role2.setName("ROLE_CAR_OWNER");
                roleRepository.save(role2);
            }

            Role roleCustomer = roleRepository.findByName("ROLE_CUSTOMER");
            if (roleCustomer == null) {
                Role role3 = new Role();
                role3.setName("ROLE_CUSTOMER");
                roleRepository.save(role3);
            }

            SignupDto signupDto = new SignupDto();
            signupDto.setUsername("xuanphi");
            signupDto.setEmail("xuanphi2003@gmail.com");
            signupDto.setPhone("0812497838");
            signupDto.setPassword("phi119");
            signupDto.setConfirmPassword("phi119");
            signupDto.setRole("Customer");
            signupDto.setAgreePrivacy("true");

            if (customerRepository.findByEmail(signupDto.getEmail()) == null){
                userService.saveUser(signupDto);
            }
            carOwnerService.changBookingStatus(1);
        };

    }
}
