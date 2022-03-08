package io.github.xpakx.tomatogarden.service;


import io.github.xpakx.tomatogarden.entity.Pomodoro;
import io.github.xpakx.tomatogarden.repository.PomodoroRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PomodoroCleanerService {
    private final PomodoroRepository repository;

    @Scheduled(cron = "0 0 * * * *")
    public void schedulePomodoroCleanUpEveryHour() {
        LocalDateTime now = LocalDateTime.now();
        List<Pomodoro> pomodoros = repository
                .findAllByFailedAndSucceedAndPaused(false, false, false)
                .stream()
                .filter((a) -> now.isAfter(getPomodoroEndTime(a)))
                .collect(Collectors.toList());
        pomodoros.forEach((a) -> a.setFailed(true));
        repository.saveAll(pomodoros);
    }

    private LocalDateTime getPomodoroEndTime(Pomodoro pomodoro) {
        if(pomodoro.getAfterPauseStart() == null) {
            return pomodoro.getStart().plusMinutes(pomodoro.getMinutes());
        } else {
            return pomodoro.getAfterPauseStart().plusSeconds(pomodoro.getSecondsAfterPause());
        }
    }

}
