//package com.course_platform.courses.controller;
//
//import com.course_platform.courses.auth.AuthenticationService;
//import com.course_platform.courses.dto.request.AuthenticationRequest;
//import com.course_platform.courses.dto.request.TokenRequest;
//import com.course_platform.courses.dto.response.ApiResponse;
//import com.course_platform.courses.dto.response.Token;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//
//
//@RequiredArgsConstructor
//public class AuthenticationController {
//    private final AuthenticationService authenticationService;
////    @PostMapping("/register")
////    public ApiResponse<Token> signUp(@RequestBody @Valid AuthenticationRequest authenticationRequest){
////        return ApiResponse.<Token>builder()
////                .result(authenticationService.signUp(authenticationRequest))
////                .build();
////    }
////    @PostMapping("/login")
////    public ApiResponse<Token> login(@RequestBody @Valid AuthenticationRequest authenticationRequest){
////        return ApiResponse.<Token>builder()
////                .result(authenticationService.login(authenticationRequest))
////                .build();
////    }
////    @PostMapping("/refresh-token")
////    public ApiResponse<Token> refreshToken(@RequestBody @Valid TokenRequest tokenRequest){
////        return ApiResponse.<Token>builder()
////                .result(authenticationService.refreshToken(tokenRequest))
////                .build();
////    }
//}
