package ir.sobhan.business;
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
public class initializer implements CommandLineRunner {
    private final UserRepository userRepository;
    static Integer counter = 0;
    @Override
    public void run(String... args) {
        makeUser("stu1", "pass", true, false, false);
        makeUser("stu2", "pass", true, false, false);
        makeUser("stu3", "pass", true, false, false);

        makeUser("ins", "pass", false, false, true);
        makeUser("adm", "pass", false, true, false);
    }

    void makeUser(String username, String pass, boolean isStu, boolean isAd, boolean isInst){
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

    }
}
