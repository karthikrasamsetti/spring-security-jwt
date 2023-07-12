package com.spring.security.auth;

import com.spring.security.User.Role;
import com.spring.security.User.User;
import com.spring.security.User.UserRepository;
import com.spring.security.config.JWTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        log.info("enter register");
        var user= User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .build();
        userRepository.save(user);
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .user(String.valueOf(user))
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticateRequest request) {
        log.info("enter authenticate");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(),
                request.password())
        );
        var user=userRepository.findByEmail(request.email()).orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .user(String.valueOf(user))
                .build();
    }
}
