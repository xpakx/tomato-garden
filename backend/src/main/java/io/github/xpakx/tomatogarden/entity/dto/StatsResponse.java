package io.github.xpakx.tomatogarden.entity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatsResponse {
    private Long succeed;
    private Long failed;
}
