package com.example.demo.service;

import com.example.demo.Exception.ProductException;
import com.example.demo.Request.CreateProductRequest;
import com.example.demo.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    public Product createProduct(CreateProductRequest req);

    public  String deleteProduct(Long productId) throws ProductException;

    public  Product updateProduct(Long productId,  Product req ) throws  ProductException;

    public  Product findProductById(Long id) throws  ProductException;

    public List<Product>findProductByCategory(String  category);
    public  List<Product> findAllProducts();

    public Page<Product> getAllProduct(String  category , List<String> colors, List<String>sizes, Integer minPrice,Integer maxPrice, Integer minDiscount, String  sort , String stock, Integer pageNumber , Integer pageSize);






}
