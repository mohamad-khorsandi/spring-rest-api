package ir.sobhan.service.user.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_tlb", indexes = {
        @Index(columnList = "id", unique = true),
        @Index(columnList = "username", unique = true)
})

public class User {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    @Setter(value = AccessLevel.PRIVATE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private StudentInf studentInf;

    @OneToOne(cascade = CascadeType.ALL)
    private InstructorInf instructorInf;

    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String phone;
    @Column(unique = true, nullable = false)
    private String nationalId;

    private boolean student;
    private boolean instructor;
    private boolean active;
    private boolean admin;
}