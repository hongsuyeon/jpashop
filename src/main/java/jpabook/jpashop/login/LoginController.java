package jpabook.jpashop.login;

import jpabook.jpashop.config.CommonResponse;
import jpabook.jpashop.config.security.JwtTokenProvider;
import jpabook.jpashop.controller.MemberForm;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.dto.LoginRequestDTO;
import jpabook.jpashop.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LoginController {

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final LoginService loginService;


    @GetMapping("/createJoinForm")
    public ModelAndView createForm(Model model){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("joinForm");
        mav.addObject("memberForm", new MemberForm());
        return mav;
    }

    @PostMapping("/join") //회원가입
    public Long join(@RequestBody LoginRequestDTO loginRequestDTO){
        Long memberId = loginService.join(loginRequestDTO);
        return memberId;
    }


    @PostMapping("/login")
    public CommonResponse login(@RequestBody LoginRequestDTO loginRequestDTO) {

        Member member = loginService.login(loginRequestDTO).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if(!passwordEncoder.matches(loginRequestDTO.getPassword(), member.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }

        return CommonResponse.builder()
                .data("SUCCEESS")
                .returnData(jwtTokenProvider.createToken(member.getUsername(), member.getRoles()))
                .build();
    }

}
