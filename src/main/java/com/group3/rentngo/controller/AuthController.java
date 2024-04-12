package com.group3.rentngo.controller;

import com.group3.rentngo.common.CommonUtil;
import com.group3.rentngo.model.dto.ResetPasswordDto;
import com.group3.rentngo.model.dto.SignupDto;
import com.group3.rentngo.model.dto.UpdateProfileDto;
import com.group3.rentngo.model.dto.UserDto;
import com.group3.rentngo.model.entity.CustomUserDetails;
import com.group3.rentngo.service.CarOwnerService;
import com.group3.rentngo.service.CustomerService;
import com.group3.rentngo.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;

/**
 * @author phinx
 * @description controller class contain authorization function
 */
@Controller
public class AuthController {
    private final UserService userService;
    private final CarOwnerService carOwnerService;
    private final CustomerService customerService;
    private final CommonUtil commonUtil;
    private final HttpSession session;

    @Autowired
    public AuthController(UserService userService,
                          CarOwnerService carOwnerService,
                          CustomerService customerService,
                          CommonUtil commonUtil,
                          HttpSession session) {
        this.userService = userService;
        this.carOwnerService = carOwnerService;
        this.customerService = customerService;
        this.commonUtil = commonUtil;
        this.session = session;
    }

    /**
     * @author phinx
     * @description redirect to home page for each actor
     */
    @GetMapping(value = {"/", "/home"})
    public String homePage(Model model) {
        CustomUserDetails userDetails = userService.getUserDetail();
        if (userDetails != null) {
            boolean isAdmin = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            boolean isCarOwner = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CAR_OWNER"));
            boolean isCustomer = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"));
            session.setAttribute("email", userDetails.getUsername());
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
        return "home-page";
    }

    /**
     * @author phinx
     * @description redirect to home page for each actor
     */
    @GetMapping(value = "/home/login-signup")
    public String showLoginSignupPage(Model model) {
        SignupDto signupDto = new SignupDto();

        model.addAttribute("email", "");
        model.addAttribute("signupDto", signupDto);
        return "login-sign-up-page";
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

        if (result.hasErrors()) {
            UserDto userDto = new UserDto();

            model.addAttribute("userDto", userDto);
            model.addAttribute("signupDto", signupDto);
            return "login-sign-up-page";
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
    public String checkExistedEmail(@RequestParam("email") String email,
                                    Model model) {
        if (email.isEmpty()) {
            model.addAttribute("emailError", "Email cannot be empty");
            SignupDto signupDto = new SignupDto();

            model.addAttribute("email", "");
            model.addAttribute("signupDto", signupDto);
            return "login-sign-up-page";
        } else if (userService.findUserByEmail(email) == null) {
            model.addAttribute("emailError", "Email address does not exist in the system.");
            SignupDto signupDto = new SignupDto();

            model.addAttribute("email", "");
            model.addAttribute("signupDto", signupDto);
            return "login-sign-up-page";
        } else {
            userService.sendComplexEmail(email, "<a href=\"http://localhost:8080/reset-password/" + email + "\">Reset password link</a>");
            return "redirect:/home/login-signup";
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

    /**
     * @author phinx
     * @description update user detail
     */
    @PostMapping("/update-user-profile")
    public String updateUserInfo(@Valid @ModelAttribute("updateProfileDto") UpdateProfileDto updateProfileDto,
                                 BindingResult result,
                                 Model model)
            throws ParseException {
        if (updateProfileDto.getDateOfBirth() == null || updateProfileDto.getDateOfBirth().isEmpty()) {
            result.rejectValue("dateOfBirth", null, "This field is required.");
        } else if (commonUtil.parseDate(updateProfileDto.getDateOfBirth()).compareTo(new Date()) > 0) {
            result.rejectValue("dateOfBirth", null, "Not earlier than current date.");
        }

        model.addAttribute("updateProfileDto", updateProfileDto);
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            model.addAttribute("updateProfileDto", updateProfileDto);

            return "edit-profile";
        } else {
            if (updateProfileDto.getRole().equals("ROLE_CAR_OWNER")) {
                carOwnerService.updateProfile(updateProfileDto);
            }
            if (updateProfileDto.getRole().equals("ROLE_CUSTOMER")) {
                customerService.updateProfile(updateProfileDto);
            }
        }

        UserDetails userDetails = userService.getUserDetail();
        if (userDetails != null) {
            boolean isAdmin = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            boolean isCarOwner = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CAR_OWNER"));
            boolean isCustomer = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"));
            session.setAttribute("email", userDetails.getUsername());
            if (isAdmin) {
                return "redirect:/admin/view-car-owner-detail?id=" + updateProfileDto.getId();
            }
            if (isCarOwner) {
                return "redirect:/car-owner/view-car-owner-detail";
            }
            if (isCustomer) {
                return "redirect:/customer/view-car-owner-detail";
            }
        }
        return "redirect:/home";
    }
}
