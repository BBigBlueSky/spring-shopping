package org.example.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.shopping.entity.Cart;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class CartDto {
    private Long id;

    private Long userId;

    private Long productId;

    public static CartDto createCartDto(Cart cart) {
        return new CartDto(cart.getId(), cart.getUser().getId(), cart.getProduct().getId());
    }
}
