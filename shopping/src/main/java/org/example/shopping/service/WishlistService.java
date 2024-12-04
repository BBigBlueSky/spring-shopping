package org.example.shopping.service;

import org.example.shopping.entity.Cart;
import org.example.shopping.entity.Product;
import org.example.shopping.entity.User;
import org.example.shopping.repository.CartRepository;
import org.example.shopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    // 사용자 위시리스트 조회
    public List<Cart> getWishlist(User user) {
        return cartRepository.findByUser(user);
    }

    // 위시리스트에 상품 추가
    public void addToWishlist(User user, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 중복 추가 방지
        if (cartRepository.findByUserAndProduct(user, product).isPresent()) {
            throw new RuntimeException("Product already in wishlist");
        }

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cartRepository.save(cart);
    }

    // 위시리스트에서 상품 제거
    public void removeFromWishlist(User user, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartRepository.findByUserAndProduct(user, product)
                .orElseThrow(() -> new RuntimeException("Product not in wishlist"));

        cartRepository.delete(cart);
    }
}
