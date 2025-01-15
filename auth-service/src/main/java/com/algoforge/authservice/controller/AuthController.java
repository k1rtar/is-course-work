package com.algoforge.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.algoforge.authservice.controller.exception.RegistrationFailException;
import com.algoforge.authservice.controller.request.AuthenticationRequest;
import com.algoforge.authservice.controller.request.AuthenticationResponse;
import com.algoforge.authservice.model.AlgoUser;
import com.algoforge.authservice.model.AlgoUserDetails;
import com.algoforge.authservice.service.AlgoUserDetailsService;
import com.algoforge.common.component.JwtUtil;
import com.algoforge.common.dto.AlgoUserDto;


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
        return convertToDto(user);
    }

    @PutMapping("/users/{username}/block")
    @PreAuthorize("hasAuthority('ADMIN')")
    public AlgoUserDto blockUser(@PathVariable String username,
                                 @AuthenticationPrincipal AlgoUserDetails currentUser) {
        AlgoUser user = userDetailsService.getByUsername(username);
        user.setBlocked(true);
        userDetailsService.save(user);
        return convertToDto(user);
    }

    @PutMapping("/users/{username}/unblock")
    @PreAuthorize("hasAuthority('ADMIN')")
    public AlgoUserDto unblockUser(@PathVariable String username,
                                   @AuthenticationPrincipal AlgoUserDetails currentUser) {
        AlgoUser user = userDetailsService.getByUsername(username);
        user.setBlocked(false);
        userDetailsService.save(user);

        return convertToDto(user);
    }


    @DeleteMapping("/users/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(@PathVariable String username,
                           @AuthenticationPrincipal AlgoUserDetails currentUser) {
        AlgoUser user = userDetailsService.getByUsername(username);
        user.setDeleted(true);
        userDetailsService.save(user);
    }

    private AlgoUserDto convertToDto(AlgoUser user) {
        AlgoUserDto dto = new AlgoUserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setBlocked(user.isBlocked());
        dto.setDeleted(user.isDeleted());
        return dto;
    }
    
}
