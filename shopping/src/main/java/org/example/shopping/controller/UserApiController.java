package org.example.shopping.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.shopping.dto.AddUserRequest;
import org.example.shopping.dto.UserDto;
import org.example.shopping.entity.User;
import org.example.shopping.repository.UserRepository;
import org.example.shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    Model logIAO(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getPrincipal());
        model.addAttribute("isAuthenticated", isAuthenticated);
        return model;
    }
    @PostMapping("/user")
    public String signup(AddUserRequest request) {
        userService.save(request);
        return "redirect:/login";
    }
    @PostMapping("/myidpassword")
    public String showyourid(Model model, @ModelAttribute UserDto dto) {
        model= logIAO(model);
        System.out.println(dto.toString());
        User target = dto.toEntity();
        System.out.println(target.toString());
        User user= userRepository.findByRealNamePhonenumber(target.getRealName(),target.getPhoneNumber());
        model.addAttribute("user", user);
        return "myidpassword.html";
    }




    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

}
