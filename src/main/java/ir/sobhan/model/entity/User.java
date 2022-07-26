package ir.sobhan.model.entity;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "user_tlb")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id @GeneratedValue @Column(nullable = false)
    @Setter(value = AccessLevel.PRIVATE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private StudentInf studentInf;

    @OneToOne(cascade = CascadeType.ALL)
    private InstructorInf instructorInf;

    private String name;
    private String phone;
    private String nationalId;
    boolean isStudent;
    boolean isInstructor;

    @Setter(value = AccessLevel.PRIVATE)
    private boolean isActive;

    @Setter(value = AccessLevel.PRIVATE)
    private Boolean isAdmin;
}