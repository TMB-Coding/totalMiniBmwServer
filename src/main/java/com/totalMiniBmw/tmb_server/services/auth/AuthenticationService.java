package com.totalMiniBmw.tmb_server.services.auth;

import com.totalMiniBmw.tmb_server.dto.KioskLoginDto;
import com.totalMiniBmw.tmb_server.dto.UserLoginDto;
import com.totalMiniBmw.tmb_server.entities.UserEntity;
import com.totalMiniBmw.tmb_server.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;


    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    public UserEntity authenticate(UserLoginDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }

    public UserEntity authenticateKiosk(KioskLoginDto input) {
        Optional<UserEntity> user = userRepository.findByEmployeeNumber(input.getEmployeeNumber());
        return user.orElse(null);
    }
}
