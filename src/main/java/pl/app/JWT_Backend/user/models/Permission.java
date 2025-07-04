package pl.app.JWT_Backend.user.models;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Table(name = "permissions")
@Getter
@Entity
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permissions_seq")
    @SequenceGenerator(name = "permissions_seq", sequenceName = "permissions_seq", allocationSize = 1)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(name = "description_polish")
    private String descriptionInPolish;

    @Setter
    @Column(name = "description_english")
    private String descriptionInEnglish;

    @Setter
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Setter
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Permission(){
        super();
    }
}
