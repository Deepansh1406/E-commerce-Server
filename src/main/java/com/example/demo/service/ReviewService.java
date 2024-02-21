package com.example.demo.service;

import com.example.demo.Exception.ProductException;
import com.example.demo.Request.ReviewRequest;
import com.example.demo.model.Review;
import com.example.demo.model.User;

import java.util.List;

public interface ReviewService {

    public Review createReview(ReviewRequest req, User user) throws ProductException;

    public List<Review> getAllReview(Long productId);


}
