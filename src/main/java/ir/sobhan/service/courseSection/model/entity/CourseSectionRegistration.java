package ir.sobhan.service.courseSection.model.entity;

import ir.sobhan.service.user.model.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseSectionRegistration {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    @Setter(value = AccessLevel.PRIVATE)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    User student;

    @ManyToOne
    CourseSection section;

    double score;
}
