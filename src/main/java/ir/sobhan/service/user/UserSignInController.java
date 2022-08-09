package ir.sobhan.service.user;

import ir.sobhan.business.DBService.InstructorDBService;
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

    final private InstructorDBService db;

    @PostMapping("/student")
    ResponseEntity<?> create(@RequestBody StudentInputDTO studentInputDTO) throws Exception {
        User stu = studentInputDTO.toRealObj(null);

        stu.setStudent(true);

        db.save(stu);

        EntityModel<?> entityModel = new StudentOutputDTO(stu).toModel();

        return ResponseEntity.status(201).body(entityModel);
    }

    @PostMapping("/instructor")
    ResponseEntity<?> create(@RequestBody InstructorInputDTO instructorInputDTO) throws Exception {
        User ins = instructorInputDTO.toRealObj(null);

        ins.setInstructor(true);

        db.save(ins);

        EntityModel<?> entityModel = new InstructorOutputDTO(ins).toModel();

        return ResponseEntity.status(201).body(entityModel);
    }
}
