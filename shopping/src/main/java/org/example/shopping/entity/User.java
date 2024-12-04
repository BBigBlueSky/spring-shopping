package org.example.shopping.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.shopping.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@ToString
@Table(name = "users")
@NoArgsConstructor
@Getter
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id; //article table에 이걸로 어떤 사용자가 작성했는지 찾기.

    @Getter
    @Column(name = "email", nullable = true, unique = true)
    public String email;

    @Column(name = "password", nullable = true)
    public String password;
    @Column(name = "realname", nullable = false)
    public String realName;
    @Column(name = "phonenumber", nullable = false)
    public String phoneNumber;
    @Column(name = "address", nullable = true)
    public String address;


    @Builder
    public User(String email, String password, String realName, String phoneNumber, String address) {
        this.email = email;
        this.password = password;
        this.realName = realName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public User(Long id, String username, String password, String realName, String phoneNumber, String address) {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void patch(UserDto dto,String newPassword) {
        if(dto.getPassword()!=null)
        {
            this.password = newPassword;
        }
    }
}

