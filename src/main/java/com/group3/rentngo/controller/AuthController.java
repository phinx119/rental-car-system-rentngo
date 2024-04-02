package com.group3.rentngo.controller;

import com.group3.rentngo.model.dto.SignupDto;
import com.group3.rentngo.service.CarOwnerService;
import com.group3.rentngo.service.CustomerService;
import com.group3.rentngo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private UserService userService;
    private CarOwnerService carOwnerService;
    private CustomerService customerService;

    @Autowired
    public AuthController(UserService userService,
                          CarOwnerService carOwnerService,
                          CustomerService customerService) {
        this.userService = userService;
        this.carOwnerService = carOwnerService;
        this.customerService = customerService;
    }

    @GetMapping(value = {"/", "/home"})
    public String homePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !authentication.getAuthorities().isEmpty()) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            boolean isCarOwner = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_CAR_OWNER"));
            boolean isCustomer = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"));
            if (isAdmin) {
                return "redirect:/admin/list-user";
            }
            if (isCarOwner) {
                return "redirect:/car-owner/home";
            }
            if (isCustomer) {
                return "redirect:/customer/home";
            }
        }

        SignupDto signupDto = new SignupDto();
        model.addAttribute("signupDto", signupDto);

        return "home-page";
    }

    @PostMapping("/home/signup")
    public String signup(@Valid @ModelAttribute("signupDto") SignupDto signupDto,
                         BindingResult result,
                         Model model) {
        // check existed user account with username
        if (userService.findUserByUsername(signupDto.getUsername()) != null) {
            result.rejectValue("username", null, "There is already an account registered with the same username");
        } else {
            // check existed car owner account with email or phone number
            if ("CarOwner".equals(signupDto.getRole())) {
                if (carOwnerService.findCarOwnerByEmail(signupDto.getEmail()) != null) {
                    result.rejectValue("email", null, "There is already a car owner account registered with the same email");
                }
                if (carOwnerService.findCarOwnerByPhone(signupDto.getPhone()) != null) {
                    result.rejectValue("phone", null, "There is already a car owner account registered with the same phone");
                }
            }
            // check existed customer account with email or phone number
            if ("Customer".equals(signupDto.getRole())) {
                if (customerService.findCustomerByEmail(signupDto.getEmail()) != null) {
                    result.rejectValue("email", null, "There is already a customer account registered with the same email");
                }
                if (customerService.findCustomerByPhone(signupDto.getPhone()) != null) {
                    result.rejectValue("phone", null, "There is already a customer account registered with the same phone");
                }
            }
        }

        if (signupDto.getAgreePrivacy() == null || signupDto.getAgreePrivacy().isEmpty()) {
            result.rejectValue("agreePrivacy", null, "You must agree to the privacy policy to continue.");
        }

        if (result.hasErrors()) {
            model.addAttribute("signupDto", signupDto);
            model.addAttribute("errors", result.getAllErrors());
            return "home-page";
        }

        userService.saveUser(signupDto);
        return "redirect:/customer/list-car";
        //return "home-page";
    }

    @GetMapping("/error-403")
    public String hello() {
        return "error/403";
    }
}
