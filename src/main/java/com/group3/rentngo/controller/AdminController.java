package com.group3.rentngo.controller;

import com.group3.rentngo.model.entity.CarOwner;
import com.group3.rentngo.model.entity.Customer;
import com.group3.rentngo.model.entity.User;
import com.group3.rentngo.service.CarOwnerService;
import com.group3.rentngo.service.CustomerService;
import com.group3.rentngo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

    public AdminController(UserService userService,
                           CarOwnerService carOwnerService,
                           CustomerService customerService) {
        this.userService = userService;
        this.carOwnerService = carOwnerService;
        this.customerService = customerService;
    }

    /**
     * @author phinx
     * @description show user management page
     */
    @GetMapping("/list-user")
    public String viewListUser(Model model){
        List<CarOwner> carOwnerList = carOwnerService.findAll();
        List<Customer> customerList = customerService.findAll();

        model.addAttribute("carOwnerList", carOwnerList);
        model.addAttribute("customerList", customerList);
        return "manage-user-page";
    }
}
