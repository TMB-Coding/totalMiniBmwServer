package com.totalMiniBmw.tmb_server.controller;

import com.totalMiniBmw.tmb_server.dto.UserRegisterDto;
import com.totalMiniBmw.tmb_server.entities.UserEntity;
import com.totalMiniBmw.tmb_server.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserRegisterDto user) {
        UserEntity userEntity = userService.create(user);
        return ResponseEntity.ok(userEntity);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/")
    public ResponseEntity<UserEntity> deleteUser() {
        return ResponseEntity.ok(new UserEntity());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping("/")
    public ResponseEntity<UserEntity> updateUser() {
        return ResponseEntity.ok(new UserEntity());
    }
}
