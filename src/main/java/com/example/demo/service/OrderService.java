package com.example.demo.service;

import com.example.demo.Exception.OrderException;
import com.example.demo.model.Address;
import com.example.demo.model.Order;
import com.example.demo.model.User;

import java.util.List;

public interface OrderService {

    public Order createOrder(User user , Address shippingAddress);
    public Order findOrderById(Long orderId) throws OrderException;
    public List<Order> useOrderHistory(Long userId);
    public  Order placedOrder(Long orderId) throws OrderException;
    public  Order confirmedOrder(Long orderId) throws OrderException;
    public  Order shippedOrder(Long orderId) throws OrderException;
    public  Order deliveredOrder(Long orderId) throws OrderException;
    public  Order canceledOrder(Long orderId) throws OrderException;
    public  List<Order> getAllOrders();
    public  void deleteOrder(Long orderId) throws  OrderException;






}
