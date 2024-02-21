package com.example.demo.model;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity

public class Cart {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;



    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "cart_item")
    private Set<CartItem> cartItemSet = new HashSet<>();


    @Column(name = "total_price")
    private double totalPrice;


    @Column(name = "total_item")
    private int totalItem;


    private int totalDiscountedPrice;
    private  int discount;

    public  Cart(){}

    public Cart(Long id, User user, Set<CartItem> cartItemSet, double totalPrice, int totalItem, int totalDiscountedPrice, int discount) {
        this.id = id;
        this.userId = user;
        this.cartItemSet = cartItemSet;
        this.totalPrice = totalPrice;
        this.totalItem = totalItem;
        this.totalDiscountedPrice = totalDiscountedPrice;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return userId;
    }

    public void setUser(User user) {
        this.userId = user;
    }

    public Set<CartItem> getCartItemSet() {
        return cartItemSet;
    }

    public void setCartItemSet(Set<CartItem> cartItemSet) {
        this.cartItemSet = cartItemSet;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public int getTotalDiscountedPrice() {
        return totalDiscountedPrice;
    }

    public void setTotalDiscountedPrice(int totalDiscountedPrice) {
        this.totalDiscountedPrice = totalDiscountedPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
