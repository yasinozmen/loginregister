package com.cvProjectBackend.loginRegister.controller;

import com.cvProjectBackend.loginRegister.config.jwt.JwtUtil;
import com.cvProjectBackend.loginRegister.entity.User;
import com.cvProjectBackend.loginRegister.payload.request.LoginRequest;
import com.cvProjectBackend.loginRegister.payload.request.RegisterRequest;
import com.cvProjectBackend.loginRegister.payload.response.JwtResponse;
import com.cvProjectBackend.loginRegister.payload.response.MessageResponse;
import com.cvProjectBackend.loginRegister.service.UserDetailsImpl;
import com.cvProjectBackend.loginRegister.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder encoder;




    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){





        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                                                                      loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);



        String jwt = jwtUtil.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();



        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail()));



    }




    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest){

        if (userService.existEmail(registerRequest)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setPassword(encoder.encode(registerRequest.getPassword()));

        userService.saveUser(user);

        return ResponseEntity.ok(new MessageResponse("User Registered Successfully!"));

    }





}
