package ir.sobhan.view;

import ir.sobhan.dao.StudentInfRepository;
import ir.sobhan.dao.UserRepository;
import ir.sobhan.model.entity.StudentInf;
import ir.sobhan.model.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CmdRunner implements CommandLineRunner {
    private final UserRepository userRepository;
    private final StudentInfRepository studentInfRepository;


    @Override
    public void run(String... args) throws Exception {
        User user = User.builder().username("mmd").password("1234").build();

        StudentInf studentInf =StudentInf.builder().studentId("myid")
                .degree(StudentInf.Degree.BS).build();

        user.setStudentInf(studentInf);
        userRepository.save(user);

        log.info("data base init : " + studentInf);
        log.info("data base init : " + user);
    }
}
