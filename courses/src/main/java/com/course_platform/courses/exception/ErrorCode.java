package com.course_platform.courses.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"Uncategorized error",HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_FOUND(1004,"User not found",HttpStatus.NOT_FOUND),
    SECTION_NOT_FOUND(1004,"Section not found",HttpStatus.NOT_FOUND),
    COURSE_NOT_FOUND(1004,"Course not found",HttpStatus.NOT_FOUND),
    LESSON_NOT_FOUND(1004,"Lesson not found",HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND(1004,"Lesson not found",HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND(1004,"Role not found",HttpStatus.NOT_FOUND),
    TOKEN_NOT_FOUND(1004,"Token not found",HttpStatus.NOT_FOUND),
    NOTE_NOT_FOUND(1004,"Note not found",HttpStatus.NOT_FOUND),
    PERSMISSION_NOT_FOUND(1004,"Permission not found",HttpStatus.NOT_FOUND),
    COMMENT_NOT_FOUND(1004,"Comment not found",HttpStatus.NOT_FOUND),
    TITLE_INVALID(1003, "Title must be not empty", HttpStatus.BAD_REQUEST),
    DESCRIPTION_INVALID(1003, "Description must be not empty", HttpStatus.BAD_REQUEST),
    IMAGE_INVALID(1003, "Image must be not empty", HttpStatus.BAD_REQUEST),
    PRICE_INVALID(1003, "Price must be greater than 0 or equals 0", HttpStatus.BAD_REQUEST),
    ORDER_FAILED(1004,"You bought course",HttpStatus.BAD_REQUEST),
    TOKEN_INVALID(1004,"Token must be not empty",HttpStatus.BAD_REQUEST),
    COURSE_INVALID(1004,"Course must be not empty",HttpStatus.BAD_REQUEST),
    SECTION_INVALID(1004,"Section must be not empty",HttpStatus.BAD_REQUEST),
    TOKEN_EXPRIRED(1004,"Refresh token is expired. Please login to get new token ",HttpStatus.BAD_REQUEST),
    EMAIL_EXIST(1003, "Email is exist", HttpStatus.BAD_REQUEST),
    FILE_INVALID(1003, "File must be not null", HttpStatus.BAD_REQUEST),
    FORBIDDEN(1003, "You don't have permission to access resources", HttpStatus.FORBIDDEN),
    UNAUTHORIZED(1003, "Unauthorizated user", HttpStatus.FORBIDDEN),
    ORDER_NOT_EXIST(1003, "You must be order this course before review this", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1003, "Password must be greater than {min} characters", HttpStatus.BAD_REQUEST),
    UNUPLOADFILE_EXCEPTION( 1004,"Upload file failed",HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND( 1004,"Resource not found",HttpStatus.NOT_FOUND),
    UNSUPPORTED_METHOD( 1005,"Method is not supported",HttpStatus.METHOD_NOT_ALLOWED);

    private int code;
    private String message;
    private HttpStatus status;
}