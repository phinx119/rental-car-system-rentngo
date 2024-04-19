package com.group3.rentngo.controller;

import com.group3.rentngo.model.dto.UpdateProfileDto;
import com.group3.rentngo.model.entity.Car;
import com.group3.rentngo.model.entity.CustomUserDetails;
import com.group3.rentngo.model.entity.Customer;
import com.group3.rentngo.model.entity.PaymentHistory;
import com.group3.rentngo.service.CarService;
import com.group3.rentngo.service.CustomerService;
import com.group3.rentngo.service.UserService;
import com.group3.rentngo.service.VNPayService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * @author phinx
 * @description controller class contain customer function
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
    private final UserService userService;
    private final CustomerService customerService;
    private final CarService carService;
    private final VNPayService vnPayService;

    public CustomerController(UserService userService,
                              CustomerService customerService,
                              CarService carService,
                              VNPayService vnPayService) {
        this.userService = userService;
        this.customerService = customerService;
        this.carService = carService;
        this.vnPayService = vnPayService;
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
    @GetMapping("/view-car-list")
    public String viewListCar(Model model) {
        List<Car> carList = carService.findAllCar();
        model.addAttribute("carList", carList);
        return "list-car-search-page";
    }

    /**
     * @author phinx
     * @description show car detail page
     */
    @GetMapping("/display-car-detail/{id}")
    public String viewCarDetail(@PathVariable Long id, Model model) {
        Optional<Car> carOptional = carService.findById(id);
        Car car = carOptional.orElse(null);

        model.addAttribute("car", car);
        return "car-detail";
    }

    /**
     * @author phinx
     * @description show car detail page
     */
    @GetMapping("/rent-car/{id}")
    public String rentCar(@PathVariable Long id, Model model) {
        Optional<Car> carOptional = carService.findById(id);
        Car car = carOptional.orElse(null);

        model.addAttribute("car", car);
        return "book-car";
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
            Customer carOwner = customerService.findCustomerByEmail(userDetails.getUsername());
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

    @GetMapping("/search-car")
    public String searchCarHome(
            @RequestParam("city") String city,
            @RequestParam("pickupDate") String pickupDate,
            @RequestParam("pickupTime") String pickupTime,
            @RequestParam("dropOffDate") String dropOffDate,
            @RequestParam("dropOffTime") String dropOffTime,
            Model model
    ){
        // Tạo đối tượng LocalDateTime từ dữ liệu ngày và giờ của người dùng
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime pickupDateTime = LocalDateTime.parse(pickupDate + " " + pickupTime, formatter);
        LocalDateTime dropOffDateTime = LocalDateTime.parse(dropOffDate + " " + dropOffTime, formatter);
        List<Car> availableCars = carService.findAvailableCars(pickupDateTime, dropOffDateTime, city);
        System.out.println(availableCars);
        // Chuyển kết quả tìm kiếm vào model để hiển thị trên trang kết quả
        model.addAttribute("numberOfCars", availableCars.size());
        model.addAttribute("availableCars", availableCars);
        return "list-car-search-page";
    }
}
