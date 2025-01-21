package com.totalMiniBmw.tmb_server.dto.responses;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private long expiresIn;
}
