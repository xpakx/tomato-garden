package io.github.xpakx.tomatogarden.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.xpakx.tomatogarden.entity.Pomodoro;
import io.github.xpakx.tomatogarden.entity.Tag;
import io.github.xpakx.tomatogarden.entity.UserAccount;
import io.github.xpakx.tomatogarden.entity.dto.StartRequest;
import io.github.xpakx.tomatogarden.error.TagNotFoundException;
import io.github.xpakx.tomatogarden.repository.PomodoroRepository;
import io.github.xpakx.tomatogarden.repository.TagRepository;
import io.github.xpakx.tomatogarden.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PomodoroService {
    private final PomodoroRepository pomodoroRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    public Pomodoro startNewPomodoro(StartRequest request, Long userId) {
        Pomodoro pomodoro = new Pomodoro();
        pomodoro.setDeepFocus(request.isDeepFocus());
        pomodoro.setCollaborative(request.isCollaborative());
        pomodoro.setFailed(false);
        pomodoro.setSucceed(false);
        pomodoro.setStart(LocalDateTime.now());
        pomodoro.setMinutes(request.getMinutes());
        pomodoro.setPaused(false);
        pomodoro.setMinutesBeforePause(0);
        pomodoro.setOwner(userRepository.getById(userId));
        if(request.getTagId() != null) {
            pomodoro.setTag(
                    tagRepository.findByOwnerIdAndId(userId, request.getTagId())
                            .orElseThrow(() -> new TagNotFoundException("Tag not found!"))
            );
        }
        return  pomodoroRepository.save(pomodoro);
    }
}
