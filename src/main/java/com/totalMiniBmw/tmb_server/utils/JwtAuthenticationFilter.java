package com.totalMiniBmw.tmb_server.utils;

import com.totalMiniBmw.tmb_server.services.UserService;
import com.totalMiniBmw.tmb_server.services.auth.JwtLogoutService;
import com.totalMiniBmw.tmb_server.services.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;

    private final JwtService jwtService;
    private final JwtLogoutService jwtLogoutService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService,
            HandlerExceptionResolver handlerExceptionResolver, JwtLogoutService jwtLogoutService,
            UserService userService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.jwtLogoutService = jwtLogoutService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            final String userEmail = jwtService.extractUsername(jwt);
            final String sessionType = jwtService.extractClaim(jwt, claims -> claims.get("session", String.class));

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (userEmail == null && authentication != null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{ \"message\": \"Token is not recognized by the TMB server.\" }");
                return;
            }
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            if (!jwtService.isTokenValid(jwt, userDetails)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{ \"message\": \"Token is not valid.\" }");
                return;
            }

            if (jwtLogoutService.isTokenInvalid(jwt)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{ \"message\": \"Token has been invalidated due to previous logout.\" }");
                return;
            }

            List<GrantedAuthority> authorities = new ArrayList<>(userDetails.getAuthorities());
            if (sessionType != null) {
                authorities.add(new SimpleGrantedAuthority("SESSION_" + sessionType.toUpperCase()));
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    authorities
            );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}