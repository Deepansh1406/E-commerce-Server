package com.example.demo.Controller;


import com.example.demo.Config.JwtProvider;
import com.example.demo.Exception.UserException;
import com.example.demo.Request.LoginRequest;
import com.example.demo.Repository.UserRepository;
import com.example.demo.model.Cart;
import com.example.demo.model.User;
import com.example.demo.response.AuthResponse;
import com.example.demo.service.CartService;
import com.example.demo.service.CustomUserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private UserRepository userRepository;
    private JwtProvider jwtProvider;

    private PasswordEncoder passwordEncoder;

    private  CustomUserServiceImpl customUserService;

    private CartService cartService;




    public  AuthController(UserRepository userRepository, CustomUserServiceImpl customUserService,CartService cartService,  PasswordEncoder passwordEncoder, JwtProvider jwtProvider){
        this.userRepository= userRepository;
        this.passwordEncoder= passwordEncoder;
        this.customUserService = customUserService;
        this.jwtProvider = jwtProvider;
        this.cartService= cartService;
    }



    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String password = user.getPassword();
        String firstString = user.getFirstName();
        String lastString = user.getLastName();



        User isEmailExist= userRepository.findByEmail(email);
        if(isEmailExist!=null){
            throw  new UserException("Email is already registered, pls register New Account");

        }

        User createdUser= new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFirstName(firstString);
        createdUser.setLastName(lastString);
        User saved = userRepository.save(createdUser);
        Cart cart = cartService.createCart(saved);

        Authentication authentication = new UsernamePasswordAuthenticationToken(saved.getEmail(), saved.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);
        System.out.println("Generated Token: " + token);

        AuthResponse authResponse = new AuthResponse();

        authResponse.setJwt(token);
        authResponse.setMessage("SignUp successfully!!!");

        return  new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);

    }


    @PostMapping("/signin")
    public  ResponseEntity<AuthResponse>loginUserHandler(@RequestBody LoginRequest loginRequest){

        String username= loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Authentication authentication = authenticate(username, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);


        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("SignIn Successfully!!");

        return  new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);

    }



    private Authentication authenticate(String username, String password) {
        System.out.println("Authenticating user: " + username);
        UserDetails userDetails = customUserService.loadUserByUsername(username);
        if(userDetails == null){
            System.out.println("Invalid Username!!");
            throw  new BadCredentialsException("Invalid Username!!");

        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            System.out.println("Invalid password!!");
            throw  new BadCredentialsException("Invalid password!!");

        }

        return  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }
}


