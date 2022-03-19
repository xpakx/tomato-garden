package io.github.xpakx.tomatogarden.entity.dto;

import lombok.Data;

@Data
public class SettingsRequest {
    private Integer pomodoroLength;
    private Integer breakLength;
    private boolean focus;
}
