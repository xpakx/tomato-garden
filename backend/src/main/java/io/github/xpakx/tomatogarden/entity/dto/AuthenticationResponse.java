package io.github.xpakx.tomatogarden.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationResponse {
    private String token;
    private String username;
    private Integer defaultPomodoroLength;
    private Integer defaultBreakLength;
    private boolean defaultFocus;
}
