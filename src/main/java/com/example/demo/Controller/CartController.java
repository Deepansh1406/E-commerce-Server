package com.example.demo.Controller;


import com.example.demo.Exception.ProductException;
import com.example.demo.Exception.UserException;
import com.example.demo.Request.AddItemRequest;
import com.example.demo.model.Cart;
import com.example.demo.model.User;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.CartService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")

public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;


    @GetMapping("/")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }


    @PutMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(
            @RequestBody AddItemRequest req,
            @RequestHeader("Authorization") String jwt
    ) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        cartService.addCartItem(user.getId(), req);


        ApiResponse res = new ApiResponse();
        res.setMessage("Item Added to cart!!");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);

    }
}
