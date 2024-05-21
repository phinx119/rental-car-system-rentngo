package com.group3.rentngo;

import com.group3.rentngo.model.dto.SignupDto;
import com.group3.rentngo.model.dto.UserDto;
import com.group3.rentngo.model.entity.Booking;
import com.group3.rentngo.model.entity.Car;
import com.group3.rentngo.model.entity.Customer;
import com.group3.rentngo.model.entity.Role;
import com.group3.rentngo.repository.*;
import com.group3.rentngo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@SpringBootApplication
public class RentNGoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentNGoApplication.class, args);
    }
    /**
     * @author phinx
     * @description parse string to date include time
     */
    public Date parseDateTime(String dateString, String timeString) {
        String dateTime = dateString.concat(" ").concat(timeString);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            java.util.Date utilDate = sdf.parse(dateTime);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Bean
    public CommandLineRunner commandLineRunner(RoleRepository roleRepository,
                                               UserRepository userRepository,
                                               UserService userService,
                                               BookingRepository bookingRepository,
                                               CarRepository carRepository,
                                               CustomerRepository customerRepository) {
        return runner -> {
            // insert default role
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

            // insert default user
            UserDto userDto = new UserDto();
            userDto.setEmail("codewithphinx@gmail.com");
            userDto.setPassword("123");
            userDto.setStatus(true);

            if (userRepository.findByEmail(userDto.getEmail()) == null){
                userService.saveAdmin(userDto);
            }

//            Booking booking = new Booking();
//            Optional<Car> newCar = carRepository.findById(1L);
//            booking.setCar(newCar.orElse(null));
//            Optional<Customer> newCustomer = customerRepository.findById(1L);
//            booking.setCustomer(newCustomer.orElse(null));
//            Date start = parseDateTime("2024-04-17" , "09:00");
//            Date end = parseDateTime("2024-05-17" , "10:00");
//            booking.setStartDateTime(start);
//            booking.setEndDateTime(end);
//            booking.setPaymentMethod("cash");
//            booking.setStatus(true);
//            bookingRepository.save(booking);

        };

    }
}
