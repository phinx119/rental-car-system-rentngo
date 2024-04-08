package com.group3.rentngo.service;

import com.group3.rentngo.model.entity.PaymentHistory;
import jakarta.servlet.http.HttpServletRequest;

public interface VNPayService {
    String createOrder(int total, String orderType, String urlReturn);
    int orderReturn(HttpServletRequest request);
    void saveNewPaymentHistory(PaymentHistory paymentHistory);
}
