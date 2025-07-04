package pl.app.JWT_Backend.user.models;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "permission_groups")
@Getter
@Entity
public class PermissionGroup {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_groups_seq")
    @SequenceGenerator(name = "permission_groups_seq", sequenceName = "permission_groups_seq", allocationSize = 1)
    @Id
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Setter
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public PermissionGroup(){
        super();
    }
}
