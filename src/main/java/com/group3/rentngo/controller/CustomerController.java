package com.group3.rentngo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group3.rentngo.model.dto.BookingDto;
import com.group3.rentngo.model.dto.CustomerDto;
import com.group3.rentngo.model.dto.UpdateBookingDetailDto;
import com.group3.rentngo.model.dto.UpdateProfileDto;
import com.group3.rentngo.model.entity.*;
import com.group3.rentngo.service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.util.*;

/**
 * @author phinx
 * @description controller class contain customer function
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
    private final UserService userService;
    private final CustomerService customerService;
    private final BookingService bookingService;
    private final CarService carService;
    private final VNPayService vnPayService;
    private final HttpSession session;

    public CustomerController(UserService userService,
                              CustomerService customerService,
                              BookingService bookingService,
                              CarService carService,
                              VNPayService vnPayService,
                              HttpSession session) {
        this.userService = userService;
        this.customerService = customerService;
        this.bookingService = bookingService;
        this.carService = carService;
        this.vnPayService = vnPayService;
        this.session = session;
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
        UpdateProfileDto updateProfileDto = customerService.getUpdateProfileDtoFromCustomer(customer);
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
        UserDetails userDetails = userService.getUserDetail();
        Customer customer = customerService.findCustomerByEmail(userDetails.getUsername());

        if (customer.getDrivingLicense() == null) {
            return "redirect:/customer/view-customer-detail?incompleteProfileInformation";
        }

        CustomerDto customerDto = customerService.getRenterFromCustomer(customer);

        Optional<Car> carOptional = carService.findById(id);
        Car car = carOptional.orElse(null);

        BookingDto bookingDto = new BookingDto();
        bookingDto.setCustomerDto(customerDto);
        bookingDto.setCarId(car.getId());

        UpdateBookingDetailDto updateBookingDetailDto = new UpdateBookingDetailDto();

        if (session.getAttribute("updateBookingDetailDto") != null) {
            updateBookingDetailDto = (UpdateBookingDetailDto) session.getAttribute("updateBookingDetailDto");
        }

        model.addAttribute("car", car);
        model.addAttribute("bookingDto", bookingDto);
        model.addAttribute("updateBookingDetailDto", updateBookingDetailDto);
        return "book-car";
    }

    /**
     * @author phinx
     * @description change booking detail
     */
    @PostMapping("/change-booking-detail")
    @ResponseBody
    public ResponseEntity<?> changeBookingDetail(@Valid @ModelAttribute("updateBookingDetailDto") UpdateBookingDetailDto updateBookingDetailDto,
                                              BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", true,
                    "errors", getErrorMessages(result)
            ));
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            session.setAttribute("updateBookingDetailDto", updateBookingDetailDto);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "updateBookingDetailDto", updateBookingDetailDto
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", true,
                    "message", "Failed to convert object to JSON"
            ));
        }
    }

    /**
     * @author phinx
     * @description turn error from binding result to json
     */
    private Map<String, String> getErrorMessages(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return errors;
    }

    /**
     * @author phinx
     * @description change booking detail
     */
    @PostMapping("/create-new-booking")
    @ResponseBody
    public ResponseEntity<?> createNewBooking(@Valid @ModelAttribute("bookingDto") BookingDto bookingDto,
                                   BindingResult result) {
        UpdateBookingDetailDto updateBookingDetailDto = (UpdateBookingDetailDto) session.getAttribute("updateBookingDetailDto");
        if (updateBookingDetailDto == null) {
            result.rejectValue("updateBookingDetailDto", null, "Mandatory information.");
        }

        Optional<Car> carOptional = carService.findById(bookingDto.getCarId());
        Car car = carOptional.orElse(null);
        if (car == null) {
            result.rejectValue("car", null, "Mandatory information.");
        }

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", true,
                    "errors", getErrorMessages(result)
            ));
        }

        bookingDto.setUpdateBookingDetailDto(updateBookingDetailDto);
        bookingService.createNewBooking(bookingDto);
        Booking booking = bookingService.findLastestBooking();
        bookingDto.setId(booking.getId());

        try {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "bookingDto", bookingDto
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of(
                    "error", true,
                    "errors", "Failed to convert object to JSON"
            ));
        }
    }

    /**
     * @author phinx
     * @description show booking detail page
     */
    @GetMapping("/display-booking-detail/{id}")
    public String viewBookingDetail(@PathVariable Long id, Model model) {
        Booking booking = bookingService.findById(id);

        model.addAttribute("booking", booking);
        return "booking-detail-page";
    }

    /**
     * @author phinx
     * @description show booking list page
     */
    @GetMapping("/view-booking")
    public String viewListBooking(Model model) {
        UserDetails userDetails = userService.getUserDetail();
        Customer customer = customerService.findCustomerByEmail(userDetails.getUsername());

        List<Booking> bookingList = bookingService.findAll(customer.getId());

        model.addAttribute("bookingList", bookingList);
        return "list-booking-page";
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
            Customer customer = customerService.findCustomerByEmail(userDetails.getUsername());
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
            if (customer.getWallet() != null) {
                model.addAttribute("wallet", numberFormat.format(customer.getWallet()));
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
