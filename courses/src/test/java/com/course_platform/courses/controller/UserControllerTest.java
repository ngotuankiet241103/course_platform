package com.course_platform.courses.controller;

import com.course_platform.courses.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class UserControllerTest {
    @MockBean
    private UserService userService;


}
