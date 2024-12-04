package org.example.shopping.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.shopping.dto.ProductForm;
import org.example.shopping.entity.Cart;
import org.example.shopping.entity.Product;
import org.example.shopping.entity.User;
import org.example.shopping.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class CartController {
    @Autowired
    private WishlistService wishlistService;

    @GetMapping("/wishlist")
    public String viewWishlist(Model model, @AuthenticationPrincipal User user) {
        // 사용자별 위시리스트 조회
        List<Cart> wishlist = wishlistService.getWishlist(user);
        model.addAttribute("wishlist", wishlist);
        return "/userpage/view"; // 위시리스트 뷰
    }

    @PostMapping("/add")
    public String addToWishlist(Model model,ProductForm dto, @AuthenticationPrincipal User user) {
        System.out.println("123123123"+dto.toString());
        Product productId = dto.toEntity();
        try {
            log.info("Adding product to wishlist: {}", productId);
            wishlistService.addToWishlist(user, productId.getId());
        } catch (RuntimeException e) {
            // 중복 추가 에러 처리
            return "redirect:/wishlist?error=" + e.getMessage();
        }
        model.addAttribute("product", dto.toEntity());
        return "redirect:/products/detail/"+productId.getId();
    }

    @PostMapping("/remove")
    public String removeFromWishlist(ProductForm dto, @AuthenticationPrincipal User user) {
        System.out.println("123123123"+dto.toString());
        Product productId = dto.toEntity();
        System.out.println("123123123"+productId.toString());
        wishlistService.removeFromWishlist(user, productId.getId());
        return "redirect:/wishlist";
    }
}
