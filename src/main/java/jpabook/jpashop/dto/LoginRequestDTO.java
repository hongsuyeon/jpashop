package jpabook.jpashop.dto;

import jpabook.jpashop.config.Role;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginRequestDTO {
    private String email;
    private String password;
    private Role role;
}
