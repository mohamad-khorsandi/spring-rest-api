package ir.sobhan.service.user;

import ir.sobhan.business.exception.BadInputException;
import ir.sobhan.service.AbstractService.LCRUD;
import ir.sobhan.service.AbstractService.model.output.OutPutDTO;
import ir.sobhan.service.user.dao.UserRepository;
import ir.sobhan.service.user.model.entity.User;
import ir.sobhan.service.user.model.input.InstructorInputDTO;
import ir.sobhan.service.user.model.input.StudentInputDTO;
import ir.sobhan.service.user.model.output.InstructorOutputDTO;
import ir.sobhan.service.user.model.output.StudentOutputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("signin")
@RequiredArgsConstructor
public class UserSignInController {

    private final UserRepository repository;

    @PostMapping("/student")
    ResponseEntity<?> create(@RequestBody StudentInputDTO studentInputDTO) throws Exception{
        User stu = studentInputDTO.toRealObj(null);

        stu.setStudent(true);

        try {
            repository.save(stu);
        } catch (RuntimeException e){
            throw new BadInputException();
        }

        EntityModel<?> entityModel = new StudentOutputDTO(stu).toModel();

        return ResponseEntity.status(201).body(entityModel);
    }

    @PostMapping("/instructor")
    ResponseEntity<?> create(@RequestBody InstructorInputDTO instructorInputDTO) throws Exception{
        User ins = instructorInputDTO.toRealObj(null);

        ins.setInstructor(true);

        try {
            repository.save(ins);
        } catch (RuntimeException e){
            throw new BadInputException();
        }

        EntityModel<?> entityModel = new InstructorOutputDTO(ins).toModel();

        return ResponseEntity.status(201).body(entityModel);
    }
}
