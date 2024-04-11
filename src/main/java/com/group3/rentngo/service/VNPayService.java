package com.group3.rentngo.service;

import com.group3.rentngo.model.entity.PaymentHistory;
import jakarta.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.util.List;

public interface VNPayService {
    String createOrder(int total, String orderType, String urlReturn);

    int orderReturn(HttpServletRequest request);

    void saveNewPaymentHistory(String orderType, String totalPrice, String paymentTime) throws ParseException;

    List<PaymentHistory> findAll();
}
