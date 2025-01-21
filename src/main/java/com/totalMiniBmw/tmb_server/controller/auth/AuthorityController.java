package com.totalMiniBmw.tmb_server.controller.auth;

import com.totalMiniBmw.tmb_server.dto.AuthorityGrantDto;
import com.totalMiniBmw.tmb_server.dto.AuthorityRevokeDto;
import com.totalMiniBmw.tmb_server.dto.responses.GenericActionResponse;
import com.totalMiniBmw.tmb_server.dto.responses.GenericActionType;
import com.totalMiniBmw.tmb_server.entities.UserEntity;
import com.totalMiniBmw.tmb_server.entities.enums.Authority;
import com.totalMiniBmw.tmb_server.services.UserService;
import com.totalMiniBmw.tmb_server.services.auth.AuthorityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authority")
public class AuthorityController {

    private final AuthorityService authorityService;
    private final UserService userService;

    public AuthorityController(AuthorityService authorityService, UserService userService) {
        this.authorityService = authorityService;
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/")
    public UserEntity grantAuthorityToUser(@RequestBody AuthorityGrantDto input) {
        UserEntity user = userService.getUserFromEmail(input.getEmail());
        authorityService.grantAuthority(user, input.getAuthority());
        return user;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/retrieve/{authorityType}")
    public List<UserEntity> retrieveAuthorities(@PathVariable Authority authorityType) {
        return authorityService.retrieveAuthorities(authorityType);

    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/revoke/{userId}")
    public ResponseEntity<GenericActionResponse> revokeAuthorityFromUser(@PathVariable String userId, @RequestBody AuthorityRevokeDto authority) {
        this.authorityService.revokeAuthority(userId, authority.getAuthority());

        GenericActionResponse gar = new GenericActionResponse("Authority revoked.", null, GenericActionType.DELETE);

        return ResponseEntity.ok(gar);
    }

}
