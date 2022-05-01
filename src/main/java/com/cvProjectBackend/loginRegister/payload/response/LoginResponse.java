package com.cvProjectBackend.loginRegister.payload.response;

import com.cvProjectBackend.loginRegister.DTO.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private UserDTO user;
    private String jwt;
}
