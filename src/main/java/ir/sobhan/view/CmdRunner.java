package ir.sobhan.view;
import ir.sobhan.dao.UserRepository;
import ir.sobhan.model.entity.peopleEntities.StudentInf;
import ir.sobhan.model.entity.peopleEntities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CmdRunner implements CommandLineRunner {
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User user = User.builder().username("mmd").password("1234").isStudent(true).build();

        StudentInf studentInf =StudentInf.builder().studentId("myid")
                .degree(StudentInf.Degree.BS).build();

        user.setStudentInf(studentInf);
        userRepository.save(user);

        log.info("data base init : " + studentInf);
        log.info("data base init : " + user);

        User user2 = User.builder().username("mmd2").password("1234").isStudent(true).build();

        StudentInf studentInf2 = StudentInf.builder().studentId("myid")
                .degree(StudentInf.Degree.BS).build();

        user2.setStudentInf(studentInf2);
        userRepository.save(user2);

        log.info("data base init : " + studentInf2);
        log.info("data base init : " + user2);
    }
}
