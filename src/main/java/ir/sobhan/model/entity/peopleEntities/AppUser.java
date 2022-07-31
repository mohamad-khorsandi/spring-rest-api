package ir.sobhan.model.entity.peopleEntities;

import ir.sobhan.security.Role;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "user_tlb")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id @GeneratedValue @Column(nullable = false)
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

    private String name;
    private String phone;
    private String nationalId;
    private boolean isStudent;
    private boolean isInstructor;

    @Setter(value = AccessLevel.PRIVATE)
    private boolean isActive;

    @Setter(value = AccessLevel.PRIVATE)
    private Boolean isAdmin;

    public List<SimpleGrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (isStudent())
            authorities.add(new SimpleGrantedAuthority(Role.STUDENT.toString()));
        if(getIsAdmin())
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.toString()));
        if(isInstructor())
            authorities.add((new SimpleGrantedAuthority(Role.INSTRUCTOR.toString())));

        return authorities;
    }
}