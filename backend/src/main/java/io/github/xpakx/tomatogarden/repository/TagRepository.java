package io.github.xpakx.tomatogarden.repository;

import io.github.xpakx.tomatogarden.entity.Tag;
import io.github.xpakx.tomatogarden.entity.dto.TagDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository  extends JpaRepository<Tag, Long> {
    Optional<Tag> findByOwnerIdAndId(Long ownerId, Long id);
    List<TagDto> findByOwnerId(Long ownerId);
}
