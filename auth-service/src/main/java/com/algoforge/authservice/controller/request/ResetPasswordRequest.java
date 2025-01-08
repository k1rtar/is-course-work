package com.algoforge.authservice.controller.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    
    String token;

    String newPassword;
    
}
