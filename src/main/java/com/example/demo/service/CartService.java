package com.example.demo.service;

import com.example.demo.Exception.ProductException;
import com.example.demo.Request.AddItemRequest;
import com.example.demo.model.Cart;
import com.example.demo.model.User;

public interface CartService {

    public Cart createCart(User user);

    public  String addCartItem(Long userId, AddItemRequest req ) throws ProductException;


    public  Cart findUserCart(Long userId);

}
