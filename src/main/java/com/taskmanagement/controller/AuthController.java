package com.taskmanagement.controller;

import com.taskmanagement.dto.AuthenticationRequest;
import com.taskmanagement.dto.AuthenticationResponse;
import com.taskmanagement.security.CustomUserDetailsService;
import com.taskmanagement.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    @GetMapping
    public String test(){
        return "okkkkkkk";
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest){
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails= customUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String token = jwtUtil.generateAuthToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
