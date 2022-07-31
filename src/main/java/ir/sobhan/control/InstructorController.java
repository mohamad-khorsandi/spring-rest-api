package ir.sobhan.control;

import ir.sobhan.dao.UserRepository;
import ir.sobhan.model.entity.peopleEntities.AppUser;
import ir.sobhan.model.input.peopleEntities.InstructorInputDTO;
import ir.sobhan.model.output.peopleEntities.InstructorOutputDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Instructors")
public class InstructorController extends Controller<AppUser, InstructorInputDTO>{
    public InstructorController(UserRepository repository) {
        super(repository, InstructorOutputDTO.class, AppUser::isInstructor, (user -> user.setInstructor(true)));
    }
}