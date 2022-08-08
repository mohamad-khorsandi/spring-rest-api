package ir.sobhan.service.courseSection;

import ir.sobhan.business.exception.AccessDeniedException;
import ir.sobhan.business.exception.NotFoundException;
import ir.sobhan.business.exception.SectionIsNotEmptyException;
import ir.sobhan.service.AbstractService.DBGetter;
import ir.sobhan.service.course.model.entity.Course;
import ir.sobhan.service.courseSection.dao.CourseSectionRepository;
import ir.sobhan.service.courseSection.dao.RegistrationRepository;
import ir.sobhan.service.courseSection.model.entity.CourseSection;
import ir.sobhan.service.courseSection.model.entity.CourseSectionRegistration;
import ir.sobhan.service.courseSection.model.input.CourseSectionInputDTO;
import ir.sobhan.service.courseSection.model.output.CourseSectionOutputDTO;
import ir.sobhan.service.courseSection.model.output.StudentOfSectionOutputDTO;
import ir.sobhan.service.term.model.entity.Term;
import ir.sobhan.service.user.dao.UserRepository;
import ir.sobhan.service.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("course-sections")
@RequiredArgsConstructor
public class CourseSectionController {
    final private CourseSectionRepository repository;
    final private RegistrationRepository registrationRepository;
    final private UserRepository userRepository;
    final private DBGetter get;

    //LCRUD ------------------------------------------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<?> list(@RequestParam Long termId) throws NotFoundException {
        get.termById(termId);

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

    @GetMapping({"{sectionId}"})
    ResponseEntity<?> read(@PathVariable Long sectionId) throws Exception {
        CourseSection section = get.sectionById(sectionId);

        EntityModel<?> userEntityModel = toOutDTOModel(section);
        return ResponseEntity.ok(userEntityModel);
    }

    @PostMapping
    ResponseEntity<?> create(@RequestParam Long termId,@RequestParam Long courseId, Authentication authentication) throws Exception{
        User instructor = get.instructorByUsername(authentication.getName());

        CourseSection section = new CourseSection();

        Term term = get.termById(termId);
        Course course = get.courseById(courseId);

        section.setInstructor(instructor);
        section.setCourse(course);
        section.setTerm(term);

        repository.save(section);

        EntityModel<?> entityModel = toOutDTOModel(section);
        return ResponseEntity.created(linkTo(methodOn(CourseSectionController.class).read(section.getId())).toUri()).body(entityModel);
    }

    @PutMapping("{sectionId}")
    ResponseEntity<?> update(@PathVariable Long sectionId, @RequestParam(required = false) Long termId, @RequestParam(required = false) Long courseId, @RequestParam(required = false) Long instructorId, Authentication authentication) throws Exception {
        CourseSection section = get.sectionById(sectionId);
        User user = get.userByUsername(authentication.getName());

        if (!user.isAdmin() && !user.getUsername().equals(section.getInstructor().getUsername()))
            throw new AccessDeniedException("only instructor of this course can do this operation");

        if (termId != null){
            Term term = get.termById(termId);
            section.setTerm(term);
        }

        if (courseId != null){
            Course course = get.courseById(courseId);
            section.setCourse(course);
        }

        if(instructorId != null){
            User instructor = get.getInstructorById(instructorId);
            section.setInstructor(instructor);
        }

        repository.save(section);
        EntityModel<?> entityModel = toOutDTOModel(section);
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("{sectionId}")
    ResponseEntity<?> delete(@PathVariable Long sectionId, Authentication authentication) throws NotFoundException, AccessDeniedException, SectionIsNotEmptyException {
        CourseSection section = get.sectionById(sectionId);
        User user = get.userByUsername(authentication.getName());

        if (!user.isAdmin() && !user.getUsername().equals(section.getInstructor().getUsername()))
            throw new AccessDeniedException("only instructor of this course can do this operation");

        if (!section.getRegistrationList().isEmpty())
            throw new SectionIsNotEmptyException();

        repository.deleteById(sectionId);
        return ResponseEntity.ok().build();
    }

    protected EntityModel<CourseSectionOutputDTO> toOutDTOModel(CourseSection section) throws Exception {
        CourseSectionOutputDTO outPutDTO = new CourseSectionOutputDTO(section);
        return EntityModel.of(outPutDTO);
    }
    //CRUD END ------------------------------------------------------------------------------------------------------


    @GetMapping("{sectionId}/students")
    public ResponseEntity<?> listStudents(@PathVariable Long sectionId) throws NotFoundException {
        CourseSection section = get.sectionById(sectionId);

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

    @PutMapping({"{sectionId}/register"})
    ResponseEntity<?> register(@PathVariable Long sectionId, Authentication authentication) throws Exception {
        CourseSection section = get.sectionById(sectionId);
        User stu = get.studentByUsername(authentication.getName());

        CourseSectionRegistration registration = CourseSectionRegistration.builder().section(section).student(stu).build();

        section.getRegistrationList().add(registration);
        stu.getStudentInf().getRegistrationSet().add(registration);
        repository.save(section);
        userRepository.save(stu);

        EntityModel<?> entityModel = toOutDTOModel(section);
        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("{sectionId}/{studentId}/setGrade")
    ResponseEntity<?> setGrade(@PathVariable Long sectionId, @PathVariable Long studentId, @RequestBody double grade) throws NotFoundException {
        CourseSection section = get.sectionById(sectionId);

        CourseSectionRegistration registration = section.getRegistrationList().stream()
                .filter(registration1 -> registration1.getStudent().getId().equals(studentId))
                .findFirst().orElseThrow(() -> new NotFoundException("student", studentId));

        registration.setScore(grade);

        registrationRepository.save(registration);
        return ResponseEntity.ok().build();
    }
}
