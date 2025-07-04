package pl.app.JWT_Backend.user.models;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GenerationType;

import lombok.Getter;
import lombok.Setter;


@Getter
@Table(name = "user_permission_groups")
@Entity
public class UserPermissionGroup {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_permission_groups_seq")
    @SequenceGenerator(name = "user_permission_groups_seq", sequenceName = "user_permission_groups_seq", allocationSize = 1)
    @Id
    private Long id;

    @Setter
    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private AppUser appUser;

    @Getter
    @Setter
    @JoinColumn(name = "permission_group_id", nullable = false)
    @ManyToOne
    private PermissionGroup permissionGroup;

    @Setter
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Setter
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public UserPermissionGroup(){
        super();
    }

}
