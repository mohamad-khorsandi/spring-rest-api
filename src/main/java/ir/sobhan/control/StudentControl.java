package ir.sobhan.control;

import ir.sobhan.dao.UserRepository;
import ir.sobhan.exception.UserNotFoundException;
import ir.sobhan.model.entity.User;
import ir.sobhan.model.input.StudentInputDTO;
import ir.sobhan.model.output.OutPutDTO;
import ir.sobhan.model.output.StudentINFOutputDTO;
import ir.sobhan.model.output.StudentOutputDTO;
import ir.sobhan.view.assemblers.StudentAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("students")
@RequiredArgsConstructor
@RestController
public class StudentControl {
    final private UserRepository userRepository;
    final private StudentAssembler studentAssembler;

//    @GetMapping
//    ResponseEntity<?> getAll(){
//        List<EntityModel<User>> list = userRepository.findAll().stream().filter(User::isStudent).map(studentAssembler::toModel).collect(Collectors.toList());
//        CollectionModel<?> collectionModel = CollectionModel.of(list);
//        return ResponseEntity.ok(collectionModel);
//
//    }

    @GetMapping({"{id}"})
    ResponseEntity<?> getOne(@PathVariable Long id) throws Exception {
        User user = userRepository.findById(id).filter(User::isStudent).orElseThrow(() -> new UserNotFoundException(id));
        EntityModel<StudentOutputDTO> userEntityModel = studentAssembler.toModel(new StudentOutputDTO(user));
        return ResponseEntity.ok(userEntityModel);
    }

    @PostMapping
    ResponseEntity<?> add(@RequestBody StudentInputDTO inputUser) throws Exception{
        User user = new User();
        inputUser.toRealObj(user);

        userRepository.save(user);
        StudentOutputDTO studentOutputDTO = new StudentOutputDTO(user);
        EntityModel<?> userEntityModel = studentOutputDTO.toModel();
        return ResponseEntity.created(linkTo(methodOn(StudentControl.class).getOne(user.getId())).toUri()).body(userEntityModel);
    }
}

