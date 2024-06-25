package com.course_platform.courses.service;

import java.io.UnsupportedEncodingException;

public interface PaymentService {
    String payment(String courseId, String redirectUrl) throws UnsupportedEncodingException;


}
