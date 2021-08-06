package jpabook.jpashop.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/")
    public String Home(HttpSession session, HttpServletRequest httpServletRequest){
        log.info("home controller ::::: "+session.getId());
        return "login";
    }
}
