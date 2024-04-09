package com.group3.rentngo.controller;

import com.group3.rentngo.model.entity.CustomUserDetails;
import com.group3.rentngo.service.UserService;
import com.group3.rentngo.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;

/**
 * @author phinx
 * @description controller class contain vnpay payment function
 */
@Controller
@RequestMapping("/vnpay")
public class VNPayController {
    private final UserService userService;
    private final VNPayService vnPayService;

    public VNPayController(UserService userService,
                           VNPayService vnPayService) {
        this.userService = userService;
        this.vnPayService = vnPayService;
    }

    /**
     * @author phinx
     * @description method show view wallet page
     */
    @GetMapping("/view-wallet")
    public String viewWallet(Model model) {
        CustomUserDetails userDetails = userService.getUserDetail();
        if (userDetails != null) {
            boolean isAdmin = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            boolean isCarOwner = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CAR_OWNER"));
            boolean isCustomer = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"));
            if (isAdmin) {
                return "redirect:/admin/list-user";
            }
            if (isCarOwner) {
                return "redirect:/car-owner/view-wallet";
            }
            if (isCustomer) {
                return "redirect:/customer/view-wallet";
            }
        }
        return "vnpay/view-wallet";
    }

    /**
     * @author phinx
     * @description method call service to create order
     */
    @PostMapping("/submit-order")
    public String submitOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("transactionType") String orderType,
                              HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(orderTotal, orderType, baseUrl);
        return "redirect:" + vnpayUrl;
    }

    /**
     * @author phinx
     * @description method check result of order to send user to suitable page
     */
    @GetMapping("/payment")
    public String GetMapping(HttpServletRequest request, Model model) throws ParseException {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderType = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount").substring(0, request.getParameter("vnp_Amount").length() - 2);;

        model.addAttribute("orderType", orderType);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        if (paymentStatus == 1) {
            vnPayService.saveNewPaymentHistory(orderType, totalPrice, paymentTime);
            return "vnpay/order-success";
        } else {
            return "vnpay/order-fail";
        }

    }
}