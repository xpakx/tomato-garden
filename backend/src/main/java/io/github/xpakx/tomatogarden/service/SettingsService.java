package io.github.xpakx.tomatogarden.service;

import io.github.xpakx.tomatogarden.entity.UserSettings;
import io.github.xpakx.tomatogarden.entity.dto.SettingsRequest;
import io.github.xpakx.tomatogarden.repository.UserSettingsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SettingsService {
    private final UserSettingsRepository settingsRepository;

    private UserSettings getSettingsByUsername(String username) {
        return settingsRepository
                .findByUserUsername(username)
                .orElseThrow();
    }

    public UserSettings updateSettings(SettingsRequest request, String username) {
        UserSettings settings = getSettingsByUsername(username);
        settings.setDefaultPomodoroLength(request.getPomodoroLength());
        settings.setDefaultBreakLength(request.getBreakLength());
        settings.setDefaultFocus(request.isFocus());
        return settingsRepository.save(settings);
    }
}
