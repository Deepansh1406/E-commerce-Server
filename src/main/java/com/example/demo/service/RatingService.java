package com.example.demo.service;


import com.example.demo.Exception.ProductException;
import com.example.demo.Request.RatingRequest;
import com.example.demo.model.Rating;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingService {
    public Rating createRating(RatingRequest req, User user)throws ProductException;

    public List<Rating> getProductsRating(Long productId);





}
