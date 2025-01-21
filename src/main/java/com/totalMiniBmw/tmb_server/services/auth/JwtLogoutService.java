package com.totalMiniBmw.tmb_server.services.auth;

import com.totalMiniBmw.tmb_server.entities.InvalidJwtEntity;
import com.totalMiniBmw.tmb_server.repository.InvalidJwtRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtLogoutService {

    private final InvalidJwtRepository invalidJwtRepository;

    public JwtLogoutService(InvalidJwtRepository invalidJwtRepository) {
        this.invalidJwtRepository = invalidJwtRepository;
    }

    public void invalidate(String token) {
        String tokenSanitized = token.substring(7); // Remove the "Bearer " prefix (7 characters)

        InvalidJwtEntity invalidJwt = new InvalidJwtEntity();
        invalidJwt.setJwt(tokenSanitized);
        invalidJwtRepository.save(invalidJwt);
    }

    public boolean isTokenInvalid(String token) {
        Optional<InvalidJwtEntity> invalidJwt = invalidJwtRepository.findByJwt(token);
        if (invalidJwt.isPresent()) return true;
        return false;
    }

}
