package jpabook.jpashop.config.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jpabook.jpashop.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private long totkenValidTime = 30 * 60 * 1000L;

    private final UserDetailService userDetailService;
    
    // @PreDestroy : 빈 객체가 소멸될 때 자동으로 호출되는 메소드를 등록
    @PostConstruct // 빈생성자 호출 후에 자동으로 호출호출될 메서드 
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String userPK, List<String> roles){
        Claims claims = Jwts.claims().setSubject(userPK); // Jwt payload 에 저장되는 정보단위
        claims.put("roles", roles); //정보는 key/value 형태로 저장
        Date now = new Date();

        return Jwts.builder().setClaims(claims) //정보 저장
                .setIssuedAt(now) //토근 발행 시간 정보
                .setExpiration(new Date(now.getTime() + totkenValidTime)) //st Expire TIme
                .signWith(SignatureAlgorithm.HS256, secretKey) //사용할 암호화 알고리즘과 sinature에 들어갈 scret값 세팅
                .compact();
    }

    //JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token){
        //유저의 정보를 불러와서 UserDetails 리턴
        UserDetails userDetails = userDetailService.loadUserByUsername(this.getUserPK(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //토큰에서 회원 정보 추출
    public String getUserPK(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    //Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN : TOKEN 값"
    public String resolveToken(HttpServletRequest request){
        return request.getHeader("X-AUTH-TOKEN");
    }
    
    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken){
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtToken);;
        return !claims.getBody().getExpiration().before(new Date());

    }


}
