package io.github.xpakx.tomatogarden.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SettingsNotFoundException extends RuntimeException  {
    public SettingsNotFoundException(String message) {
        super(message);
    }
}
