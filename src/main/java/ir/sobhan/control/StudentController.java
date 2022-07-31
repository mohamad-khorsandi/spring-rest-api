package ir.sobhan.control;

import ir.sobhan.dao.UserRepository;
import ir.sobhan.model.entity.peopleEntities.AppUser;
import ir.sobhan.model.input.peopleEntities.StudentInputDTO;
import ir.sobhan.model.output.peopleEntities.StudentOutputDTO;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("students")
public class StudentController extends Controller<AppUser, StudentInputDTO>{
    public StudentController(UserRepository repository) {
        super(repository, StudentOutputDTO.class, AppUser::isStudent, (user -> user.setStudent(true)));
    }
}

