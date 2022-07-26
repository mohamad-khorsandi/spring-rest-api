package ir.sobhan.control;

import ir.sobhan.dao.UserRepository;
import ir.sobhan.model.entity.peopleEntities.User;
import ir.sobhan.model.input.peopleEntities.StudentInputDTO;
import ir.sobhan.model.output.peopleEntities.StudentOutputDTO;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("students")
public class StudentControl extends Controller<User, StudentInputDTO>{
    public StudentControl(UserRepository repository) {
        super(repository, StudentOutputDTO.class, User::isStudent, (user -> user.setStudent(true)));
    }
}

