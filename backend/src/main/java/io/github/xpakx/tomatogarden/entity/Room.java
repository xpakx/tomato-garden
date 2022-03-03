package io.github.xpakx.tomatogarden.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;
    private String code;

    @OneToOne(mappedBy = "room")
    private Pomodoro pomodoro;

    @JsonIgnore
    @ManyToMany(cascade={CascadeType.MERGE})
    @JoinTable(name="room_user",
            joinColumns={@JoinColumn(name="room_id")},
            inverseJoinColumns={@JoinColumn(name="user_account_id")})
    Set<UserAccount> users;
}
