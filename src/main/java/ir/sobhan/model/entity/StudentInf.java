package ir.sobhan.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentInf {
    @Id @GeneratedValue @Column(nullable = false)
    @Setter(value = AccessLevel.PRIVATE)
    private Long id;

    String studentId;

    @Enumerated(EnumType.STRING)//todo: what kind of thing need relation annotation?
    Degree degree;

    public enum Degree{
        BS, MS, PHD;
    }
}
