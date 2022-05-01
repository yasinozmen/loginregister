package com.cvProjectBackend.loginRegister.controller;

import com.cvProjectBackend.loginRegister.DTO.UserDTO;
import com.cvProjectBackend.loginRegister.config.jwt.JwtUtil;
import com.cvProjectBackend.loginRegister.entity.User;
import com.cvProjectBackend.loginRegister.payload.request.LoginRequest;
import com.cvProjectBackend.loginRegister.payload.request.RegisterRequest;
import com.cvProjectBackend.loginRegister.payload.response.LoginResponse;
import com.cvProjectBackend.loginRegister.payload.response.MessageResponse;
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
import java.util.Optional;

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
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){

        Optional<User> loginUser = userService.findUsername(loginRequest);



        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                                                                      loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User)authentication.getPrincipal();

        String jwt = jwtUtil.generateToken(authentication);

        UserDTO userDTO = userService.getUserDto(user);
        return ResponseEntity.ok(new LoginResponse(userDTO,jwt));


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
