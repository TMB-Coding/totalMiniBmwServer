package com.totalMiniBmw.tmb_server.controller.management;

import com.totalMiniBmw.tmb_server.dto.UserDeleteDto;
import com.totalMiniBmw.tmb_server.dto.UserRegisterDto;
import com.totalMiniBmw.tmb_server.dto.responses.GenericActionResponse;
import com.totalMiniBmw.tmb_server.dto.responses.GenericActionType;
import com.totalMiniBmw.tmb_server.entities.UserEntity;
import com.totalMiniBmw.tmb_server.services.UserService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mgmt/user")
public class UserMController {

    private final UserService userService;

    public UserMController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserRegisterDto user) {
        UserEntity userEntity = userService.create(user);
        return ResponseEntity.ok(userEntity);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping("/")
    public ResponseEntity<UserEntity> updateUser() {
        return ResponseEntity.ok(new UserEntity());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/delete")
    public ResponseEntity<GenericActionResponse> deleteUser(@RequestBody UserDeleteDto user) {

        String response = userService.deleteUserFromEmail(user.getEmail());


        GenericActionResponse gar = new GenericActionResponse(response, null, GenericActionType.POST);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(gar);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') AND hasAuthority('SESSION_ALL_APPS')")
    @GetMapping("/")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> allUsers = userService.getAllUsers();

        return ResponseEntity.ok(allUsers);
    }
}
