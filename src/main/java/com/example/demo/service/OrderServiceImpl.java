package com.example.demo.service;


import com.example.demo.Exception.OrderException;
import com.example.demo.Repository.*;
import com.example.demo.model.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements  OrderService {


    private CartRepository cartRepository;
    private CartService cartService;
    private  ProductService productService;
    private OrderRepository orderRepository;
    private AddressRepository addressRepository;
    private UserRepository userRepository;
    private  OrderItemService orderItemService;
    private OrderItemRepository orderItemRepository;

    @Autowired
    public  OrderServiceImpl(CartRepository cartRepository, CartService cartItemService, ProductService productService,
                             OrderRepository orderRepository,  AddressRepository addressRepository , UserRepository userRepository
                             ,  OrderItemService orderItemService,
                             OrderItemRepository orderItemRepository
                             ){
        this.cartRepository= cartRepository;
        this.cartService = cartItemService;
        this.productService= productService;
        this.orderRepository =orderRepository;
        this.addressRepository= addressRepository;
        this.userRepository= userRepository;
        this.orderItemService = orderItemService;
         this.orderItemRepository = orderItemRepository;



    }


    @Override
    public Order createOrder(User user, Address shippingAddress) {
        shippingAddress.setUser(user);
        Address address = addressRepository.save(shippingAddress);
        user.getAddress().add(address);
        userRepository.save(user);
        Cart cart= cartService.findUserCart(user.getId());
        List<OrderItem> orderItems=  new ArrayList<>();

        for (CartItem item: cart.getCartItemSet()){
            OrderItem orderItem= new OrderItem();
            orderItem.setPrice(item.getPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setSize(item.getSize());
            orderItem.setUserId(item.getUserId());
            orderItem.setDiscountedPrice(item.getDiscount());
            OrderItem  createdOrderItems= orderItemRepository.save(orderItem);
            orderItems.add(createdOrderItems);

        }

        Order createdOrder= new Order();
        createdOrder.setUser(user);
        createdOrder.setOrderItems(orderItems);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        createdOrder.setDiscount(cart.getDiscount());
        createdOrder.setTotalItems(cart.getTotalItem());
        createdOrder.setShippingAddress(address);
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus("Pending");
        createdOrder.getPaymentDetails().setStatus("Pending");
        createdOrder.setCreatedAt(LocalDateTime.now());


        Order savedOrder= orderRepository.save(createdOrder);
        for (OrderItem item:orderItems){
            item.setOrder(savedOrder);
            orderItemRepository.save(item);

        }
        return savedOrder;
    }


    @Override
    public Order findOrderById(Long orderId) throws OrderException {

        Optional<Order> opt= orderRepository.findById(orderId);
        if(opt.isPresent()){
            return opt.get();

        }
        throw new OrderException("Order do not Exist with id" + orderId);

    }

    @Override
    public List<Order> useOrderHistory(Long userId) {

        List<Order> orders = orderRepository.getUsersOrders(userId);
        return orders;
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {

        Order order = findOrderById(orderId);
        order.setOrderStatus("Placed");
        order.getPaymentDetails().setStatus("Completed");
        return order;
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("Confirmed!!");
        return orderRepository.save(order);
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("Shipped!!");
        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("Delivered!!");
        return orderRepository.save(order);
    }

    @Override
    public Order canceledOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("Cancelled!!");
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        orderRepository.deleteById(orderId);

    }
}
