package com.group3.rentngo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/customer")
public class CustomerController {
    @GetMapping("/customer/list-car")
    public String viewListCar() {
        return "list-car-search-page";
    }
    @GetMapping("/car-detail")
    public String viewCarDetail() {
        return "car-detail";
    }
    @GetMapping("/edit-profile")
    public String editProfile() {
        return "edit-profile";
    }
}
