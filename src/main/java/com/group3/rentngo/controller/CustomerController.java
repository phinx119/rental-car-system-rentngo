package com.group3.rentngo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author phinx
 * @description controller class contain customer function
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
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
     * @description show customer profile page
     */
    @GetMapping("/edit-profile")
    public String editProfile() {
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
