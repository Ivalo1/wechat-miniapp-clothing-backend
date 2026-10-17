package com.ivalo1.wechatbackend.exception;

import com.ivalo1.wechatbackend.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<String> handleAll(Exception ex) {
        // Log exception in real application
        return ApiResponse.error(ex.getMessage() == null ? "internal error" : ex.getMessage());
    }
}
