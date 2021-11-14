package com.alirizakocas.filestorage.controller;

import com.alirizakocas.filestorage.jwt.TokenManager;
import com.alirizakocas.filestorage.dto.CredentialsDTO;
import com.alirizakocas.filestorage.message.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    // AuthenticationManager'ı çağırdık
    private final AuthenticationManager authenticationManager;

    private final TokenManager tokenManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CredentialsDTO loginRequest){
        try {
            // auth işlemi yapıldı
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));

            String token = tokenManager.generateToken(loginRequest.getUsername());
            JwtResponse jwtResponse = new JwtResponse(token); // token oluşturup döndük

            return ResponseEntity.ok(jwtResponse);

        }catch (Exception e){
            throw e;
        }
    }
}
