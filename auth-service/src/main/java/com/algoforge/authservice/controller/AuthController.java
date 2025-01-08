package com.algoforge.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algoforge.authservice.controller.exception.RegistrationFailException;
import com.algoforge.authservice.controller.request.AuthenticationRequest;
import com.algoforge.authservice.controller.request.AuthenticationResponse;
import com.algoforge.authservice.model.AlgoUser;
import com.algoforge.authservice.model.AlgoUserDetails;
import com.algoforge.authservice.service.AlgoUserDetailsService;
import com.algoforge.common.auth.AlgoUserDto;
import com.algoforge.common.component.JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AlgoUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws BadCredentialsException {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        
        final AlgoUser user = userDetailsService.getByUsername(authenticationRequest.getUsername());
        
        final String jwt = jwtTokenUtil.generateToken(user.getUsername(), user.getRoles().stream().map(el -> el.toString()).toList());

        ResponseCookie jwtCookie = ResponseCookie.from("JWT-TOKEN", jwt)
            .path("/")
            .secure(false)
            .maxAge(7 * 24 * 60 * 60)
            .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(new AuthenticationResponse(jwt, user.getUsername(), user.getEmail()));
    }

    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody AuthenticationRequest registrationRequest) throws RegistrationFailException {

        AlgoUser user = userDetailsService.registerUser(registrationRequest);

        final String jwt = jwtTokenUtil.generateToken(user.getUsername(), user.getRoles().stream().map(el -> el.toString()).toList());

        ResponseCookie jwtCookie = ResponseCookie.from("JWT-TOKEN", jwt)
            .path("/")
            .secure(false)
            .maxAge(7 * 24 * 60 * 60)
            .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(new AuthenticationResponse(jwt, user.getUsername(), user.getEmail()));

    }

    @GetMapping("users/{username}")
    public AlgoUserDto getUserByUsername(@PathVariable String username) {
        
        AlgoUser user = userDetailsService.getByUsername(username);
        AlgoUserDto userDto = new AlgoUserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setBlocked(user.isBlocked());
        
        return userDto;

    }
    
}
