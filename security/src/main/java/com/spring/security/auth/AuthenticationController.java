package com.spring.security.auth;

import com.spring.security.User.User;
import com.spring.security.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository repository;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        log.info("register method");
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticateRequest request){
        log.info("authenticate method");
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> userById(@PathVariable int id)  {
        log.info("getById method in controller");
        return ResponseEntity.ok(authenticationService.getById(id));
    }


}
