package com.course_platform.courses.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CustomRuntimeException extends RuntimeException{
    private ErrorCode errorCode;
}
