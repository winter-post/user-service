package com.devwinter.userservice.presentation.exceptionhandler;

import com.devwinter.userservice.domain.exception.UserException;
import com.devwinter.userservice.presentation.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public static String ARGUMENT_NOT_VALID_STRING = "유효성 검사에 실패하였습니다.";

    @ExceptionHandler(UserException.class)
    public BaseResponse userExceptionHandler(UserException e) {
        log.error("UserExceptionHandler: ", e);
        return BaseResponse.builder()
                           .code(e.getUserErrorCode()
                                  .getHttpStatus()
                                  .toString())
                           .message(e.getUserErrorCode()
                                     .getMessage())
                           .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<MultiValueMap<String, String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidExceptionHandler: ", e);
        MultiValueMap<String, String> errors = new LinkedMultiValueMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            errors.add(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return BaseResponse.<MultiValueMap<String, String>>builder()
                           .code(HttpStatus.BAD_REQUEST.toString())
                           .message(ARGUMENT_NOT_VALID_STRING)
                           .body(errors)
                           .build();
    }
}
