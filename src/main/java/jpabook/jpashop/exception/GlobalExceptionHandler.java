package jpabook.jpashop.exception;

import jpabook.jpashop.config.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomAuthenticationException.class)
    protected ResponseEntity<CommonResponse> handleCustomerAuthenticationException(CustomAuthenticationException e){
        log.info("handleCustomerAuthenticationException",e);

        CommonResponse response = CommonResponse.builder().status(ErrorCode.AUTHENTICATION_FAILED.getStatus())
                                                          .message(e.getMessage())
                                                          .data(ErrorCode.AUTHENTICATION_FAILED.getData()).build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}
