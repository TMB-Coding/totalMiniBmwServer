package com.totalMiniBmw.tmb_server.services.auth;

import com.totalMiniBmw.tmb_server.entities.UserEntity;
import com.totalMiniBmw.tmb_server.entities.enums.Authority;
import com.totalMiniBmw.tmb_server.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorityService {

    private final UserRepository userRepository;

    public AuthorityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void grantAuthority(UserEntity user, Authority authority) {
        List<Authority> newAuthorities = new ArrayList<>();
        user.getAuthorities().stream().parallel().forEach(authority1 -> {
            newAuthorities.add(Authority.valueOf(authority1.getAuthority()));
        });
        newAuthorities.add(Authority.valueOf(authority.name()));
        user.setAuthorities(newAuthorities);
        userRepository.save(user);
    }

    public List<UserEntity> retrieveAuthorities(Authority authority) {
        return this.userRepository.findAllByAuthority(String.valueOf(authority).toUpperCase());
    }

    @Transactional
    public void revokeAuthority(String id, Authority authority) {
        this.userRepository.removeAuthorityFromUser(id, authority.name());
    }
}
