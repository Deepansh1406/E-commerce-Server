package com.example.demo.service;

import com.example.demo.Exception.CartItemException;
import com.example.demo.Exception.UserException;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Product;

public interface CartItemService  {

    public CartItem createCartItem(CartItem cartItem);
    public  CartItem updateCartItem(Long userid, Long id, CartItem cartItem ) throws CartItemException, UserException;
    public  CartItem isCartItemExist(Cart cart , Product product, String  size, Long userId);
    public  CartItem removeCartItem(Long userId, Long cartItemId) throws  CartItemException , UserException;
    public CartItem  findCartItemById(Long cartItemId) throws  CartItemException;




}
