package ir.sobhan.service.courseSection.model.entity;

import ir.sobhan.service.user.model.entity.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = @Index(columnList = "id", unique = true))
public class CourseSectionRegistration {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    @Setter(value = AccessLevel.PRIVATE)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    User student;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn
    CourseSection section;

    double score;
}
