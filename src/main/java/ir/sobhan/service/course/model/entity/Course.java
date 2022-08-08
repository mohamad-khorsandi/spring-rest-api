package ir.sobhan.service.course.model.entity;

import ir.sobhan.service.courseSection.model.entity.CourseSection;
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
@Table(indexes = @Index(columnList = "id", unique = true))
public class Course {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    @Setter(value = AccessLevel.PRIVATE)
    private Long id;
    private String title;
    private int units;
}
