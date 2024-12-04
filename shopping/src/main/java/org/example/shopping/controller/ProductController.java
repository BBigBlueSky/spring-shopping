package org.example.shopping.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.shopping.dto.ProductForm;
import org.example.shopping.entity.Product;
import org.example.shopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    Model logIAO(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getPrincipal());
        model.addAttribute("isAuthenticated", isAuthenticated);
        return model;
    }
    //상품 등록
    @GetMapping("/products/register")
    public String newProductForm(Model model) {
        logIAO(model);
        return "/products/register";

    }
    @PostMapping("/products/create")
    public String createProduct(ProductForm form,Model model)
    {
        logIAO(model);
        log.info(form.toString());
        Product product = form.toEntity();
        Product saved = productRepository.save(product);
        log.info(saved.toString());
        return "redirect:/products/" + saved.getId();
    }
    @GetMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable Long id ,Model model)
    {
        logIAO(model);
        productRepository.deleteById(id);
        return "redirect:/manageList";
    }
    @GetMapping("/manageList")
    public String productManageList(Model model)
    {
        logIAO(model);
        List<Product> products = productRepository.findAll();
        log.info(products.toString());
        model.addAttribute("productsList", products);
        return "/products/productManageList";
    }
    //특정 상품 출력
    @GetMapping("/products/{id}")
    public String show(@PathVariable Long id, Model model) {
        logIAO(model);
        //1. ProductRepository 에서 데이터 찾기
        Product productEntity = productRepository.findById(id).orElse(null);
        //2. 데이터 모델변수에 등록
        model.addAttribute("product", productEntity);
        //3. 뷰 반환.
        return "/products/show";
    }
    @GetMapping("/products/index/{category}")
    public String index(@PathVariable String category, Model model) {
        logIAO(model);
        List<Product> product = productRepository.findAllByCategory(category);
        model.addAttribute("productList", product);
        model.addAttribute("category", category);
        return "/products/index";
    }

    @GetMapping("/mainpage")
    public String mainPageLoad( Model model) {
        logIAO(model);
        List<Product> product = productRepository.findAll();
        System.out.println(product.toString());
        model.addAttribute("productList", product);
        return "/mainPage";
    }

    @GetMapping("/products/detail/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        logIAO(model);
        //1. ProductRepository 에서 데이터 찾기
        Product productEntity = productRepository.findById(id).orElse(null);
        //2. 데이터 모델변수에 등록
        model.addAttribute("product", productEntity);
        //3. 뷰 반환.
        return "/products/detail";
    }

}
