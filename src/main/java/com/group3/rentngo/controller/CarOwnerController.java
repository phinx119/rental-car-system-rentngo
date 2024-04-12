package com.group3.rentngo.controller;

import com.group3.rentngo.model.dto.CarDto;
import com.group3.rentngo.model.dto.UpdateProfileDto;
import com.group3.rentngo.model.entity.*;
import com.group3.rentngo.service.CarOwnerService;
import com.group3.rentngo.service.CarService;
import com.group3.rentngo.service.UserService;
import com.group3.rentngo.service.VNPayService;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
     * @author tiennq
     * @description show add car form
     */
    @GetMapping("/add-car-form")
    public String addNewCar(Model model) {
        CarDto car = new CarDto();
        model.addAttribute("car", car);
        return "add-car-page";
    }

    /**
     * @author thiendd
     * @description show list car of owner
     */
    @GetMapping("/view-list-car/{id}")
    public String listCarOfOwner(Model model, @PathVariable long id) {
        CarOwner carOwner = carOwnerService.findCarOwnerByIdUser(id);
        List<Car> list = carService.listCarOfOwner(carOwner.getId());
        model.addAttribute("list", list);
        return "list-car-page";
    }

    /**
     * @author thiendd
     * @description
     */
    @GetMapping("/display-car-detail/{id}")
    public String carDetail(Model model, @PathVariable long id) {
        Optional<Car> carOptional = carService.findbyId(id);
        Car car = carOptional.orElse(null);
        String carOption = car.getAdditionalFunctions();
        String carTerm = car.getTermOfUse();
        model.addAttribute("carRegistrationPaper", car.getRegistrationPaperPath());
        model.addAttribute("carCertificateOfInspection", car.getCertificateOfInspectionPath());
        model.addAttribute("carInsurance", car.getInsurancePath());
        System.out.println(car.getRegistrationPaperPath()+" " + car.getCertificateOfInspectionPath()+ " " +car.getInsurancePath());
        model.addAttribute("carTerm",carTerm);
        model.addAttribute("carOption",carOption);
        model.addAttribute("car", car);
        return "car-detail";
    }


    /**
     * @author thiendd
     * @description
     */
    @GetMapping("/edit-car/{id}")
    public String editCarDetail(Model model, @PathVariable long id) {
        Optional<Car> carOptional = carService.findbyId(id);
        Car car = carOptional.orElse(null);

        model.addAttribute("car", car);
        return "edit-car-detial-page";
    }

    /**
     * @author thiendd
     * @description
     */
    @PostMapping("/edit-car/{id}")
    public String editCarInfoDetail(Model model, @PathVariable long id) {
        Optional<Car> carOptional = carService.findbyId(id);
        Car car = carOptional.orElse(null);
        model.addAttribute("car", car);
        return "edit-car-detial-page";
    }

    /**
     * @author thiendd
     * @description
     */

    @PostMapping(path = "/addnewcar", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String addNewCar(@ModelAttribute("car") CarDto car,
                            @RequestPart("registrationPaper") MultipartFile registrationPaper,
                            @RequestPart("inspectionCertificate") MultipartFile inspectionCertificate,
                            @RequestPart("insurance") MultipartFile insurance,
                            @RequestPart("frontImage") MultipartFile frontImage,
                            @RequestPart("backImage") MultipartFile backImage,
                            @RequestPart("leftImage") MultipartFile leftImage,
                            @RequestPart("rightImage") MultipartFile rightImage,
                            Model model,
                            BindingResult bindingResult) {

        if (car.getName() == null || car.getName().isEmpty()) {
            bindingResult.rejectValue("name", null, "Name is required");
        }

        if (car.getLicensePlate() == null || car.getLicensePlate().isEmpty()) {
            bindingResult.rejectValue("licensePlate", null, "License plate is required");
        }

        if (car.getBrand() == null || car.getBrand().isEmpty()) {
            bindingResult.rejectValue("brand", null, "Brand is required");
        }

        if (car.getModel() == null || car.getModel().isEmpty()) {
            bindingResult.rejectValue("model", null, "Model is required");
        }

        if (car.getColor() == null || car.getColor().isEmpty()) {
            bindingResult.rejectValue("color", null, "Color is required");
        }

        if (car.getNumberOfSeats() <= 0) {
            bindingResult.rejectValue("numberOfSeats", null, "Number of seats must be positive");
        }

        if (car.getProductionYear() <= 0) {
            bindingResult.rejectValue("productionYear", null, "Production year must be positive");
        }

        if (car.getTransmissionType() == null || car.getTransmissionType().isEmpty()) {
            bindingResult.rejectValue("transmissionType", null, "Transmission type is required");
        }

        if (car.getFuelType() == null || car.getFuelType().isEmpty()) {
            bindingResult.rejectValue("fuelType", null, "Fuel type is required");
        }

        if (car.getMileage() <= 0) {
            bindingResult.rejectValue("mileage", null, "Mileage must be positive");
        }

        if (car.getFuelConsumption() == null || car.getFuelConsumption().isEmpty()) {
            bindingResult.rejectValue("fuelConsumption", null, "Fuel consumption is required");
        }

        if (car.getBasePrice() <= 0) {
            bindingResult.rejectValue("basePrice", null, "Base price must be positive");
        }

        if (car.getDeposit() <= 0) {
            bindingResult.rejectValue("deposit", null, "Deposit must be positive");
        }

        if (car.getAddress() == null || car.getAddress().isEmpty()) {
            bindingResult.rejectValue("address", null, "Address is required");
        }

        if (car.getDescription() == null || car.getDescription().isEmpty()) {
            bindingResult.rejectValue("description", null, "Description is required");
        }

        if (car.getAdditionalFunctions() == null || car.getAdditionalFunctions().isEmpty()) {
            bindingResult.rejectValue("additionalFunctions", null, "Additional functions are required");
        }

        if (car.getTermOfUse() == null || car.getTermOfUse().isEmpty()) {
            bindingResult.rejectValue("termOfUse", null, "Term of use is required");
        }

        if (car.getRegistrationPaper() == null) {
            bindingResult.rejectValue("registrationPaper", null, "Registration paper is required");
        }

        if (car.getCertificateOfInspection() == null) {
            bindingResult.rejectValue("certificateOfInspection", null, "Certificate of inspection is required");
        }

        if (car.getInsurance() == null) {
            bindingResult.rejectValue("insurance", null, "Insurance is required");
        }

        if (car.getFrontImage() == null) {
            bindingResult.rejectValue("frontImage", null, "Front image is required");
        }

        if (car.getBackImage() == null) {
            bindingResult.rejectValue("backImage", null, "Back image is required");
        }

        if (car.getLeftImage() == null) {
            bindingResult.rejectValue("leftImage", null, "Left image is required");
        }

        if (car.getRightImage() == null) {
            bindingResult.rejectValue("rightImage", null, "Right image is required");
        }
        String checkLicensePlate = carOwnerService.findCarByLicensePlate(car.getLicensePlate());
        if (checkLicensePlate != null) {
            bindingResult.rejectValue("licensePlate", null, "Biển số đã tồn tại trong hệ thống");
            return "add-car-page";
        }

        else{
            String saveLocation = "src/main/resources/static/images/document";
            String saveLocationCarImage = "src/main/resources/static/images/car";
            CarImage carImage = new CarImage();
            CarOwner carOwner = new CarOwner();
            UserDetails userDetails = userService.getUserDetail();
            carOwner = carOwnerService.findCarOwnerByEmail(userDetails.getUsername());
            try {
                car.setRegistrationPaperPath(carService.storeFile(saveLocation, registrationPaper));
                car.setCertificateOfInspectionPath(carService.storeFile(saveLocation, inspectionCertificate));
                car.setInsurancePath(carService.storeFile(saveLocation, insurance));
                carImage.setFrontImagePath(carService.storeFile(saveLocationCarImage, frontImage));
                carImage.setBackImagePath(carService.storeFile(saveLocationCarImage, backImage));
                carImage.setLeftImagePath(carService.storeFile(saveLocationCarImage, leftImage));
                carImage.setRightImagePath(carService.storeFile(saveLocationCarImage, rightImage));
                car.setCarOwner(carOwner);
                carService.addCarImage(carImage);
                carService.addCar(car, carImage);
            } catch (Exception e) {
                System.out.println(e);
            }
            return "redirect:/home";
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