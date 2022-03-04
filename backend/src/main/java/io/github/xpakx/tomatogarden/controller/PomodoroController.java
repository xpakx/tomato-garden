package io.github.xpakx.tomatogarden.controller;

import io.github.xpakx.tomatogarden.entity.Pomodoro;
import io.github.xpakx.tomatogarden.entity.dto.AuthenticationRequest;
import io.github.xpakx.tomatogarden.entity.dto.AuthenticationResponse;
import io.github.xpakx.tomatogarden.entity.dto.StartRequest;
import io.github.xpakx.tomatogarden.service.PomodoroService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class PomodoroController {
    private final PomodoroService pomodoroService;

    @PostMapping("/{userId}/pomodoro")
    public ResponseEntity<Pomodoro> start(@RequestBody StartRequest request, @PathVariable Long userId) {
        return new ResponseEntity<>(
                pomodoroService.startNewPomodoro(request, userId),
                HttpStatus.OK
        );
    }

    @PutMapping("/{userId}/pomodoro/{pomodoroId}/stop")
    public ResponseEntity<Pomodoro> stop(@PathVariable Long userId, @PathVariable Long pomodoroId) {
        return new ResponseEntity<>(
                pomodoroService.stopPomodoro(userId, pomodoroId),
                HttpStatus.OK
        );
    }
}
