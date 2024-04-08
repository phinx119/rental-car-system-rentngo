package com.group3.rentngo.controller;

import com.group3.rentngo.model.dto.CarDto;
import com.group3.rentngo.model.dto.CarOwnerDto;
import com.group3.rentngo.model.entity.Car;
import com.group3.rentngo.service.impl.CarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

/**
 * @author phinx
 * @description controller class contain car owner function
 */
@Controller
@RequestMapping("/car-owner")
public class CarOwnerController {
    /**
     * @author phinx
     * @description show home page for role car owner
     */
    @GetMapping("/home")
    public String viewCarOwnerHome() {
        return "home-page-as-car-owner";
    }

    /**
     * @author tiennq
     * @description show add car form
     */
    @GetMapping("/add-car-form")
    public String addNewCar(Model model) {
        CarDto car = new CarDto();
        model.addAttribute("car", car);
        return "add-car-page";
    }

    @Autowired
    CarServiceImpl carService;


    @PostMapping("/addnewcar")
    public String addNewCar(@RequestParam("registrationPaper") MultipartFile registrationPaper,
                            @ModelAttribute("car") CarDto car) {

        String saveLocation = "src/main/resources/static/images/car";
        String registrationPaperName = registrationPaper.getOriginalFilename();
        String registrationPaperNewName = UUID.randomUUID().toString() + registrationPaperName;
        try {
            File saveLocationFile = new File(saveLocation);
            if (!saveLocationFile.exists()) {
                saveLocationFile.mkdirs();
            }
            File registrationPaperFile = new File(saveLocation + "/" + registrationPaperNewName);
            FileOutputStream fos = new FileOutputStream(registrationPaperFile);
            fos.write(registrationPaper.getBytes());
            fos.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        // car.setRegistrationPaper(saveLocation + "/" + registrationPaperNewName);
        carService.addCar(car);

        return "redirect:/home";

    }

}



