package com.gingerx.focusservice.auth;

import com.gingerx.focusservice.dto.AuthenticationRequest;
import com.gingerx.focusservice.dto.AuthenticationResponse;
import com.gingerx.focusservice.dto.UserRequest;
import com.gingerx.focusservice.facade.RegistrationFacade;
import com.gingerx.focusservice.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;
  private final RegistrationFacade registrationFacade;

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<AuthenticationResponse>> register(@RequestBody UserRequest request) {
      return ResponseEntity.ok().body(new ApiResponse<>(ApiResponse.SUCCESS, "User registered successfully", authenticationService.register(request)));
  }


  /*
      * This method is used to authenticate a user and return a token
   */

  @PostMapping("/authenticate")
  public ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(@RequestBody AuthenticationRequest request) {
      return ResponseEntity.ok().body(new ApiResponse<AuthenticationResponse>(ApiResponse.SUCCESS, "User authenticated successfully", authenticationService.authenticate(request)));
  }

  @PostMapping("/verify-otp")
  public ResponseEntity<ApiResponse<AuthenticationResponse>> verifyOtp(@RequestBody AuthenticationRequest request) {
      return ResponseEntity.ok().body(new ApiResponse<AuthenticationResponse>(ApiResponse.SUCCESS, "OTP verified successfully", registrationFacade.verifyOtp(request)));
  }

  @GetMapping("/resend-otp")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> resendOtp(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok().body(new ApiResponse<AuthenticationResponse>(ApiResponse.SUCCESS, "OTP resent successfully", registrationFacade.resendOtp(request)));
    }


}
