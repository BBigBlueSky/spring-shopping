package org.example.shopping.controller;

import org.example.shopping.entity.Cart;
import org.example.shopping.entity.User;
import org.example.shopping.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class UserViewController {//로그인 했는지 안했는지 확인후 model변수에 로그인 했는지 넘기는 함수

    UserService userService;

    Model logIAO(Model model) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isAuthenticated = authentication != null && authentication.isAuthenticated() &&
                    !"anonymousUser".equals(authentication.getPrincipal());
            model.addAttribute("isAuthenticated", isAuthenticated);
            return model;
        }

    @GetMapping("/useGuide")
    public String useGuide(Model model) {
        logIAO(model);
            return "/useGuide";
    }
    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/findidpassword")
    public String findPassword() {return "findidpassword.html";}

    @GetMapping("/signup")
    public String signup() {
        return "signup.html";
    }



}
