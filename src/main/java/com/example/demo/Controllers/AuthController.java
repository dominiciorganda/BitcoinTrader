package com.example.demo.Controllers;

import com.example.demo.DTOs.AuthenticationResponse;
import com.example.demo.DTOs.LoginRequest;
import com.example.demo.DTOs.RegisterRequest;
import com.example.demo.Entities.User;
import com.example.demo.Services.AuthService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
@RequestMapping("/CoinTrader/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest)  {
        authService.register(registerRequest);
        return new ResponseEntity<>("User Registration Succesful", HttpStatus.OK);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }


}
