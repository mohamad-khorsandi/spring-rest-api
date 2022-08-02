package ir.sobhan.service.courseSection;

import ir.sobhan.business.exception.EntityNotFoundException;
import ir.sobhan.service.AbstractService.CRUD;
import ir.sobhan.service.courseSection.model.entity.CourseSection;
import ir.sobhan.service.courseSection.model.entity.CourseSectionRegistration;
import ir.sobhan.service.courseSection.model.input.CourseSectionInputDTO;
import ir.sobhan.service.courseSection.model.output.CourseSectionOutputDTO;
import ir.sobhan.service.courseSection.model.output.StudentOfSectionOutputDTO;
import ir.sobhan.service.user.dao.UserRepository;
import ir.sobhan.service.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("course-sections")
public class CourseSectionController extends CRUD<CourseSection, CourseSectionInputDTO> {
    public CourseSectionController(JpaRepository<CourseSection, Long> repository, UserRepository userRepository) {
        super(repository, CourseSectionOutputDTO.class);
        this.userRepository = userRepository;
    }
    final private UserRepository userRepository;
    @GetMapping
    public ResponseEntity<?> list(@RequestParam Long termId){
        List<?> list = repository.findAll().stream()
                .filter((courseSection) -> courseSection.getTerm().getId().equals(termId))
                .map((section -> {
                    try {
                        return new CourseSectionOutputDTO(section);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })).collect(Collectors.toList());

        CollectionModel<?> collectionModel = CollectionModel.of(list);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("{id}/students")
    public ResponseEntity<?> listStudents(@PathVariable Long id) throws EntityNotFoundException {
        CourseSection section = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));

        List<?> list = section.getRegistrationList().stream()
                .map((registration -> {
                    User student = registration.getStudent();
                    Double score = registration.getScore();
                    try {
                        return new StudentOfSectionOutputDTO(student, score);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })).collect(Collectors.toList());

        CollectionModel<?> collectionModel = CollectionModel.of(list);
        return ResponseEntity.ok(collectionModel);
    }

    @PutMapping({"{id}/register"})
    ResponseEntity<?> register(@PathVariable Long id, Authentication authentication) throws Exception {
        CourseSection section = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(()->new EntityNotFoundException(authentication.getName()));

        CourseSectionRegistration registration = CourseSectionRegistration.builder().section(section).student(user).build();

        section.getRegistrationList().add(registration);

        repository.save(section);

        EntityModel<?> entityModel = toOutDTOModel(section);
        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("{id}/{studentId}/setGrade")
    void setGrade(@PathVariable Long id, @PathVariable Long studentId, @RequestBody double grade) throws EntityNotFoundException {
        CourseSection section = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));

        CourseSectionRegistration registration = section.getRegistrationList().stream()
                .filter(registration1 -> registration1.getStudent().getId().equals(studentId))
                .findFirst().orElseThrow(() -> new EntityNotFoundException(studentId));

        registration.setScore(grade);
    }
}
