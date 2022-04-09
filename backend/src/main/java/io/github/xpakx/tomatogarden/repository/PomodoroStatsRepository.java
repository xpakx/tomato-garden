package io.github.xpakx.tomatogarden.repository;

import io.github.xpakx.tomatogarden.entity.Pomodoro;
import io.github.xpakx.tomatogarden.entity.dto.PomodoroMin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PomodoroStatsRepository extends PagingAndSortingRepository<Pomodoro, Long> {
    Page<PomodoroMin> findByOwnerId(Long ownerId, Pageable pageable);
    List<Pomodoro> findByOwnerId(Long ownerId);
    List<Pomodoro> findByOwnerIdAndStartBefore(Long ownerId, LocalDateTime before);
    List<Pomodoro> findByOwnerIdAndStartAfter(Long ownerId, LocalDateTime after);
    List<Pomodoro> findByOwnerIdAndStartBeforeAndStartAfter(Long ownerId, LocalDateTime before, LocalDateTime after);
    List<Pomodoro> findByOwnerIdAndTagId(Long ownerId, Long tagId);
    List<Pomodoro> findByOwnerIdAndTagIdAndStartBefore(Long ownerId, Long tagId, LocalDateTime before);
    List<Pomodoro> findByOwnerIdAndTagIdAndStartAfter(Long ownerId, Long tagId, LocalDateTime after);
    List<Pomodoro> findByOwnerIdAndTagIdAndStartBeforeAndStartAfter(Long ownerId, Long tagId, LocalDateTime before, LocalDateTime after);
}