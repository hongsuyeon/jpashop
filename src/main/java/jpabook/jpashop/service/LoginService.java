package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.dto.LoginRequestDTO;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<Member> login(LoginRequestDTO loginRequestDTO) {
        return Optional.ofNullable(memberRepository.findByEmail(loginRequestDTO.getEmail()));
    }

    @Transactional
    @Override
    public long join(LoginRequestDTO loginRequestDTO) {
                return memberRepository.save(
                        Member.builder().email(loginRequestDTO.getEmail()).password(passwordEncoder.encode(loginRequestDTO.getPassword()))
                        .roles(Collections.singletonList("ROLE_USER")).build());
                        // Collections.singletonList : 메모리를 좀 더 아낄 수 있음. 1개짜리 요소를 가진 리스트 생성
                        // 반환한 리스트를 요소를 추가,삭제하면 UnsupportedOperationException 발생(불변보장)

    }

   /* @Override
    public Optional<MemberDTO> login(String email, String password) {
        MemberDTO memberDTO = MemberDTO.builder().userName("eddy")
                            .email(email)
                            .role(Role.USER)
                            .build();
        return Optional.ofNullable(memberDTO);
    }*/

    //login
  /*  public Optional<MemberDTO> login(String email, String password) {

        MemberDTO memberDTO = MemberDTO.builder()
                .userName("hongsu")
                .email(email)
                .role(Role.USER).build();
        return Optional.ofNullable(memberDTO);
    }*/


}
