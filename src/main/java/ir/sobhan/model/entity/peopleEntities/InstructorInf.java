package ir.sobhan.model.entity.peopleEntities;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstructorInf {
    @Id @GeneratedValue @Column(nullable = false)
    @Setter(value = AccessLevel.PRIVATE)
    private Long id;
    private Rank rank;
    public enum Rank{
        ASSISTANT, ASSOCIATE_FULL
    }
}
