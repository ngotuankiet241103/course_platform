package com.course_platform.courses.controller;

import com.course_platform.courses.dto.request.OrderRequest;
import com.course_platform.courses.dto.response.ApiResponse;
import com.course_platform.courses.dto.response.Course;
import com.course_platform.courses.service.CourseService;
import com.course_platform.courses.service.OrderService;
import com.course_platform.courses.service.PaymentService;
import com.course_platform.courses.utils.HtmlComponent;
import com.course_platform.courses.utils.Payment;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;
import java.net.URI;

@Controller
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CourseService courseService;
    private final PaymentService paymentService;
    private final SimpMessagingTemplate messagingTemplate;
    @PostMapping
    public void create(@RequestBody @Valid OrderRequest orderRequest,
                                    @RequestParam("redirect_url") String redirectUrl,
                                    Authentication authentication
                                   ) throws Exception {
        boolean isFree = courseService.isFree(orderRequest.getCourseId());
        RedirectView redirectView = new RedirectView();
        String code = Payment.getRandomNumber(9);
        orderService.create(orderRequest,code);
        if(!isFree){

            String paymentUrl = paymentService.payment(orderRequest.getCourseId(),code);
            redirectView.setUrl(paymentUrl);
            messagingTemplate.convertAndSend("/topic/orders/"+ authentication.getName() + "/messages", redirectView);
            String url = orderService.generateUrl(code,redirectUrl);
            redirectView.setUrl(url);

            messagingTemplate.convertAndSend("/topic/orders/"+ authentication.getName() + "/messages", redirectView);

        }

    }
    @GetMapping("/payment")
    public ResponseEntity<?> getPayment(@RequestParam("vnp_TxnRef") String code,
                           @RequestParam("vnp_TransactionStatus") String status){
        orderService.updateStatus(code,status);

        return ResponseEntity.ok(HtmlComponent.closeTab);
    }

}
