package jpabook.jpashop.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="member_id")
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 300, nullable = false)
    private String password;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList();

    @ElementCollection(fetch= FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //계정의 권한 목록을 리턴
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        //리턴 타입 : Collection<? extends GrantedAuthoriry>
    }

    @Override
    public String getUsername() {
        return email;
    } //계정의 고유한 값을 리턴 (DB PK값, 중복이 없는 이메일 값)

    @Override
    public boolean isAccountNonExpired() {
        return true;
    } //계정의 만료 여부 리턴 (true : 만료 안됨)

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } //계정의 잠김 여부 리턴 (true : 잠기지 않음)

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }  // 비밀번호 만료 여부 리턴 (true: 만료 안됨)

    @Override
    public boolean isEnabled() {
        return true;
    } // 계정의 활성화 여부 리턴 (true: 활성화 됨)
}
