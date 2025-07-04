package pl.app.JWT_Backend.user.models;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "permissions_assigned_to_groups")
@Getter
@Entity
public class PermissionAssignedToGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permissions_assigned_to_groups_seq")
    @SequenceGenerator(name = "permissions_assigned_to_groups_seq", sequenceName = "permissions_assigned_to_groups_seq", allocationSize = 1)
    private Long id;

    @Setter
    @JoinColumn(name = "permission_group_id", nullable = false)
    @ManyToOne
    private PermissionGroup permissionGroup;

    @Setter
    @JoinColumn(name = "permission_id", nullable = false)
    @ManyToOne
    private Permission permission;

    @Setter
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Setter
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public PermissionAssignedToGroup(){
        super();
    }

}