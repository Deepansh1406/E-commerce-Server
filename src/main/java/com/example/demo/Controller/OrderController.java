package com.example.demo.Controller;


import com.example.demo.Exception.OrderException;
import com.example.demo.Exception.UserException;
import com.example.demo.model.*;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService  orderService;


    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress, @RequestHeader("Authorization")String jwt ) throws UserException {
        User user  = userService.findUserProfileByJwt(jwt);
        Order order = orderService.createOrder(user, shippingAddress);

        System.out.println("Order"   + order);

        return  new ResponseEntity<Order>(order, HttpStatus.CREATED);


    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> userOrderHistory(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        List<Order> orders = orderService.useOrderHistory(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }


    @GetMapping("/{Id}")
    public ResponseEntity<Order> findOrderById(
            @PathVariable(name = "Id") Long orderId,
            @RequestHeader("Authorization") String jwt) throws UserException, OrderException {

            User user = userService.findUserProfileByJwt(jwt);
            Order order = orderService.findOrderById(orderId);

            return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

}
