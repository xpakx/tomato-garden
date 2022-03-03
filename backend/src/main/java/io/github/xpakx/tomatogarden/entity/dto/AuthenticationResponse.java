package io.github.xpakx.tomatogarden.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {
    private String token;
    private String username;
}
