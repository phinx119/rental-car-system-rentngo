package com.group3.rentngo.controller;

import com.group3.rentngo.common.CommonUtil;
import com.group3.rentngo.model.dto.UpdateProfileDto;
import com.group3.rentngo.model.entity.CarOwner;
import com.group3.rentngo.model.entity.Customer;
import com.group3.rentngo.service.CarOwnerService;
import com.group3.rentngo.service.CustomerService;
import com.group3.rentngo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author phinx
 * @description controller class contain admin function
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private CarOwnerService carOwnerService;
    private CustomerService customerService;
    private CommonUtil commonUtil;

    public AdminController(UserService userService,
                           CarOwnerService carOwnerService,
                           CustomerService customerService,
                           CommonUtil commonUtil) {
        this.userService = userService;
        this.carOwnerService = carOwnerService;
        this.customerService = customerService;
        this.commonUtil = commonUtil;
    }

    /**
     * @author phinx
     * @description show user management page
     */
    @GetMapping("/list-user")
    public String viewListUser(Model model) {
        List<CarOwner> carOwnerList = carOwnerService.findAll();
        List<Customer> customerList = customerService.findAll();

        model.addAttribute("carOwnerList", carOwnerList);
        model.addAttribute("customerList", customerList);
        return "manage-user-page";
    }

    @GetMapping("/view-car-owner-detail")
    public String viewCarOwnerDetail(@RequestParam("id") Long carOwnerId, Model model) {
        Optional<CarOwner> carOwnerDetail = carOwnerService.findCarOwnerById(carOwnerId);
        CarOwner carOwner = carOwnerDetail.orElse(null);

        UpdateProfileDto updateProfileDto = carOwnerService.getDtoFromCarOwner(carOwner);

        model.addAttribute("role", updateProfileDto.getRole());
        model.addAttribute("updateProfileDto", updateProfileDto);

        return "edit-profile";
    }

    @GetMapping("/view-customer-detail")
    public String viewCustomerDetail(@RequestParam("id") Long customerId, Model model) {
        Optional<Customer> customerDetail = customerService.findCustomerById(customerId);
        Customer customer = customerDetail.orElse(null);

        UpdateProfileDto updateProfileDto = customerService.getDtoFromCustomer(customer);

        model.addAttribute("role", updateProfileDto.getRole());
        model.addAttribute("updateProfileDto", updateProfileDto);

        return "edit-profile";
    }

    @PostMapping("/update-user-profile/{role}")
    public String updateUserInfo(@PathVariable("role") String role,
                                 @Valid @ModelAttribute("updateProfileDto") UpdateProfileDto updateProfileDto,
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
            if (role.equals("ROLE_CAR_OWNER")) {
                carOwnerService.updateProfile(updateProfileDto);
            }
            if (role.equals("ROLE_CUSTOMER")) {

            }
        }
        return "redirect:/admin/view-car-owner-detail?id=" + updateProfileDto.getId();
    }
}
