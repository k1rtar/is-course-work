package com.algoforge.authservice.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.algoforge.authservice.controller.exception.EmailNotFoundException;
import com.algoforge.authservice.controller.exception.RegistrationFailException;
import com.algoforge.authservice.controller.request.AuthenticationRequest;
import com.algoforge.authservice.model.AlgoUser;
import com.algoforge.authservice.model.AlgoUserDetails;
import com.algoforge.authservice.model.Role;
import com.algoforge.authservice.repository.AlgoUserRepository;

@Service
public class AlgoUserDetailsService implements UserDetailsService {
 
    @Autowired
    private AlgoUserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AlgoUser user = getByUsername(username);

        return new AlgoUserDetails(user, user.getGrantedAuthorities());
    }

    @Transactional
    public AlgoUser getByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + username));
    }

    public AlgoUser getByEmail(String email) throws EmailNotFoundException {

        return userRepository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException("No user found with email: " + email));

    }

    public AlgoUser registerUser(AuthenticationRequest registrationRequest) throws RegistrationFailException {

        if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
            throw new RegistrationFailException("User with this username already exists");
        }

        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            throw new RegistrationFailException("User with this email already exists");
        }

        AlgoUser newUser = new AlgoUser();
        newUser.setUsername(registrationRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        newUser.setEmail(registrationRequest.getEmail());
        newUser.setBio(registrationRequest.getBio());
        newUser.setProfilePhoto(registrationRequest.getProfilePhoto());
        newUser.addRole(Role.USER);
        boolean adminExists = userRepository.existsByRolesContaining(Role.ADMIN);

        if (!adminExists) {
            newUser.addRole(Role.ADMIN);
        }

        userRepository.save(newUser);

        return newUser;
    }

    public AlgoUser save(AlgoUser user) {
        return userRepository.save(user);
    }

}
