package com.group3.rentngo.controller;

import com.group3.rentngo.model.dto.CarDto;
import com.group3.rentngo.model.entity.Car;
import com.group3.rentngo.model.entity.CarOwner;
import com.group3.rentngo.model.entity.CustomUserDetails;
import com.group3.rentngo.model.entity.PaymentHistory;
import com.group3.rentngo.service.CarOwnerService;
import com.group3.rentngo.service.CarService;
import com.group3.rentngo.service.UserService;
import com.group3.rentngo.service.VNPayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

/**
 * @author phinx
 * @description controller class contain car owner function
 */
@Controller
@RequestMapping("/car-owner")
public class CarOwnerController {
    private final UserService userService;
    private final CarService carService;
    private final CarOwnerService carOwnerService;
    private final VNPayService vnPayService;

    public CarOwnerController(UserService userService,
                              CarService carService,
                              CarOwnerService carOwnerService,
                              VNPayService vnPayService) {
        this.userService = userService;
        this.carService = carService;
        this.carOwnerService = carOwnerService;
        this.vnPayService = vnPayService;
    }

    /**
     * @author phinx
     * @description show home page for role car owner
     */
    @GetMapping({ "/home"})
    public String viewCarOwnerHome(Model model) {
        CustomUserDetails userDetails = userService.getUserDetail();
        if (userDetails != null) {
            long id = userDetails.getId();
            model.addAttribute("id", id);
        }
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

    //List car of owner
    @GetMapping("/view-list-car/{id}")
    public String listCarOfOwner(Model model, @PathVariable long id) {
        CarOwner carOwner = carOwnerService.findCarOwnerByIdUser(id);
        List<Car> list = carService.listCarOfOwner(carOwner.getId());
        model.addAttribute("list", list);
        return "list-car-page";
    }

    @GetMapping("/display-car-detail/{id}")
    public String carDetail(Model model, @PathVariable long id) {
        Optional<Car> carOptional = carService.findbyId(id);
        Car car = carOptional.orElse(null);

        model.addAttribute("car", car);
        return "car-detail";
    }

    @GetMapping("/edit-car/{id}")
    public String editCarDetail(Model model, @PathVariable long id) {
        Optional<Car> carOptional = carService.findbyId(id);
        Car car = carOptional.orElse(null);

        model.addAttribute("car", car);
        return "edit-car-detial-page";
    }
    @PostMapping("/edit-car/{id}")
    public String editCarInfoDetail(Model model, @PathVariable long id) {
        Optional<Car> carOptional = carService.findbyId(id);
        Car car = carOptional.orElse(null);

        model.addAttribute("car", car);
        return "edit-car-detial-page";
    }

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

    @GetMapping("/view-wallet")
    public String viewWallet(Model model) {
        // get wallet
        CustomUserDetails userDetails = userService.getUserDetail();
        if (userDetails != null) {
            CarOwner carOwner = carOwnerService.findCarOwnerByEmail(userDetails.getUsername());
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
            model.addAttribute("wallet", numberFormat.format(carOwner.getWallet()));
        } else {
            model.addAttribute("wallet", "No data.");
        }
        // get payment history list
        List<PaymentHistory> paymentHistoryList = vnPayService.findAll();
        model.addAttribute("paymentHistoryList", paymentHistoryList);

        return "vnpay/view-wallet";
    }
}



