package io.github.xpakx.tomatogarden.entity.dto;

import java.time.LocalDateTime;

public interface PomodoroMin {
    Long getId();

    boolean isFailed();
    boolean isSucceed();

    LocalDateTime getStart();
    LocalDateTime getFinish();
    Integer getMinutes();
}
