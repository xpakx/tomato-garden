package io.github.xpakx.tomatogarden.service;

import io.github.xpakx.tomatogarden.entity.UserAccount;
import io.github.xpakx.tomatogarden.entity.UserRole;
import io.github.xpakx.tomatogarden.entity.UserSettings;
import io.github.xpakx.tomatogarden.entity.dto.AuthenticationRequest;
import io.github.xpakx.tomatogarden.entity.dto.AuthenticationResponse;
import io.github.xpakx.tomatogarden.entity.dto.RegistrationRequest;
import io.github.xpakx.tomatogarden.error.JwtBadCredentialsException;
import io.github.xpakx.tomatogarden.error.UserDisabledException;
import io.github.xpakx.tomatogarden.repository.UserRepository;
import io.github.xpakx.tomatogarden.repository.UserSettingsRepository;
import io.github.xpakx.tomatogarden.security.JwtTokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtil;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserSettingsRepository settingsRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse generateAuthenticationToken(AuthenticationRequest authenticationRequest) {
        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        authenticate(userDetails.getUsername(), authenticationRequest.getPassword());
        final String token = jwtTokenUtil.generateToken(userDetails);
        Optional<UserSettings> settings = settingsRepository.findByUserUsername(userDetails.getUsername());
        return AuthenticationResponse.builder()
                .token(token)
                .username(userDetails.getUsername())
                .defaultBreakLength(settings.map(UserSettings::getDefaultBreakLength).orElse(5))
                .defaultPomodoroLength(settings.map(UserSettings::getDefaultPomodoroLength).orElse(25))
                .defaultFocus(settings.map(UserSettings::isDefaultFocus).orElse(false))
                .build();
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new UserDisabledException("User " +username+" disabled!");
        } catch (BadCredentialsException e) {
            throw new JwtBadCredentialsException("Invalid password!");
        }
    }

    public AuthenticationResponse register(RegistrationRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ValidationException("Username exists!");
        }
        if (!request.getPassword().equals(request.getPasswordRe())) {
            throw new ValidationException("Passwords don't match!");
        }
        Set<UserRole> roles = new HashSet<>();

        UserAccount userToAdd = new UserAccount();
        userToAdd.setPassword(passwordEncoder.encode(request.getPassword()));
        userToAdd.setUsername(request.getUsername());
        userToAdd.setRoles(roles);

        userToAdd = userRepository.save(userToAdd);

        UserSettings settings = new UserSettings();
        settings.setDefaultFocus(false);
        settings.setDefaultBreakLength(5);
        settings.setDefaultPomodoroLength(25);
        settings.setUser(userToAdd);
        settingsRepository.save((settings));

        authenticate(request.getUsername(), request.getPassword());
        final String token = jwtTokenUtil.generateToken(userService.userAccountToUserDetails(userToAdd));
        return AuthenticationResponse.builder()
                .token(token)
                .username(userToAdd.getUsername())
                .defaultBreakLength(settings.getDefaultBreakLength())
                .defaultPomodoroLength(settings.getDefaultPomodoroLength())
                .defaultFocus(settings.isDefaultFocus())
                .build();
    }
}
