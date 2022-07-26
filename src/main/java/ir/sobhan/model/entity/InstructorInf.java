package ir.sobhan.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstructorInf {
    @Id @GeneratedValue @Column(nullable = false)
    private Long id;
    private Rank rank;
    public enum Rank{
        ASSISTANT, ASSOCIATE_FULL
    }
}
