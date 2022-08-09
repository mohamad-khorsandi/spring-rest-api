package ir.sobhan.conf;

import ir.sobhan.business.DBService.DBService;
import ir.sobhan.business.userService.UserService;
import ir.sobhan.service.course.dao.CourseRepository;
import ir.sobhan.service.course.model.entity.Course;
import ir.sobhan.service.term.dao.TermRepository;
import ir.sobhan.service.term.model.entity.Term;
import ir.sobhan.service.user.dao.UserRepository;
import ir.sobhan.service.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanMaker {
    final private UserRepository userRepository;
    final private TermRepository termRepository;
    final private CourseRepository courseRepository;
    final private UserService userService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username Not Found"));

            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    passwordEncoder().encode(user.getPassword()),
                    userService.getAuthorities(user));
        };
    }

    @Bean
    DBService<Term> termDBService() {
        return new DBService<>(termRepository);
    }

    @Bean
    DBService<Course> courseDBService() {
        return new DBService<>(courseRepository);
    }

}
