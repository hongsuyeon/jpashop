package jpabook.jpashop.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    AUTHENTICATION_FAILED(401, "AUTH_001","AUTHENTICATION_FAILED.."),
    LOGIN_FAILED(401, "AUTH_002","LOGIN_FAILED..");


    private int status; //상태코드
    private String data;// 상태 메세지
    private String message; // 전달 메세지

    ErrorCode(int status, String data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
