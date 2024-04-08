package com.group3.rentngo.controller;

import com.group3.rentngo.model.dto.ResetPasswordDto;
import com.group3.rentngo.model.dto.SignupDto;
import com.group3.rentngo.model.dto.UserDto;
import com.group3.rentngo.model.entity.CustomUserDetails;
import com.group3.rentngo.service.CarOwnerService;
import com.group3.rentngo.service.CustomerService;
import com.group3.rentngo.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author phinx
 * @description controller class contain authorization function
 */
@Controller
public class AuthController {
    private UserService userService;
    private CarOwnerService carOwnerService;
    private CustomerService customerService;
    private HttpSession session;

    @Autowired
    public AuthController(UserService userService,
                          CarOwnerService carOwnerService,
                          CustomerService customerService,
                          HttpSession session) {
        this.userService = userService;
        this.carOwnerService = carOwnerService;
        this.customerService = customerService;
        this.session = session;
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
            session.setAttribute("email", userDetails.getUsername());
            if (isAdmin) {
                return "redirect:/admin/list-user";
            }
            if (isCarOwner) {
                long id = userDetails.getId();
                model.addAttribute("id",id);
//                return "redirect:/car-owner/home";
                return "home-page-as-car-owner";
            }
            if (isCustomer) {
                return "redirect:/customer/home";
            }
        }

        UserDto userDto = new UserDto();
        SignupDto signupDto = new SignupDto();

        model.addAttribute("userDto", userDto);
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
            result.rejectValue("email", null, "Email already existed. Please try another email.");
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

        if (!signupDto.getPassword().equals(signupDto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", null, "Password and Confirm password don’t match. Please try again.");
        }

//        if (signupDto.getAgreePrivacy() == null || signupDto.getAgreePrivacy().isEmpty()) {
//            result.rejectValue("agreePrivacy", null, "You must agree to the privacy policy to continue.");
//        }

        if (result.hasErrors()) {
            UserDto userDto = new UserDto();
          
            model.addAttribute("userDto", userDto);
            model.addAttribute("signupDto", signupDto);
            model.addAttribute("errors", result.getAllErrors());
            return "home-page";
        } else {
            userService.saveUser(signupDto);
            return "redirect:/home";
        }
    }

    /**
     * @author phinx
     * @description get input email, check existed to reset password
     */
    @PostMapping("/home/check-existed-email")
    public String checkExistedEmail(@Valid @ModelAttribute("userDto") UserDto userDto,
                                    BindingResult result,
                                    Model model) {
        if (userService.findUserByEmail(userDto.getEmail()) == null) {
            result.rejectValue("email", null, "Email address does not exist in the system.");
        }
        result.getAllErrors();
        if (result.hasErrors()) {
            model.addAttribute("userDto", userDto);
            model.addAttribute("errors", result.getAllErrors());
            return "home-page";
        } else {
            userService.sendComplexEmail(userDto.getEmail(), "<a href=\"http://localhost:8080/reset-password/" + userDto.getEmail() + "\">Reset password link</a>");
            return "redirect:/home";
        }
    }

    /**
     * @author phinx
     * @description show reset password page
     */
    @GetMapping("/reset-password/{email}")
    public String showResetPasswordPage(@PathVariable String email, Model model) {
        ResetPasswordDto resetPasswordDto = new ResetPasswordDto();
        if (email != null) {
            resetPasswordDto.setEmail(email);
        }
        model.addAttribute("resetPasswordDto", resetPasswordDto);
        return "reset-password-page";
    }

    /**
     * @author phinx
     * @description get input email, check existed to reset password
     */
    @PostMapping("/reset-password")
    public String resetPassword(@Valid @ModelAttribute("resetPasswordDto") ResetPasswordDto resetPasswordDto,
                                BindingResult result,
                                Model model) {
        if (!resetPasswordDto.getPassword().equals(resetPasswordDto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", null, "Password and Confirm password don’t match. Please try again.");
        }

        if (result.hasErrors()) {
            model.addAttribute("resetPasswordDto", resetPasswordDto);
            model.addAttribute("email", resetPasswordDto.getEmail());
            model.addAttribute("errors", result.getAllErrors());
            return "reset-password-page";
        } else {
            userService.resetPassword(resetPasswordDto);
            return "redirect:/home";
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
