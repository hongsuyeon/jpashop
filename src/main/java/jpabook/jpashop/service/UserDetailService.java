package jpabook.jpashop.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailService {

    public UserDetails loadUserByUsername(String name);

}
