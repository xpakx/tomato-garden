package io.github.xpakx.tomatogarden.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PomodoroEditException extends RuntimeException {
    public PomodoroEditException(String message) {
        super(message);
    }
}
