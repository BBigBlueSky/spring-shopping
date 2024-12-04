package org.example.shopping.repository;

import org.example.shopping.entity.Cart;
import org.example.shopping.entity.Product;
import org.example.shopping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    // 특정 사용자의 위시리스트 조회
    List<Cart> findByUser(User user);

    // 특정 사용자의 특정 상품 조회 (중복 방지)
    Optional<Cart> findByUserAndProduct(User user, Product product);
}
