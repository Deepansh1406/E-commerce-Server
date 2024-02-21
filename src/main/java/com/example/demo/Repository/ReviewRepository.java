package com.example.demo.Repository;

import com.example.demo.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review , Long> {


    @Query("SELECT r FROM Review r WHERE r.product.id = :productId")
    public List<Review> getAllProducts(@Param("productId") Long productId);


}
