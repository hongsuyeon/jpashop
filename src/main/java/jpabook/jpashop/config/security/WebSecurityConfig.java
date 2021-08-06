package jpabook.jpashop.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    //암호화에 필요한 PasswordEncoder를 Bean 등록한다.
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //authenticationManager를 Bean 등록
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()// 요청에 대한 사용권한 체크
                //.antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/**","/api/v1/join","/api/v1/createJoinForm").permitAll()
               // .antMatchers("/members/**").hasRole("USER")
               // .antMatchers("/**").permitAll() //그외 나머지 요청은 누구나 접근 가능
            //    .accessDecisionManager(authenticationEnptr) https://sas-study.tistory.com/360
                . anyRequest().hasRole("USER")
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
                // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다.
        
        /**
         * UsernamePasswordAuthenticationFilter : usernamem, password를 쓰는 form기반 인증을 처리하는 필터
         * 성공하면, Authentication 객체를 SecurityContext에 저장 후 AuthenticationSuccessHandler실행
         * 실패하면, AuthenticationFailureHandler 실행
        **/
    }

    @Override //ignore check swagger resource
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs","/swagger-resources/**"
                ,"/swagger-ui.html","/webjars/**","/swagger/**");
    }
}
