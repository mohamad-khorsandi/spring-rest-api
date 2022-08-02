package ir.sobhan.service.user;

import ir.sobhan.service.AbstractService.LCRUD;
import ir.sobhan.service.courseSection.model.output.CourseSectionOutputDTO;
import ir.sobhan.service.user.dao.UserRepository;
import ir.sobhan.service.user.model.entity.User;
import ir.sobhan.service.user.model.input.StudentInputDTO;
import ir.sobhan.service.user.model.output.StudentOutputDTO;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("students")
public class StudentController extends LCRUD<User, StudentInputDTO> {
    public StudentController(UserRepository repository) {
        super(repository, StudentOutputDTO.class, User::isStudent, (user -> user.setStudent(true)));
    }
}

