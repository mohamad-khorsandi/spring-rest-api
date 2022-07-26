package ir.sobhan.model.entity.peopleEntities;

import lombok.*;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    Degree degree;

    public enum Degree{
        BS, MS, PHD;
    }
}
