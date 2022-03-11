package io.github.xpakx.tomatogarden.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer defaultPomodoroLength;
    private Integer defaultBreakLength;
    private boolean defaultFocus;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserAccount user;
}
