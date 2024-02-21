package com.example.demo.Controller;


import com.example.demo.Exception.ProductException;
import com.example.demo.Exception.UserException;
import com.example.demo.Request.RatingRequest;
import com.example.demo.Request.ReviewRequest;
import com.example.demo.model.Rating;
import com.example.demo.model.Review;
import com.example.demo.model.User;
import com.example.demo.service.ReviewService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Review> createReviews(@RequestBody ReviewRequest req, @RequestHeader("Authorization")String jwt ) throws UserException, ProductException {
        User user  = userService.findUserProfileByJwt(jwt);
        Review review = reviewService.createReview(req, user);

        return  new ResponseEntity<Review>(review, HttpStatus.CREATED);


    }


    @GetMapping("/{product}/{productId}")
    public ResponseEntity<List<Review>> getProductsReviews(
            @PathVariable Long productId
            ,@RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        List<Review> reviews = reviewService.getAllReview(productId);

        return new ResponseEntity<>(reviews, HttpStatus.ACCEPTED);
    }
}
