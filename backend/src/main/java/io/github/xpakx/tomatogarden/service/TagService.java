package io.github.xpakx.tomatogarden.service;

import io.github.xpakx.tomatogarden.entity.Pomodoro;
import io.github.xpakx.tomatogarden.entity.Tag;
import io.github.xpakx.tomatogarden.entity.dto.IdRequest;
import io.github.xpakx.tomatogarden.entity.dto.TagDto;
import io.github.xpakx.tomatogarden.entity.dto.TagRequest;
import io.github.xpakx.tomatogarden.error.PomodoroNotFoundException;
import io.github.xpakx.tomatogarden.error.TagNotFoundException;
import io.github.xpakx.tomatogarden.error.UserNotFoundException;
import io.github.xpakx.tomatogarden.repository.PomodoroRepository;
import io.github.xpakx.tomatogarden.repository.TagRepository;
import io.github.xpakx.tomatogarden.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagService {
    private final PomodoroRepository pomodoroRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    private Long getIdByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(UserNotFoundException::new)
                .getId();
    }

    public Tag newTag(TagRequest request, String username) {
        Long userId = getIdByUsername(username);
        Tag tag = new Tag();
        tag.setOwner(userRepository.getById(userId));
        tag.setColor(request.getColor());
        tag.setName(request.getName());
        return  tagRepository.save(tag);
    }

    public Tag editTag(TagRequest request, String username, Long tagId) {
        Tag tag = tagRepository
                .findByOwnerIdAndId(getIdByUsername(username), tagId)
                .orElseThrow(() -> new TagNotFoundException("Tag doesn't exist!"));
        tag.setColor(request.getColor());
        tag.setName(request.getName());
        return  tagRepository.save(tag);
    }

    public void deleteTag(String username, Long tagId) {
        Tag tag = tagRepository
                .findByOwnerIdAndId(getIdByUsername(username), tagId)
                .orElseThrow(() -> new TagNotFoundException("Tag doesn't exist!"));
        tagRepository.delete(tag);
    }

    public List<TagDto> getTags(String username) {
        return tagRepository.findByOwnerId(getIdByUsername(username));
    }

    public Pomodoro updateTagForPomodoro(IdRequest request, String username, Long pomodoroId) {
        Long userId = getIdByUsername(username);
        Pomodoro pomodoro = pomodoroRepository.findByOwnerIdAndId(userId, pomodoroId)
                .orElseThrow(() -> new PomodoroNotFoundException("Pomodoro not found!"));
        Tag tag = tagRepository.findByOwnerIdAndId(userId, request.getId())
                .orElseThrow(() -> new TagNotFoundException("Tag doesn't exist!"));
        pomodoro.setTag(tag);
        return pomodoroRepository.save(pomodoro);
    }
}
