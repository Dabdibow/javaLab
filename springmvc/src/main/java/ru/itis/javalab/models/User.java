package ru.itis.javalab.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;
    private String password;

    @Column(name = "confirm_code")
    private String confirmCode;

    @Enumerated(value = EnumType.STRING)
    private State state;

    @Column(name = "confirm_state")
    @Enumerated(value = EnumType.STRING)
    private ConfirmState confirmState;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public boolean isActive() {
        return this.state == State.ACTIVE;
    }

    public boolean isBanned() {
        return this.state == State.BANNED;
    }

    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    public boolean isConfirmed() {
        return this.confirmState == ConfirmState.CONFIRMED;
    }
}
