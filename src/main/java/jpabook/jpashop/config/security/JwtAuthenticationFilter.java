package jpabook.jpashop.config.security;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    // GenericFilterBean 설정 정보를 얻어올수 있는 확장 추상클래스 (Filter를 확장시킴)

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //헤더에서 jwt를 받아옵니다.
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        //유효한 토큰인지 확인
        if(token != null && jwtTokenProvider.validateToken(token)){
            //토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
            /** Authentication : 인증 정보
             * Object getPrincipal() : 주로 ID
             * Object getCredentials() : 주로 비밀번호
             * Object getDetails() : 사용자 상세 정보
             * Collection<(?) extends GrantedAuthority> getAuthorities() : 사용자 권한 목록
             * boolean isAUthenticated() : 인증여부
             * void setAuthenticated(boolean isAuthenticated) throws illegalArgumentException : 인증 전 false 인증 후 true
             */
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // SecurityContext에 Authentication 객체를 저장합니다.
            // SecurityContextHolder : 접근주체(Authentication)와 인증정보(GrantedAuthority)을 담겨져 사용
            //ThreadLocal에 보관되며, SecurityContextHolder를 통해 접근 가능
            //ThreadLocal : 쓰레드 단위로 로컬 변수를 할당하는 기능 -> 메모리 누수의 주범 / Thread Pool 환경에서 ThreadLocal을 사용시 변수에 보관된 데이터 사용이 끝나면 반드시 해당 데이터를 삭제해주어야 함..
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
