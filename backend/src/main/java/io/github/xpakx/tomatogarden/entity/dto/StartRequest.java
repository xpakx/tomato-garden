package io.github.xpakx.tomatogarden.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StartRequest {
    private boolean deepFocus;
    private boolean collaborative;
    private Integer minutes;
    private Long tagId;
}
