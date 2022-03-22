package io.github.xpakx.tomatogarden.controller;

import io.github.xpakx.tomatogarden.entity.Pomodoro;
import io.github.xpakx.tomatogarden.entity.UserSettings;
import io.github.xpakx.tomatogarden.entity.dto.SettingsRequest;
import io.github.xpakx.tomatogarden.service.SettingsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class SettingsController {
    private final SettingsService service;

    @PreAuthorize("#username.equals(authentication.principal.username)")
    @PutMapping("/{username}/settings")
    public ResponseEntity<UserSettings> updateSettings(@RequestBody SettingsRequest request, @PathVariable String username) {
        return new ResponseEntity<>(
                service.updateSettings(request, username),
                HttpStatus.OK
        );
    }

    @PreAuthorize("#username.equals(authentication.principal.username)")
    @GetMapping("/{username}/settings")
    public ResponseEntity<UserSettings> getSettings(@PathVariable String username) {
        return new ResponseEntity<>(
                service.getSettings(username),
                HttpStatus.OK
        );
    }
}
