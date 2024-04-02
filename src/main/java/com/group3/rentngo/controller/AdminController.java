package com.group3.rentngo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author phinx
 * @description controller class contain admin function
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    /**
     * @author phinx
     * @description show user management page
     */
    @GetMapping("/list-user")
    public String viewListUser(){
        return "manage-user-page";
    }
}
