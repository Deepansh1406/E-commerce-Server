package com.example.demo.service;

import com.example.demo.Exception.ProductException;
import com.example.demo.Repository.RatingRepository;
import com.example.demo.Request.RatingRequest;
import com.example.demo.model.Product;
import com.example.demo.model.Rating;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;
    private ProductService productService;


    public  RatingServiceImpl(RatingRepository ratingRepository,ProductService productService ){
        this.ratingRepository = ratingRepository;
         this.productService= productService;

    }

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {

        Product product = productService.findProductById(req.getProductId());
        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());

        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {


        return ratingRepository.getAllProductsRatings(productId);
    }
}
