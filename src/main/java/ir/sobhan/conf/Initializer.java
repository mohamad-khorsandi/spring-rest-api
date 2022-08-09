package ir.sobhan.conf;

import ir.sobhan.service.course.dao.CourseRepository;
import ir.sobhan.service.course.model.entity.Course;
import ir.sobhan.service.term.dao.TermRepository;
import ir.sobhan.service.term.model.entity.Term;
import ir.sobhan.service.user.dao.UserRepository;
import ir.sobhan.service.user.model.entity.InstructorInf;
import ir.sobhan.service.user.model.entity.StudentInf;
import ir.sobhan.service.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static ir.sobhan.service.user.model.entity.InstructorInf.Rank.ASSISTANT;

@Component
@RequiredArgsConstructor
@Slf4j
public class Initializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final TermRepository termRepository;
    private final CourseRepository courseRepository;
    static Integer counter = 0;

    @Override
    public void run(String... args) {
        makeUser("stu1", "pass", true, false, false);
        makeUser("stu2", "pass", true, false, false);
        makeUser("stu3", "pass", true, false, false);

        makeUser("ins", "pass", false, false, true);

        makeUser("adm", "pass", false, true, false);

        Term term1 = Term.builder().title("99-00").open(true).id(10L).build();
        Term term2 = Term.builder().title("98-99").open(false).id(11L).build();
        termRepository.save(term1);
        termRepository.save(term2);

        Course course1 = Course.builder().title("data structure").id(12L).build();
        Course course2 = Course.builder().title("programming").id(13L).build();
        courseRepository.save(course1);
        courseRepository.save(course2);
        //todo: what about presist error
    }

    public void makeUser(String username, String pass, boolean isStu, boolean isAd, boolean isInst) {
        User.UserBuilder userBuilder = User.builder().username(username).password(pass).
                name(counter.toString()).phone(counter.toString()).nationalId(counter.toString());
        counter++;

        User user = null;

        if (isStu) {
            StudentInf studentInf = StudentInf.builder().degree(StudentInf.Degree.BS).build();
            userBuilder.student(true);
            user = userBuilder.build();
            user.setStudentInf(studentInf);
        }

        if (isInst) {
            InstructorInf instructorInf = InstructorInf.builder().rank(ASSISTANT).build();
            userBuilder.instructor(true);
            user = userBuilder.build();
            user.setInstructorInf(instructorInf);
        }

        if (isAd) {
            user = userBuilder.build();
            user.setAdmin(true);
            user.setActive(true);
        }

        userRepository.save(user);
        log.info("data base init : " + user);
    }
}
