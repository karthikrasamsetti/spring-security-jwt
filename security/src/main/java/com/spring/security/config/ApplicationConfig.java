package com.spring.security.config;

import com.spring.security.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationConfig {

    private final UserRepository userRepository;
//    Special book that holds information of users
    @Bean
    public UserDetailsService userDetailsService(){
        log.info("enter userDetailsService");
//        getting data from the user repository
        return username -> userRepository.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("User Not Found"));
    }
//    Detective-AuthenticationProvider is responsible for
//    actually authenticating a user by checking their credentials
    @Bean
    public AuthenticationProvider authenticationProvider(){
        log.info("enter authenticationProvider");
        DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder() );
        return authProvider;
    }
//    Supervisor for detectives-
//    AuthenticationManager is responsible for managing the
//    authentication process by delegating the authentication to one or more AuthenticationProviders.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        log.info("enter authenticationManager");
        return config.getAuthenticationManager();
    }
//converting a password into a secret code to protect it from unauthorized access
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("enter passwordEncoder");
        return new BCryptPasswordEncoder();
    }
}
