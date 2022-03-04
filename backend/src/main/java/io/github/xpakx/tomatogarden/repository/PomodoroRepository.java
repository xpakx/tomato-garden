package io.github.xpakx.tomatogarden.repository;

import io.github.xpakx.tomatogarden.entity.Pomodoro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PomodoroRepository extends JpaRepository<Pomodoro, Long> {
    Optional<Pomodoro> findByOwnerIdAndId(Long ownerId, Long id);
}
