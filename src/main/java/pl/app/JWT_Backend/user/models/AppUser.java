package pl.app.JWT_Backend.user.models;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.JWT_Backend.user.enums.Theme;
import pl.app.JWT_Backend.user.utils.UserRolesInterface;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "app_users")
@Getter
@Entity
public class AppUser implements UserRolesInterface {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_users_seq")
    @SequenceGenerator(name = "app_users_seq", sequenceName = "app_users_seq", allocationSize = 1)
    @Id
    private Long id;

    @Setter
    @Column(nullable = false)
    private String username;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private String surname;

    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @OneToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Setter
    @OneToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Setter
    @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isActive;

    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Theme theme;

    @Setter
    @OneToMany(mappedBy = "appUser")
    private List<UserPermissionGroup> userPermissionGroups;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
