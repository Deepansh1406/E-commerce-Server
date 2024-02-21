package com.example.demo.service;

import com.example.demo.Repository.OrderItemRepository;
import com.example.demo.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderItemServiceImpl implements  OrderItemService{


    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {




        return orderItemRepository.save(orderItem);
    }
}
