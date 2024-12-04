package org.example.shopping.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.example.shopping.entity.Product;

@AllArgsConstructor
@ToString
public class ProductForm {
    private Long id;
    private String name;
    private Long price;
    private String description;
    private String image;//이미지 url 들어갈 자리
    private String category;
    private Long quantity;

    public Product toEntity() {
        return new Product(id,name,price,description,image,category,quantity);
    }
}
