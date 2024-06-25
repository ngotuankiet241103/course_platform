package com.course_platform.courses.service.impl;


import com.course_platform.courses.entity.CourseEntity;
import com.course_platform.courses.entity.OrderEntity;
import com.course_platform.courses.exception.CustomRuntimeException;
import com.course_platform.courses.exception.ErrorCode;
import com.course_platform.courses.repository.CourseRepository;
import com.course_platform.courses.repository.OrderRepository;
import com.course_platform.courses.service.PaymentService;
import com.course_platform.courses.utils.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final CourseRepository courseRepository;
    private final OrderRepository orderRepository;
    @Value("${vnPay.version}")
    private String version;
    @Value("${vnPay.command}")
    private String vnpCommand;
    @Value("${vnPay.tmnCode}")
    private String tmnCode;
    private String txnRef;
    @Value("${vnPay.returnUrl}")
    private String returnUrl;
    @Value("${vnPay.ipAddress}")
    private String ipAddress;
    @Value("${vnPay.hashSecret}")
    private String hashSecret;
    @Value("${vnPay.url}")
    private String payUrl;


    @Override
    public String payment(String courseId, String code) throws UnsupportedEncodingException {
        txnRef = code;
        CourseEntity course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.COURSE_NOT_FOUND));
        Map<String,String> params = generateParams(course);
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName =  itr.next();
            String fieldValue = params.get(fieldName);
            if ((fieldValue != null) && !fieldValue.isEmpty()) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                // Build query
                query.append(fieldName);
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        System.out.println(queryUrl);
        System.out.println(hashData.toString());
        String vnp_SecureHash = Payment.hmacSHA512(hashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        return payUrl+ "?" + queryUrl;
    }



    private Map<String,String> generateParams(CourseEntity course){
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", version);
        vnp_Params.put("vnp_Command", vnpCommand);
        vnp_Params.put("vnp_TmnCode", tmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf((int) course.getPrice() * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_TxnRef", txnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + txnRef);
        vnp_Params.put("vnp_ReturnUrl", returnUrl);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_IpAddr", "127.0.0.1");
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE,15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        return vnp_Params;
    }
}
