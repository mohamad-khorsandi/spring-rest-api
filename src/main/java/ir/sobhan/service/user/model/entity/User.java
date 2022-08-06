package ir.sobhan.service.user.model.entity;

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
public class User {
    @Id @GeneratedValue @Column(nullable = false)
    @Setter(value = AccessLevel.PRIVATE)
    private Long id;

//    @Column(unique = true, nullable = false)
    private String username;

//    @Column(nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private StudentInf studentInf;

    @OneToOne(cascade = CascadeType.ALL)
    private InstructorInf instructorInf;

//    @Column(nullable = false)
    private String name;
//    @Column(unique = true, nullable = false)
    private String phone;
//    @Column(unique = true, nullable = false)
    private String nationalId;
    private boolean isStudent;
    private boolean isInstructor;
    private boolean isActive;
    private boolean isAdmin;

    public List<SimpleGrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if (isStudent())
            authorities.add(new SimpleGrantedAuthority(Role.STUDENT.getROLE_str()));

        if(isAdmin())
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getROLE_str()));

        if(isInstructor())
            authorities.add((new SimpleGrantedAuthority(Role.INSTRUCTOR.getROLE_str())));

        return authorities;
    }
}