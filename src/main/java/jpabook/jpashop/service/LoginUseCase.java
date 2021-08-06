package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.dto.LoginRequestDTO;

import java.util.Optional;

public interface LoginUseCase {
    Optional<Member> login(LoginRequestDTO loginRequestDTO);

    long join(LoginRequestDTO loginRequestDTO);
}
