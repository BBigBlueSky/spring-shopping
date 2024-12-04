package org.example.shopping.repository;

import org.example.shopping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(value="SELECT * FROM users WHERE realname=:realname AND phonenumber=:phonenumber",nativeQuery=true)
    User findById(String realname,String phonenumber);
    @Query(value="SELECT * FROM users WHERE realname=:realname AND phonenumber=:phonenumber",nativeQuery=true)
    User findByRealNamePhonenumber(String realname,String phonenumber);
    @Query(value="SELECT * FROM users WHERE email=:email",nativeQuery=true)
    User findByEmailU(String email);
}


