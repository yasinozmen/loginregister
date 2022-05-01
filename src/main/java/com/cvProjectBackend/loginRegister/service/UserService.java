package com.cvProjectBackend.loginRegister.service;

import com.cvProjectBackend.loginRegister.DTO.UserDTO;
import com.cvProjectBackend.loginRegister.entity.User;
import com.cvProjectBackend.loginRegister.payload.request.LoginRequest;
import com.cvProjectBackend.loginRegister.payload.request.RegisterRequest;
import com.cvProjectBackend.loginRegister.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional<User> findUsername(LoginRequest loginRequest){
        return userRepository.findByEmail(loginRequest.getEmail());
    }

    public Boolean existEmail(RegisterRequest registerRequest){
        return userRepository.existsByEmail(registerRequest.getEmail());
    }

    public void saveUser(User user){
        userRepository.save(user);

    }

    public UserDTO getUserDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserid(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
