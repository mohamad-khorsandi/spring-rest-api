package ir.sobhan.universityrestapi;

import ir.sobhan.business.DBService.DBService;
import ir.sobhan.business.DBService.UserDBService;
import ir.sobhan.business.exception.BadInputException;
import ir.sobhan.service.course.model.entity.Course;
import ir.sobhan.service.term.model.entity.Term;
import ir.sobhan.service.user.model.entity.InstructorInf;
import ir.sobhan.service.user.model.entity.StudentInf;
import ir.sobhan.service.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static ir.sobhan.service.user.model.entity.InstructorInf.Rank.ASSISTANT;

@Component
@RequiredArgsConstructor
@Slf4j
public class Initializer {
    private final UserDBService userDB;
    private final DBService<Term> termDBService;
    private final DBService<Course> courseDBService;
    static Integer counter = 0;

    @PostConstruct
    public void run() throws BadInputException {
        makeUser("stu1", "pass", true, false);
        makeUser("stu2", "pass", true, false);
        makeUser("stu3", "pass", true, false);

        makeUser("ins", "pass", false, true);

        Term term1 = Term.builder().title("99-00").open(true).id(10L).build();
        Term term2 = Term.builder().title("98-99").open(false).id(11L).build();
        termDBService.save(term1);
        termDBService.save(term2);

        Course course1 = Course.builder().title("data structure").id(12L).build();
        Course course2 = Course.builder().title("programming").id(13L).build();
        courseDBService.save(course1);
        courseDBService.save(course2);
    }

    public void makeUser(String username, String pass, boolean isStu, boolean isInst) throws BadInputException {
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

        userDB.save(user);
        log.info("data base init : " + user);
    }
}
