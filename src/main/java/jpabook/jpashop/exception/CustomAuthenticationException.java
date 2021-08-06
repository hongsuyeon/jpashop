package jpabook.jpashop.exception;

public class CustomAuthenticationException extends RuntimeException {

    public CustomAuthenticationException() {
       super(ErrorCode.AUTHENTICATION_FAILED.getMessage());
    }

    public CustomAuthenticationException(Exception e) {
        super(e);
    }
}
