package io.github.xpakx.tomatogarden.service;

import io.github.xpakx.tomatogarden.entity.Pomodoro;
import io.github.xpakx.tomatogarden.entity.dto.PomodoroMin;
import io.github.xpakx.tomatogarden.entity.dto.StatsRequest;
import io.github.xpakx.tomatogarden.entity.dto.StatsResponse;
import io.github.xpakx.tomatogarden.repository.PomodoroStatsRepository;
import io.github.xpakx.tomatogarden.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public StatsResponse getTimelinePage(String username, StatsRequest request) {
        Long userId = getIdByUsername(username);
        List<Pomodoro> pomodoros = getFromRequest(request, userId);
        return StatsResponse.builder()
                .succeed(
                        pomodoros.stream().filter(Pomodoro::isSucceed).count()
                )
                .failed(
                        pomodoros.stream().filter(Pomodoro::isFailed).count()
                )
                .build();
    }

    private List<Pomodoro> getFromRequest(StatsRequest request, Long userId) {
        if (request.getTagId() != null) {
            return getWithTag(request.getBefore(), request.getAfter(), userId, request.getTagId());
        }
        return getWithoutTag(request.getBefore(), request.getAfter(), userId);
    }

    private List<Pomodoro> getWithoutTag(LocalDateTime before, LocalDateTime after, Long userId) {
        if(before != null && after != null) {
            return statsRepository.findByOwnerIdAndStartBeforeAndStartAfter(userId, before, after);
        }
        if(before != null) {
            return statsRepository.findByOwnerIdAndStartBefore(userId, before);
        }
        if(after != null) {
            return statsRepository.findByOwnerIdAndStartAfter(userId, after);
        }
        return statsRepository.findByOwnerId(userId);
    }

    private List<Pomodoro> getWithTag(LocalDateTime before, LocalDateTime after, Long userId, Long tagId) {
        if(before != null && after != null) {
            return statsRepository.findByOwnerIdAndTagIdAndStartBeforeAndStartAfter(userId, tagId, before, after);
        }
        if(before != null) {
            return statsRepository.findByOwnerIdAndTagIdAndStartBefore(userId, tagId, before);
        }
        if(after != null) {
            return statsRepository.findByOwnerIdAndTagIdAndStartAfter(userId, tagId, after);
        }
        return statsRepository.findByOwnerIdAndTagId(userId, tagId);
    }
}
