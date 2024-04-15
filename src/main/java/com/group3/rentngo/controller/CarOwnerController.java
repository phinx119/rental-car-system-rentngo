package com.group3.rentngo.controller;

import com.group3.rentngo.model.dto.CarDto;
import com.group3.rentngo.model.dto.UpdateProfileDto;
import com.group3.rentngo.model.dto.UserDto;
import com.group3.rentngo.model.entity.Car;
import com.group3.rentngo.model.entity.CarOwner;
import com.group3.rentngo.model.entity.CustomUserDetails;
import com.group3.rentngo.model.entity.PaymentHistory;
import com.group3.rentngo.service.CarOwnerService;
import com.group3.rentngo.service.CarService;
import com.group3.rentngo.service.UserService;
import com.group3.rentngo.service.VNPayService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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
    @GetMapping({"/home"})
    public String viewCarOwnerHome(Model model) {
        CustomUserDetails userDetails = userService.getUserDetail();
        if (userDetails != null) {
            long id = userDetails.getId();
            model.addAttribute("id", id);
        }
        return "home-page-as-car-owner";
    }

    /**
     * @author phinx
     * @description show car owner detail
     */
    @GetMapping("/view-car-owner-detail")
    public String viewCarOwnerDetail(Model model) {
        UserDetails userDetails = userService.getUserDetail();

        CarOwner carOwner = carOwnerService.findCarOwnerByEmail(userDetails.getUsername());

        UpdateProfileDto updateProfileDto = carOwnerService.getDtoFromCarOwner(carOwner);

        model.addAttribute("updateProfileDto", updateProfileDto);

        return "edit-profile";
    }

    /**
     * @author thiendd
     * @description show list car of owner
     */
    @GetMapping("/view-list-car")
    public String listCarOfOwner(Model model) {
        UserDetails userDetails = userService.getUserDetail();

        CarOwner carOwner = carOwnerService.findCarOwnerByEmail(userDetails.getUsername());

        List<Car> carList = carService.listCarOfOwner(carOwner.getId());
        model.addAttribute("carList", carList);
        return "list-car-page";
    }

    /**
     * @author thiendd
     * @description
     */
    @GetMapping("/display-car-detail/{id}")
    public String carDetail(Model model, @PathVariable long id) {
        Optional<Car> carOptional = carService.findById(id);
        Car car = carOptional.orElse(null);
        String carOption = car.getAdditionalFunctions();
        String carTerm = car.getTermOfUse();
        model.addAttribute("carRegistrationPaper", car.getRegistrationPaperPath());
        model.addAttribute("carCertificateOfInspection", car.getCertificateOfInspectionPath());
        model.addAttribute("carInsurance", car.getInsurancePath());
        System.out.println(car.getRegistrationPaperPath() + " " + car.getCertificateOfInspectionPath() + " " + car.getInsurancePath());
        model.addAttribute("carTerm", carTerm);
        model.addAttribute("carOption", carOption);
        model.addAttribute("car", car);
        return "car-detail";
    }


    /**
     * @author thiendd
     * @description
     */
    @GetMapping("/edit-car/{id}")
    public String editCarDetail(Model model, @PathVariable Long id) {
        Optional<Car> carOptional = carService.findById(id);
        Car car = carOptional.orElse(null);
        CarDto carDto = carService.getCarDtoFromCar(car);
        String address = carDto.getHouseNumberAndStreet().concat("-")
                        .concat(carDto.getWard()).concat("-")
                        .concat(carDto.getDistrict()).concat("-")
                        .concat(carDto.getCity());
        String carTerm = car.getTermOfUse();
        String carOption = car.getAdditionalFunctions();
        model.addAttribute("carOption", carOption);
        model.addAttribute("address", address);
        model.addAttribute("carDto", carDto);
        System.out.println( carDto.getCertificateOfInspectionPath());
        model.addAttribute("certificate", carDto.getCertificateOfInspectionPath());
        model.addAttribute("insurance", carDto.getInsurancePath());
        model.addAttribute("registration", carDto.getRegistrationPaperPath());
        model.addAttribute("carTerm", carTerm);
        return "edit-car-detail-page";
    }

    @GetMapping("/edit-car-detail/{id}")
    public String editCar(Model model, @PathVariable Long id,
                          @Valid @ModelAttribute("carDto") CarDto carDto,
                          BindingResult result) {
        Optional<Car> findCar = carService.findById(id);
        Car findByLicensePLate = carOwnerService.findCarByLicensePlate(carDto.getLicensePlate());
        if(findByLicensePLate !=null && !carDto.getLicensePlate().equalsIgnoreCase(findCar.get().getLicensePlate()) ){
            result.rejectValue("licensePlate",null,"The License Plate is wrong");
        }
        if (result.hasErrors()) {
            System.out.println("123");
            model.addAttribute("carDto", carDto);
            return "edit-car-detail-page";
        } else {
            Car car = findCar.orElse(null);
            carService.editCar(car,carDto);
            return "redirect:/car-owner/view-list-car";
        }

    }
    /**
     * @author tiennq
     * @description show add car form
     */
    @GetMapping("/add-car-form")
    public String addNewCar(Model model) {
        CarDto carDto = new CarDto();
        model.addAttribute("carDto", carDto);
        return "add-car-page";
    }

    /**
     * @author tiennq
     * @description
     */
    @PostMapping(path = "/add-new-car", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String addNewCar(@Valid @ModelAttribute("carDto") CarDto carDto,
                            BindingResult result,
                            Model model) {
        if (carDto.getLicensePlate() != null || !carDto.getLicensePlate().isEmpty()) {
            if (carOwnerService.findCarByLicensePlate(carDto.getLicensePlate()) != null) {
                result.rejectValue("licensePlate", null, "License plate already existed. Please try another one.");
            }
        }

        if (carDto.getRegistrationPaper().isEmpty()){
            result.rejectValue("registrationPaper", null, "This field is required.");
        }

        if (carDto.getCertificateOfInspection().isEmpty()){
            result.rejectValue("certificateOfInspection", null, "This field is required.");
        }

        if (carDto.getInsurance().isEmpty()){
            result.rejectValue("insurance", null, "This field is required.");
        }

        if (carDto.getFrontImage().isEmpty()){
            result.rejectValue("frontImage", null, "This field is required.");
        }

        if (carDto.getBackImage().isEmpty()){
            result.rejectValue("backImage", null, "This field is required.");
        }

        if (carDto.getLeftImage().isEmpty()){
            result.rejectValue("leftImage", null, "This field is required.");
        }

        if (carDto.getRightImage().isEmpty()){
            result.rejectValue("rightImage", null, "This field is required.");
        }

        if (result.hasErrors()) {
            model.addAttribute("carDto", carDto);
            return "add-car-page";
        } else {
            carService.addCar(carDto);

            return "redirect:/car-owner/view-list-car";
        }
    }

    /**
     * @author phinx
     * @description show user wallet
     */
    @GetMapping("/view-wallet")
    public String viewWallet(Model model) {
        // get wallet
        CustomUserDetails userDetails = userService.getUserDetail();
        if (userDetails != null) {
            CarOwner carOwner = carOwnerService.findCarOwnerByEmail(userDetails.getUsername());
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
            if (carOwner.getWallet() != null) {
                model.addAttribute("wallet", numberFormat.format(carOwner.getWallet()));
            } else {
                model.addAttribute("wallet", "0");
            }
        }
        // get payment history list
        List<PaymentHistory> paymentHistoryList = vnPayService.findAll();
        model.addAttribute("paymentHistoryList", paymentHistoryList);

        return "vnpay/view-wallet";
    }
}