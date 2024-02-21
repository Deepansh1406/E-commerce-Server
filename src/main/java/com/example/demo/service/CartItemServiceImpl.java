package com.example.demo.service;

import com.example.demo.Exception.CartItemException;
import com.example.demo.Exception.UserException;
import com.example.demo.Repository.CartItemRepository;
import com.example.demo.Repository.CartRepository;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;


@Service
public class CartItemServiceImpl implements  CartItemService{

    private CartItemRepository cartItemRepository;
    private  UserService userService;
    private CartRepository cartRepository;


    @Autowired
    public  CartItemServiceImpl(CartItemRepository cartItemRepository,UserService userService, CartRepository cartRepository ){
        this.cartItemRepository = cartItemRepository;
        this.userService = userService ;
        this.cartRepository= cartRepository;

    }


    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        cartItem.setDiscount(cartItem.getProduct().getDiscountPrice() * cartItem.getQuantity());

        CartItem createdCartItem= cartItemRepository.save(cartItem);

        return createdCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userid, Long id, CartItem cartItem) throws CartItemException, UserException {

        CartItem item= findCartItemById(id);
        User user = userService.findUserById(item.getId());

        if(user.getId().equals(userid)){
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity() * item.getProduct().getPrice());
            item.setDiscount(item.getProduct().getDiscountPrice() * item.getQuantity());
        }

        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {

        CartItem cartItem = cartItemRepository.isCartItemExist(cart, product , size, userId);
        return  cartItem;
    }

    @Override
    public CartItem removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {

        CartItem cartItem = findCartItemById(cartItemId);
        User user = userService.findUserById(cartItem.getId());

        User reqUser= userService.findUserById(userId);
        if(user.getId().equals(reqUser.getId())){
            cartItemRepository.deleteById(cartItemId);
        }
            throw new UserException("You cant remove another users items ");


    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {

        Optional<CartItem> opt= cartItemRepository.findById(cartItemId);
        if(opt.isPresent()){
            return opt.get();

        }
        throw new CartItemException("CartItems not found with id:" + cartItemId);
    }
}
