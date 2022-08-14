package ir.sobhan.conf;

import ir.sobhan.business.DBService.DBService;
import ir.sobhan.service.course.dao.CourseRepository;
import ir.sobhan.service.course.model.entity.Course;
import ir.sobhan.service.term.dao.TermRepository;
import ir.sobhan.service.term.model.entity.Term;
import ir.sobhan.service.user.dao.UserRepository;
import ir.sobhan.service.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@org.springframework.context.annotation.Configuration
@RequiredArgsConstructor
public class Configuration {
    final private TermRepository termRepository;
    final private CourseRepository courseRepository;
    final private UserRepository userRepository;

    @PostConstruct
    void MakeAdmin() {
        User admin = User.builder().username("adm").password("pass").name("mmd").phone("0913").nationalId("127").build();

        admin.setAdmin(true);
        admin.setActive(true);
        userRepository.save(admin);
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
