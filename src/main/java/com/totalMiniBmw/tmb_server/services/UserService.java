package com.totalMiniBmw.tmb_server.services;

import com.totalMiniBmw.tmb_server.dto.UserRegisterDto;
import com.totalMiniBmw.tmb_server.entities.ToolEntity;
import com.totalMiniBmw.tmb_server.entities.UserEntity;
import com.totalMiniBmw.tmb_server.entities.enums.Authority;
import com.totalMiniBmw.tmb_server.repository.UserRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity create(UserRegisterDto input) {
        UserEntity user = new UserEntity();
        user.setEmployeeNumber(input.getEmployeeNumber());
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setAuthorities(input.getAuthorities());
        return userRepository.save(user);
    }

    public void delete(UserEntity user) {
        userRepository.delete(user);
    }

    public UserEntity update(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity getUserFromEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isEmpty()) throw new ErrorResponseException(HttpStatusCode.valueOf(404));
        return user.get();
    }

    public String deleteUserFromEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isEmpty()) return "Failed to delete the user. The user does not exist.";

        userRepository.delete(user.get());
        return "Deleted the user.";
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
