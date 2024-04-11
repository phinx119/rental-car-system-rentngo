package com.group3.rentngo.service.impl;

import com.group3.rentngo.common.CommonUtil;
import com.group3.rentngo.config.VNPayConfig;
import com.group3.rentngo.model.entity.PaymentHistory;
import com.group3.rentngo.model.entity.User;
import com.group3.rentngo.repository.PaymentHistoryRepository;
import com.group3.rentngo.service.CarOwnerService;
import com.group3.rentngo.service.CustomerService;
import com.group3.rentngo.service.UserService;
import com.group3.rentngo.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VNPayServiceImpl implements VNPayService {

    private final PaymentHistoryRepository paymentHistoryRepository;
    private final UserService userService;
    private final CarOwnerService carOwnerService;
    private final CustomerService customerService;
    public final CommonUtil commonUtil;
    private final HttpSession session;

    public VNPayServiceImpl(PaymentHistoryRepository paymentHistoryRepository,
                            UserService userService,
                            CarOwnerService carOwnerService,
                            CustomerService customerService,
                            CommonUtil commonUtil,
                            HttpSession session) {
        this.paymentHistoryRepository = paymentHistoryRepository;
        this.userService = userService;
        this.carOwnerService = carOwnerService;
        this.customerService = customerService;
        this.commonUtil = commonUtil;
        this.session = session;
    }

    /**
     * @author phinx
     * @description method create order
     */
    @Override
    public String createOrder(int total, String orderType, String urlReturn) {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        String vnp_OrderType = orderType;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(total * 100));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderType);
        vnp_Params.put("vnp_OrderType", vnp_OrderType);

        String locate = "vn";
        vnp_Params.put("vnp_Locale", locate);

        urlReturn += VNPayConfig.vnp_ReturnUrl;
        vnp_Params.put("vnp_ReturnUrl", urlReturn);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
        return paymentUrl;
    }

    /**
     * @author phinx
     * @description method return order result
     */
    @Override
    public int orderReturn(HttpServletRequest request) {
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
            String fieldName = null;
            String fieldValue = null;
            try {
                fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        String signValue = VNPayConfig.hashAllFields(fields);
        if (signValue.equals(vnp_SecureHash)) {
            if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }

    @Override
    public void saveNewPaymentHistory(String orderType, String totalPrice, String paymentTime) throws ParseException {
        // create new payment history
        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setAmount(BigDecimal.valueOf(Long.parseLong(totalPrice)));
        paymentHistory.setType(orderType);

        // parse string to sql date
        Date createDate = commonUtil.parseDateTime(paymentTime);
        paymentHistory.setCreateDate(createDate);

        // get user information to add to new payment history
        String email = (String) session.getAttribute("email");
        User user = userService.findUserByEmail(email);

        paymentHistory.setUser(user);
        paymentHistoryRepository.save(paymentHistory);

        // update wallet value of car owner
        if (carOwnerService.findCarOwnerByEmail(email) != null) {
            carOwnerService.updateWallet(email, totalPrice);
        }

        // update wallet value of customer
        if (customerService.findCustomerByEmail(email) != null) {
            customerService.updateWallet(email, totalPrice);
        }
    }

    @Override
    public List<PaymentHistory> findAll() {
        return paymentHistoryRepository.findAll();
    }
}
