package org.example.shopping.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping.entity.Cart;
import org.example.shopping.entity.User;
import org.example.shopping.dto.AddUserRequest;
import org.example.shopping.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto) {
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .realName(dto.getRealName())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                .build()).getId();
    }
}
