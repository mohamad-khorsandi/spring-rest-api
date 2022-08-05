package ir.sobhan.service.user.model.entity;

import ir.sobhan.service.courseSection.model.entity.CourseSectionRegistration;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentInf{
    @Id @GeneratedValue @Column(nullable = false)
    @Setter(value = AccessLevel.PRIVATE)
    private Long id;

    String studentId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<CourseSectionRegistration> registrationSet;

    @Enumerated(EnumType.STRING)
    Degree degree;

    public enum Degree{
        BS, MS, PHD;
    }
}
