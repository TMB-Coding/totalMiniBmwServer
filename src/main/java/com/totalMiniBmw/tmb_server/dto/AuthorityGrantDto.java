package com.totalMiniBmw.tmb_server.dto;

import com.totalMiniBmw.tmb_server.entities.enums.Authority;
import lombok.Data;

@Data
public class AuthorityGrantDto {
    private String email;
    private String authority;

    public Authority getAuthority() {
        return Authority.valueOf(authority);
    }
}
