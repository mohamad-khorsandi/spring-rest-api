package ir.sobhan.business.userService;

import ir.sobhan.business.SecurityService.Role;
import ir.sobhan.service.user.model.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    public List<SimpleGrantedAuthority> getAuthorities(User user) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if (user.isStudent())
            authorities.add(new SimpleGrantedAuthority(Role.STUDENT.getROLE_str()));

        if (user.isAdmin())
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getROLE_str()));

        if (user.isInstructor())
            authorities.add((new SimpleGrantedAuthority(Role.INSTRUCTOR.getROLE_str())));

        return authorities;
    }
}
