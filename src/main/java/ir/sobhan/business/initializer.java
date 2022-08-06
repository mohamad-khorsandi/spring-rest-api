package ir.sobhan.business;
import ir.sobhan.service.course.dao.CourseRepository;
import ir.sobhan.service.course.model.entity.Course;
import ir.sobhan.service.courseSection.dao.CourseSectionRepository;
import ir.sobhan.service.courseSection.model.entity.CourseSection;
import ir.sobhan.service.courseSection.model.entity.CourseSectionRegistration;
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

import java.util.Arrays;
import java.util.List;

import static ir.sobhan.service.user.model.entity.InstructorInf.Rank.ASSISTANT;

@Component
@RequiredArgsConstructor
@Slf4j
public class initializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final TermRepository termRepository;
    private final CourseRepository courseRepository;
    private final CourseSectionRepository sectionRepository;
    static Integer counter = 0;
    @Override
    public void run(String... args) {
        User stu1 = makeUser("stu1", "pass", true, false, false);
        User stu2 = makeUser("stu2", "pass", true, false, false);
        User stu3 = makeUser("stu3", "pass", true, false, false);

        User inst = makeUser("ins", "pass", false, false, true);
        makeUser("adm", "pass", false, true, false);

        Term term1 = Term.builder().title("99-00").open(true).build();
        Term term2 = Term.builder().title("98-99").open(false).build();
        termRepository.save(term1);
        termRepository.save(term2);

        Course course1 = Course.builder().title("data structure").build();
        Course course2 = Course.builder().title("programming").build();
        courseRepository.save(course1);
        courseRepository.save(course2);

        //todo: what about presist error
//        CourseSection section1 = CourseSection.builder().term(term1).build();
//        CourseSection section2 = CourseSection.builder().course(course2).term(term2).instructor(inst).build();
//        CourseSection section3 = CourseSection.builder().course(course2).term(term1).instructor(inst).build();
//        CourseSection section4 = CourseSection.builder().course(course1).term(term2).instructor(inst).build();

//        sectionRepository.save(section1);
//        sectionRepository.save(section2);
//        sectionRepository.save(section3);
//        sectionRepository.save(section4);
    }

    User makeUser(String username, String pass, boolean isStu, boolean isAd, boolean isInst){
        User.UserBuilder userBuilder = User.builder().username(username).password(pass).
                name(counter.toString()).phone(counter.toString()).nationalId(counter.toString());
        counter++;

        User user = null;

        if(isStu){
            StudentInf studentInf =StudentInf.builder().degree(StudentInf.Degree.BS).build();
            userBuilder.isStudent(true);
            user = userBuilder.build();
            user.setStudentInf(studentInf);
        }

        if(isInst){
            InstructorInf instructorInf = InstructorInf.builder().rank(ASSISTANT).build();
            userBuilder.isInstructor(true);
            user = userBuilder.build();
            user.setInstructorInf(instructorInf);
        }

        if(isAd){
            user = userBuilder.build();
            user.setAdmin(true);
            user.setActive(true);
        }

        userRepository.save(user);
        log.info("data base init : " + user);

        return user;
    }
}
