package ir.sobhan.service.user.model.entity;

import ir.sobhan.service.courseSection.model.entity.CourseSection;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = @Index(columnList = "id", unique = true))
public class InstructorInf {
    @Id @GeneratedValue @Column(nullable = false)
    @Setter(value = AccessLevel.PRIVATE)
    private Long id;
    private Rank rank;

    public enum Rank{
        ASSISTANT, ASSOCIATE_FULL
    }
}
