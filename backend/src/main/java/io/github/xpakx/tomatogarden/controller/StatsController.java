package io.github.xpakx.tomatogarden.controller;

import io.github.xpakx.tomatogarden.entity.dto.PomodoroMin;
import io.github.xpakx.tomatogarden.entity.dto.StatsRequest;
import io.github.xpakx.tomatogarden.entity.dto.StatsResponse;
import io.github.xpakx.tomatogarden.service.StatsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class StatsController {
    private final StatsService service;

    @PreAuthorize("#username.equals(authentication.principal.username)")
    @PostMapping("/{username}/stats")
    public ResponseEntity<StatsResponse> getStats(@RequestBody StatsRequest request, @PathVariable String username) {
        return new ResponseEntity<>(
                service.getStats(username, request),
                HttpStatus.OK
        );
    }

    @PreAuthorize("#username.equals(authentication.principal.username)")
    @GetMapping("/{username}/timeline")
    public ResponseEntity<Page<PomodoroMin>> getTimeline(@PathVariable String username) {
        return new ResponseEntity<>(
                service.getTimelinePage(username, 0),
                HttpStatus.OK
        );
    }

    @PreAuthorize("#username.equals(authentication.principal.username)")
    @GetMapping("/{username}/timeline/{page}")
    public ResponseEntity<Page<PomodoroMin>> getTimelineForPage(@PathVariable String username, @PathVariable Integer page) {
        return new ResponseEntity<>(
                service.getTimelinePage(username, page),
                HttpStatus.OK
        );
    }
}
