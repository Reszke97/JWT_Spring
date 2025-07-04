package pl.app.JWT_Backend.user.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "departments")
@Getter
@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departments_seq")
    @SequenceGenerator(name = "departments_seq", sequenceName = "departments_seq", allocationSize = 1)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String code;

    @Setter
    @Column(nullable = false)
    private String label;

    @Setter
    @OneToOne
    @JoinColumn(name = "supervisor_id")
    private AppUser supervisor;

    @Setter
    @Column(name = "allow_helpdesk", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean allowHelpdesk;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Department(){
        super();
    }
}
