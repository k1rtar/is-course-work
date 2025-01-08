package com.algoforge.authservice.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationRequest {
  
    private final String username;
    private final String password;
    private final String email;
    private final String bio;
    private final byte[] profilePhoto;
    
}