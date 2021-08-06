package jpabook.jpashop.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> {

    private int status; //상태코드
    private String data; //상태 메세지
    private String message; //전달 메세지
    private T returnData; //성공시 전달 값
}
