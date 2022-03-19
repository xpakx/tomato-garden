package io.github.xpakx.tomatogarden.repository;

import io.github.xpakx.tomatogarden.entity.Pomodoro;
import io.github.xpakx.tomatogarden.entity.dto.PomodoroMin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PomodoroStatsRepository extends PagingAndSortingRepository<Pomodoro, Long> {
    Page<PomodoroMin> findByOwnerId(Long ownerId, Pageable pageable);
}