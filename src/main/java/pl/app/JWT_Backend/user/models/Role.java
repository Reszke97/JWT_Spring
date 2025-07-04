package pl.app.JWT_Backend.user.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.JWT_Backend.user.enums.UserRole;
import pl.app.JWT_Backend.user.utils.UserRolesInterface;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
@Getter
@Entity
public class Role implements UserRolesInterface {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq")
    @SequenceGenerator(name = "roles_seq", sequenceName = "roles_seq", allocationSize = 1)
    @Id
    private Long id;

    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
