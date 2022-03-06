package io.github.xpakx.tomatogarden.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pomodoro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean deepFocus;
    private boolean collaborative;
    private boolean failed;
    private boolean succeed;

    private LocalDateTime start;
    private LocalDateTime finish;
    private Integer minutes;

    private boolean paused;
    private LocalDateTime afterPauseStart;
    private Integer minutesBeforePause;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount owner;
}
