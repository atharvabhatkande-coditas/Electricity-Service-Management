package com.coditas.electricityservicemanagement.platform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseTokens {

    private String accessToken;
    private String refreshToken;
}

