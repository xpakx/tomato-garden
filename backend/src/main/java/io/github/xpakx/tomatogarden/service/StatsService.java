package io.github.xpakx.tomatogarden.service;

import io.github.xpakx.tomatogarden.entity.dto.PomodoroMin;
import io.github.xpakx.tomatogarden.repository.PomodoroStatsRepository;
import io.github.xpakx.tomatogarden.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatsService {
    private final UserRepository userRepository;
    private final PomodoroStatsRepository statsRepository;

    private Long getIdByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow()
                .getId();
    }

    public Page<PomodoroMin> getTimelinePage(String username, Integer page) {
        Long userId = getIdByUsername(username);
        return statsRepository.findByOwnerId(
                userId,
                PageRequest.of(page, 10, Sort.by("start"))
        );
    }
}
