package ir.sobhan.service.courseSection.model.entity;

import ir.sobhan.service.term.model.entity.Term;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    @Setter(value = AccessLevel.PRIVATE)
    private Long id;

    @ManyToOne
    private Term term;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<CourseSectionRegistration> registrationList;
}
