package com.group3.rentngo.controller;

import com.group3.rentngo.model.dto.SignupDto;
import com.group3.rentngo.model.entity.CustomUserDetails;
import com.group3.rentngo.service.CarOwnerService;
import com.group3.rentngo.service.CustomerService;
import com.group3.rentngo.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author phinx
 * @description controller class contain authorization function
 */
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

    /**
     * @author phinx
     * @description redirect to home page for each actor
     */
    @GetMapping(value = {"/", "/home"})
    public String homePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            boolean isAdmin = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            boolean isCarOwner = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CAR_OWNER"));
            boolean isCustomer = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"));
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

    /**
     * @author phinx
     * @description signup method
     */
    @PostMapping("/home/signup")
    public String signup(@Valid @ModelAttribute("signupDto") SignupDto signupDto,
                         BindingResult result,
                         Model model) {
        // check existed user account with username
        if (userService.findUserByEmail(signupDto.getEmail()) != null) {
            result.rejectValue("email", null, "There is already an account registered with the same username");
        } else {
            // check existed car owner account with email or phone number
            if ("CarOwner".equals(signupDto.getRole()) && carOwnerService.findCarOwnerByPhone(signupDto.getPhone()) != null) {
                result.rejectValue("phone", null, "There is already a car owner account registered with the same phone");
            }
            // check existed customer account with email or phone number
            if ("Customer".equals(signupDto.getRole()) && customerService.findCustomerByPhone(signupDto.getPhone()) != null) {
                result.rejectValue("phone", null, "There is already a customer account registered with the same phone");
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
        return "redirect:/home";
    }

    /**
     * @author phinx
     * @description get input email, check existed to reset password
     */
    @PostMapping("/home/check-existed-email")
    public String checkExistedEmail(@Valid @RequestParam("email") @NotBlank @Email String email,
                                    BindingResult result,
                                    Model model) {
        if (result.hasErrors()) {
            // Handle validation errors, if any
            return "error-page"; // You can specify an error page to redirect to
        } else {
            // Proceed with your logic here
            // For example, check if the email exists in your system
            // and perform necessary actions
            return "success-page"; // You can specify a success page to redirect to
        }
    }

    /**
     * @author phinx
     * @description show error 403 page
     */
    @GetMapping("/error-403")
    public String showError403() {
        return "error/403";
    }
}
