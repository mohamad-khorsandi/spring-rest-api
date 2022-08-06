package ir.sobhan.service.courseSection.model.entity;

import ir.sobhan.service.course.model.entity.Course;
import ir.sobhan.service.term.model.entity.Term;
import ir.sobhan.service.user.model.entity.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseSection {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Term term;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CourseSectionRegistration> registrationList;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Course course;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User instructor;
}
