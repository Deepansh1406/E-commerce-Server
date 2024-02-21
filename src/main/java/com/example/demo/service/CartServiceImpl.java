package com.example.demo.service;


import com.example.demo.Exception.ProductException;
import com.example.demo.Repository.CartItemRepository;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Request.AddItemRequest;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl  implements CartService {


    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private  ProductService productService;


    public CartServiceImpl(CartRepository cartRepository, CartItemService cartItemService,ProductService productService ){
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.productService= productService;



    }


    @Override
    public Cart createCart(User user) {

        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);

    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {

        Cart cart = cartRepository.findByUserId(userId);
        Product product = productService.findProductById(req.getProductId());
        CartItem isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
        if(isPresent == null){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setId(userId);

            int price  = req.getQuantity() * product.getDiscountPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());

            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItemSet().add(createdCartItem);

        }
        return "Items Successfully added to cart!!";



    }

    @Override
    public Cart findUserCart(Long userId) {

        Cart cart = cartRepository.findByUserId(userId);
        int totalPrice= 0;
        int totalDiscountedPrice = 0;
        int totalItem= 0;
        for(CartItem cartItem :cart.getCartItemSet()){
            totalPrice=totalPrice+ cartItem.getPrice();
            totalDiscountedPrice = totalDiscountedPrice+cartItem.getDiscount();
            totalItem = totalItem+ cartItem.getQuantity();
        }
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        cart.setTotalPrice(totalPrice);
        cart.setDiscount(totalPrice - totalDiscountedPrice);


        return cartRepository.save(cart);
    }
}
