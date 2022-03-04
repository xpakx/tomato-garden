package io.github.xpakx.tomatogarden.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PomodoroNotFoundException extends RuntimeException {
    public PomodoroNotFoundException(String message) {
        super(message);
    }
}
