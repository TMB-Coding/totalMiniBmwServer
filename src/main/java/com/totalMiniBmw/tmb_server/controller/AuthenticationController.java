package com.totalMiniBmw.tmb_server.controller;

import com.totalMiniBmw.tmb_server.dto.KioskLoginDto;
import com.totalMiniBmw.tmb_server.dto.UserLoginDto;
import com.totalMiniBmw.tmb_server.dto.responses.LoginResponse;
import com.totalMiniBmw.tmb_server.entities.UserEntity;
import com.totalMiniBmw.tmb_server.services.auth.AuthenticationService;
import com.totalMiniBmw.tmb_server.services.auth.JwtLogoutService;
import com.totalMiniBmw.tmb_server.services.auth.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final JwtLogoutService jwtLogoutService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, JwtLogoutService jwtLogoutService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.jwtLogoutService = jwtLogoutService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody UserLoginDto loginUserDto, HttpServletResponse response) {
        UserEntity authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        Cookie cookie = new Cookie("jwt", jwtToken);
        cookie.setHttpOnly(true); // Prevent JavaScript access (XSS protection)
        cookie.setSecure(false);  // Use only over HTTPS
        cookie.setPath("/");     // Make the cookie available site-wide

        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/kauth")
    public ResponseEntity<LoginResponse> authenticateKiosk(@RequestBody KioskLoginDto kioskLoginDto, HttpServletResponse response) {
        UserEntity authenticatedUser = authenticationService.authenticateKiosk(kioskLoginDto);

        String jwtToken = jwtService.generateKioskToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        Cookie cookie = new Cookie("jwt", jwtToken);
        cookie.setHttpOnly(true); // Prevent JavaScript access (XSS protection)
        cookie.setSecure(false);  // Use only over HTTPS
        cookie.setPath("/");     // Make the cookie available site-wide

        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String jwt) {
        jwtLogoutService.invalidate(jwt);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validate(@RequestHeader("Authorization") String jwtToken) {
        boolean isValidJwt = jwtService.isTokenValid(jwtToken);
        boolean isTokenInvalid = jwtLogoutService.isTokenInvalid(jwtToken);
        if (isValidJwt && !isTokenInvalid) return ResponseEntity.ok().build();
        return ResponseEntity.status(401).build();
    }
}
