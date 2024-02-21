package com.example.demo.Controller;

import com.example.demo.Exception.ProductException;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProductByCategoryHandler(
            @RequestParam String category,
            @RequestParam List<String> colors,
            @RequestParam List<String> sizes,
            @RequestParam Integer minPrice,
            @RequestParam Integer maxPrice,
            @RequestParam Integer minDiscount,
            @RequestParam String sort,
            @RequestParam String stock,
            @RequestParam Integer pageSize,
            @RequestParam Integer pageNumber) {

        Page<Product> res = productService.getAllProduct(category, colors, sizes, minPrice, maxPrice, minDiscount, sort, stock,pageSize, pageNumber);

        System.out.println("Completed Products" + res);
        System.out.println("Controller--> Category: " + category);
        System.out.println("Controller-->Min Price: " + minPrice);
        System.out.println("Controller-->Max Price: " + maxPrice);
// Add similar log statements for other parameters

        return new  ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/id/{productId}")
    public ResponseEntity<Product> findProductByHandler(@PathVariable Long productId) throws ProductException {
        Product product = productService.findProductById(productId);
        return new ResponseEntity<Product>(product, HttpStatus.ACCEPTED);
    }
}
