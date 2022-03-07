package io.github.xpakx.tomatogarden.controller;

import io.github.xpakx.tomatogarden.entity.Tag;
import io.github.xpakx.tomatogarden.entity.dto.TagDto;
import io.github.xpakx.tomatogarden.entity.dto.TagRequest;
import io.github.xpakx.tomatogarden.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class TagController {
    private final TagService tagService;

    @PreAuthorize("#username.equals(authentication.principal.username)")
    @PostMapping("/{username}/tag")
    public ResponseEntity<Tag> create(@RequestBody TagRequest request, @PathVariable String username) {
        return new ResponseEntity<>(
                tagService.newTag(request, username),
                HttpStatus.CREATED
        );
    }

    @PreAuthorize("#username.equals(authentication.principal.username)")
    @PutMapping("/{username}/tag/{tagId}")
    public ResponseEntity<Tag> update(@RequestBody TagRequest request, @PathVariable String username, @PathVariable Long tagId) {
        return new ResponseEntity<>(
                tagService.editTag(request, username, tagId),
                HttpStatus.OK
        );
    }

    @PreAuthorize("#username.equals(authentication.principal.username)")
    @DeleteMapping("/{username}/tag/{tagId}")
    public ResponseEntity<?> delete(@PathVariable String username, @PathVariable Long tagId) {
        tagService.deleteTag(username, tagId);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @PreAuthorize("#username.equals(authentication.principal.username)")
    @GetMapping("/{username}/tag")
    public ResponseEntity<List<TagDto>> getAll(@PathVariable String username) {
        return new ResponseEntity<>(
                tagService.getTags(username),
                HttpStatus.OK
        );
    }
}
