package io.github.xpakx.tomatogarden.service;

import io.github.xpakx.tomatogarden.entity.Pomodoro;
import io.github.xpakx.tomatogarden.entity.dto.StartRequest;
import io.github.xpakx.tomatogarden.error.PomodoroEditException;
import io.github.xpakx.tomatogarden.error.PomodoroNotFoundException;
import io.github.xpakx.tomatogarden.error.TagNotFoundException;
import io.github.xpakx.tomatogarden.repository.PomodoroRepository;
import io.github.xpakx.tomatogarden.repository.TagRepository;
import io.github.xpakx.tomatogarden.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class PomodoroService {
    private final PomodoroRepository pomodoroRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    private Long getIdByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow()
                .getId();
    }
    public Pomodoro startNewPomodoro(StartRequest request, String username) {
        Long userId = getIdByUsername(username);
        Pomodoro pomodoro = new Pomodoro();
        pomodoro.setDeepFocus(request.isDeepFocus());
        pomodoro.setCollaborative(request.isCollaborative());
        pomodoro.setFailed(false);
        pomodoro.setSucceed(false);
        pomodoro.setStart(LocalDateTime.now());
        pomodoro.setMinutes(request.getMinutes());
        pomodoro.setPaused(false);
        pomodoro.setSecondsAfterPause(0);
        pomodoro.setOwner(userRepository.getById(userId));
        if(request.getTagId() != null) {
            pomodoro.setTag(
                    tagRepository.findByOwnerIdAndId(userId, request.getTagId())
                            .orElseThrow(() -> new TagNotFoundException("Tag not found!"))
            );
        }
        return  pomodoroRepository.save(pomodoro);
    }

    public Pomodoro stopPomodoro(String username, Long pomodoroId) {
        Long userId = getIdByUsername(username);
        LocalDateTime now = LocalDateTime.now();
        Pomodoro pomodoro = pomodoroRepository.findByOwnerIdAndId(userId, pomodoroId)
                .orElseThrow(() -> new PomodoroNotFoundException("Pomodoro not found!"));

        if(isSuccessful(now, pomodoro)) {
            pomodoro.setSucceed(true);
        } else {
            pomodoro.setFailed(true);
        }
        pomodoro.setFinish(now);

        return  pomodoroRepository.save(pomodoro);
    }

    public Pomodoro cancelPomodoro(String username, Long pomodoroId) {
        Long userId = getIdByUsername(username);
        LocalDateTime now = LocalDateTime.now();
        Pomodoro pomodoro = pomodoroRepository.findByOwnerIdAndId(userId, pomodoroId)
                .orElseThrow(() -> new PomodoroNotFoundException("Pomodoro not found!"));

        if(pomodoro.isSucceed() || pomodoro.isFailed()) {
            throw new PomodoroEditException("Pomodoro is already finished!");
        }

        pomodoro.setFailed(true);
        pomodoro.setFinish(now);

        return  pomodoroRepository.save(pomodoro);
    }

    private boolean isSuccessful(LocalDateTime now, Pomodoro pomodoro) {
        LocalDateTime end = pomodoro.getStart().plusMinutes(pomodoro.getMinutes());
        LocalDateTime buffer = pomodoro.getStart().plusMinutes(pomodoro.getMinutes()+5);
        if(now.isBefore(end)) {
            throw new PomodoroEditException("Pomodoro isn't finished yet!");
        }

        return !now.isAfter(buffer);
    }

    public Pomodoro pausePomodoro(String username, Long pomodoroId) {
        Long userId = getIdByUsername(username);
        LocalDateTime now = LocalDateTime.now();
        Pomodoro pomodoro = pomodoroRepository.findByOwnerIdAndId(userId, pomodoroId)
                .orElseThrow(() -> new PomodoroNotFoundException("Pomodoro not found!"));

        if(pomodoro.isSucceed() || pomodoro.isFailed()) {
            throw new PomodoroEditException("Pomodoro is already finished!");
        }
        if(pomodoro.isDeepFocus()) {
            throw new PomodoroEditException("You cannot pause while in deep focus!");
        }

        pomodoro.setPaused(true);
        if(pomodoro.getAfterPauseStart() == null) {
            pomodoro.setSecondsAfterPause(
                    pomodoro.getMinutes()*60 - (int)ChronoUnit.SECONDS.between(pomodoro.getStart(), now)
            );
        } else {
            pomodoro.setSecondsAfterPause(
                    pomodoro.getSecondsAfterPause() - (int)ChronoUnit.SECONDS.between(pomodoro.getAfterPauseStart(), now)
            );
        }

        return  pomodoroRepository.save(pomodoro);
    }

    public Pomodoro restartPomodoro(String username, Long pomodoroId) {
        Long userId = getIdByUsername(username);
        Pomodoro pomodoro = pomodoroRepository.findByOwnerIdAndId(userId, pomodoroId)
                .orElseThrow(() -> new PomodoroNotFoundException("Pomodoro not found!"));

        if(!pomodoro.isPaused()) {
            throw new PomodoroEditException("Pomodoro isn't paused!");
        }

        pomodoro.setPaused(false);
        pomodoro.setAfterPauseStart(LocalDateTime.now());

        return  pomodoroRepository.save(pomodoro);
    }
}
