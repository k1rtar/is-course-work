package com.algoforge.authservice.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {

  private final String jwt;
  private final String username;
  private final String email;

}
