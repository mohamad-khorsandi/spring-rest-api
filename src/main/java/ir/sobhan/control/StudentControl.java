package ir.sobhan.control;

import ir.sobhan.dao.UserRepository;
import ir.sobhan.exception.UserNotFoundException;
import ir.sobhan.model.entity.User;
import ir.sobhan.model.input.StudentInputDTO;
import ir.sobhan.model.output.StudentOutputDTO;
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
    @GetMapping
    ResponseEntity<?> getAll(){
        List<?> list = userRepository.findAll().stream().filter(User::isStudent)
                .map((stu -> {
                    try {
                        return new StudentOutputDTO(stu).toModel();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })).collect(Collectors.toList());

        CollectionModel<?> collectionModel = CollectionModel.of(list);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping({"{id}"})
    ResponseEntity<?> read(@PathVariable Long id) throws Exception {
        User user = userRepository.findById(id).filter(User::isStudent).orElseThrow(() -> new UserNotFoundException(id));

        EntityModel<?> userEntityModel = new StudentOutputDTO(user).toModel();
        return ResponseEntity.ok(userEntityModel);
    }

    @PostMapping
    ResponseEntity<?> create(@RequestBody StudentInputDTO inputUser) throws Exception{
        User user = new User();
        inputUser.toRealObj(user);
        user.setStudent(true);
        userRepository.save(user);
        StudentOutputDTO studentOutputDTO = new StudentOutputDTO(user);
        EntityModel<?> userEntityModel =  studentOutputDTO.toModel();
        return ResponseEntity.created(linkTo(methodOn(StudentControl.class).read(user.getId())).toUri()).body(userEntityModel);
    }

//    @PutMapping
//    ResponseEntity<?> update(){
//
//    }
}

