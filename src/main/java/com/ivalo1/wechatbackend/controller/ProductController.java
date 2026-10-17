package com.ivalo1.wechatbackend.controller;

import com.ivalo1.wechatbackend.common.ApiResponse;
import com.ivalo1.wechatbackend.model.Product;
import com.ivalo1.wechatbackend.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ApiResponse<Map<String,Object>> list(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        List<Product> list = productService.listProducts(page, size);
        long total = productService.countProducts();
        Map<String,Object> data = new HashMap<>();
        data.put("items", list);
        data.put("total", total);
        data.put("page", page);
        data.put("size", size);
        return ApiResponse.ok(data);
    }

    @GetMapping("/{id}")
    public ApiResponse<Product> get(@PathVariable Long id) {
        return ApiResponse.ok(productService.getProduct(id));
    }

    @PostMapping
    public ApiResponse<Product> create(@RequestBody Product product) {
        productService.create(product);
        return ApiResponse.ok(product);
    }

    @PutMapping("/{id}")
    public ApiResponse<Product> update(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        productService.update(product);
        return ApiResponse.ok(product);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ApiResponse.ok();
    }
}
