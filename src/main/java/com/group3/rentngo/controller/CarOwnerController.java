package com.group3.rentngo.controller;

import com.group3.rentngo.model.dto.CarDto;
import com.group3.rentngo.model.dto.CarOwnerDto;
import com.group3.rentngo.model.entity.Car;
import com.group3.rentngo.service.impl.CarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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


    @PostMapping(path = "/addnewcar", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String addNewCar(@ModelAttribute("car") CarDto car,
                            @RequestPart("registrationPaper") MultipartFile registrationPaper,
                            @RequestPart("inspectionCertificate") MultipartFile inspectionCertificate,
                            @RequestPart("insurance") MultipartFile insurance) {
        String saveLocation = "src/main/resources/static/images/car";

        try {
            // Lưu registrationPaper
            String registrationPaperName = registrationPaper.getOriginalFilename();
            String registrationPaperNewName = UUID.randomUUID().toString() + registrationPaperName;
            File registrationPaperFile = new File(saveLocation + "/" + registrationPaperNewName);
            FileOutputStream registrationFos = new FileOutputStream(registrationPaperFile);
            registrationFos.write(registrationPaper.getBytes());
            registrationFos.close();
            car.setRegistrationPaperPath(saveLocation + "/" + registrationPaperNewName);

            // Lưu inspectionCertificate
            String inspectionCertificateName = inspectionCertificate.getOriginalFilename();
            String inspectionCertificateNewName = UUID.randomUUID().toString() + inspectionCertificateName;
            File inspectionCertificateFile = new File(saveLocation + "/" + inspectionCertificateNewName);
            FileOutputStream inspectionFos = new FileOutputStream(inspectionCertificateFile);
            inspectionFos.write(inspectionCertificate.getBytes());
            inspectionFos.close();
            car.setCertificateOfInspectionPath(saveLocation + "/" + inspectionCertificateNewName);

             //Lưu insurance
            String insuranceName = insurance.getOriginalFilename();
            String insuranceNewName = UUID.randomUUID().toString() + insuranceName;
            File insuranceFile = new File(saveLocation + "/" + insuranceNewName);
            FileOutputStream insuranceFos = new FileOutputStream(insuranceFile);
            insuranceFos.write(insurance.getBytes());
            insuranceFos.close();
            car.setInsurancePath(saveLocation + "/" + insuranceNewName);

            carService.addCar(car);

        } catch (Exception e) {
            System.out.println(e);
        }

        return "redirect:/home";
    }


}



