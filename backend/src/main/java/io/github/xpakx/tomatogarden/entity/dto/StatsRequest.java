package io.github.xpakx.tomatogarden.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StatsRequest {
    private Long tagId;
    private LocalDateTime before;
    private LocalDateTime after;
}
