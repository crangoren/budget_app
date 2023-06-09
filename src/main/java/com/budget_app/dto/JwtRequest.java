package com.budget_app.dto;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
