package com.group3.rentngo.controller;

import com.group3.rentngo.model.entity.PaymentHistory;
import com.group3.rentngo.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author phinx
 * @description controller class contain vnpay payment function
 */
@Controller
@RequestMapping("/vnpay")
public class VNPayController {
    private final VNPayService vnPayService;

    public VNPayController(VNPayService vnPayService) {
        this.vnPayService = vnPayService;
    }

    /**
     * @author phinx
     * @description method show view wallet page
     */
    @GetMapping("/view-wallet")
    public String viewWallet() {
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
    public String GetMapping(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderType = request.getParameter("vnp_OrderType");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderType);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        if (paymentStatus == 1) {
            PaymentHistory paymentHistory = new PaymentHistory();
            paymentHistory.setAmount(BigDecimal.valueOf(Long.parseLong(totalPrice)));
            paymentHistory.setType(orderType);
            paymentHistory.setCreateDate(Date.valueOf(paymentTime));

            vnPayService.saveNewPaymentHistory(paymentHistory);

            return "vnpay/order-success";
        } else {
            return "vnpay/order-fail";
        }

    }
}