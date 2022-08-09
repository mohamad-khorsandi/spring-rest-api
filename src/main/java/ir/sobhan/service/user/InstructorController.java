package ir.sobhan.service.user;

import ir.sobhan.business.DBService.InstructorDBService;
import ir.sobhan.service.AbstractService.LCRUD;
import ir.sobhan.service.user.model.entity.User;
import ir.sobhan.service.user.model.input.InstructorInputDTO;
import ir.sobhan.service.user.model.output.InstructorOutputDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("instructors")
public class InstructorController extends LCRUD<User, InstructorInputDTO> {
    public InstructorController(InstructorDBService dbService) {
        super(dbService, InstructorOutputDTO.class, (user -> {
            user.setInstructor(true);
            user.setActive(true);
        }));
    }
}