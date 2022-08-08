package ir.sobhan.service.term.model.entity;

import ir.sobhan.service.courseSection.model.entity.CourseSection;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = @Index(columnList = "id", unique = true))
public class Term {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    @Setter(value = AccessLevel.PRIVATE)
    private Long id;

    private String title;
    private Boolean open;
}
