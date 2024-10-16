package com.gingerx.focusservice.auth;


import com.gingerx.focusservice.config.JwtService;
import com.gingerx.focusservice.dto.AuthenticationRequest;
import com.gingerx.focusservice.dto.AuthenticationResponse;
import com.gingerx.focusservice.dto.UserRequest;
import com.gingerx.focusservice.entity.User;
import com.gingerx.focusservice.exception.JwtAuthenticationException;
import com.gingerx.focusservice.repository.UserRepository;
import com.gingerx.focusservice.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
  private final UserRepository repository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UsersService usersService;

  public AuthenticationResponse register(UserRequest userRequest) {
      try{
        log.info("AuthenticationService::Registering user started");
        User user = usersService.Register(userRequest);
        log.info("AuthenticationService::Registering user completed");
        var jwtToken = jwtService.generateToken(new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>()));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(user.getId())
                .build();
      }catch (Exception e){
        log.error("AuthenticationService::Registering user failed");
        throw e;
      }
  }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("AuthenticationService::Authenticating user started");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            log.error("AuthenticationService::Authenticating user failed due to invalid credentials");
            throw new JwtAuthenticationException("Invalid credentials");
        }

        var user = repository.findByEmail(request.getEmail()).orElseThrow(()->new JwtAuthenticationException("User not found with email "+request.getEmail()));
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
        var jwtToken = jwtService.generateToken(userDetails);
        log.info("AuthenticationService::Authenticating user completed");
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(user.getId())
                .build();
    }
}
