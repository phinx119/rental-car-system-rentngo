package com.group3.rentngo.controller;

import com.group3.rentngo.model.dto.UpdateProfileDto;
import com.group3.rentngo.model.entity.Customer;
import com.group3.rentngo.service.CustomerService;
import com.group3.rentngo.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author phinx
 * @description controller class contain customer function
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
    private final UserService userService;
    private final CustomerService customerService;

    public CustomerController(UserService userService,
                              CustomerService customerService) {
        this.userService = userService;
        this.customerService = customerService;
    }

    /**
     * @author phinx
     * @description show home page for role cuatomer
     */
    @GetMapping("/home")
    public String viewCarOwnerHome() {
        return "home-page-as-customer";
    }

    /**
     * @author phinx
     * @description show customer detail
     */
    @GetMapping("/view-customer-detail")
    public String viewCarOwnerDetail(Model model) {
        UserDetails userDetails = userService.getUserDetail();

        Customer customer = customerService.findCustomerByEmail(userDetails.getUsername());

        UpdateProfileDto updateProfileDto = customerService.getDtoFromCustomer(customer);

        model.addAttribute("updateProfileDto", updateProfileDto);

        return "edit-profile";
    }

    /**
     * @author phinx
     * @description show list car page
     */
    @GetMapping("/list-car")
    public String viewListCar() {
        return "list-car-search-page";
    }

    /**
     * @author phinx
     * @description show car detail page
     */
    @GetMapping("/car-detail")
    public String viewCarDetail() {
        return "car-detail";
    }
}
