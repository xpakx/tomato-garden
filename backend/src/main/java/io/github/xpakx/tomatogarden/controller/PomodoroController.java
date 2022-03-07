package io.github.xpakx.tomatogarden.controller;

import io.github.xpakx.tomatogarden.entity.Pomodoro;
import io.github.xpakx.tomatogarden.entity.dto.AuthenticationRequest;
import io.github.xpakx.tomatogarden.entity.dto.AuthenticationResponse;
import io.github.xpakx.tomatogarden.entity.dto.StartRequest;
import io.github.xpakx.tomatogarden.service.PomodoroService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class PomodoroController {
    private final PomodoroService pomodoroService;

    @PreAuthorize("#username.equals(authentication.principal.username)")
    @PostMapping("/{username}/pomodoro")
    public ResponseEntity<Pomodoro> start(@RequestBody StartRequest request, @PathVariable String username) {
        return new ResponseEntity<>(
                pomodoroService.startNewPomodoro(request, username),
                HttpStatus.OK
        );
    }

    @PreAuthorize("#username.equals(authentication.principal.username)")
    @PutMapping("/{username}/pomodoro/{pomodoroId}/stop")
    public ResponseEntity<Pomodoro> stop(@PathVariable String username, @PathVariable Long pomodoroId) {
        return new ResponseEntity<>(
                pomodoroService.stopPomodoro(username, pomodoroId),
                HttpStatus.OK
        );
    }

    @PreAuthorize("#username.equals(authentication.principal.username)")
    @PutMapping("/{username}/pomodoro/{pomodoroId}/cancel")
    public ResponseEntity<Pomodoro> cancel(@PathVariable String username, @PathVariable Long pomodoroId) {
        return new ResponseEntity<>(
                pomodoroService.cancelPomodoro(username, pomodoroId),
                HttpStatus.OK
        );
    }
}
