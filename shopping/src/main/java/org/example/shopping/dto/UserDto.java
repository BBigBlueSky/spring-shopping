package org.example.shopping.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.shopping.entity.User;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    public Long id;
    public String email;
    public String password;
    public String realName;
    public String phoneNumber;
    public String address;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .realName(this.realName)
                .phoneNumber(this.phoneNumber)
                .address(this.address)
                .build();
    }


    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
