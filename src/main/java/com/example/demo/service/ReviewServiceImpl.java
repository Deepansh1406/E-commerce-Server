package com.example.demo.service;


import com.example.demo.Exception.ProductException;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.ReviewRepository;
import com.example.demo.Request.ReviewRequest;
import com.example.demo.model.Product;
import com.example.demo.model.Review;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl  implements ReviewService  {

    private ReviewRepository reviewRepository;
    private  ProductService productService;
    private ProductRepository productRepository;


    public  ReviewServiceImpl(ReviewRepository reviewRepository, ProductService productService , ProductRepository productRepository){
        this.reviewRepository= reviewRepository;
        this.productService = productService;
        this.productRepository= productRepository;


    }
    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {

        Product  product = productService.findProductById(req.getProductId());
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReview(req.getReview());
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {


        return reviewRepository.getAllProducts(productId);
    }
}
