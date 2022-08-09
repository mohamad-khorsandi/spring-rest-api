package ir.sobhan.service.courseSection;

import ir.sobhan.business.DBService.*;
import ir.sobhan.business.exception.AccessDeniedException;
import ir.sobhan.business.exception.BadInputException;
import ir.sobhan.business.exception.NotFoundException;
import ir.sobhan.business.exception.SectionIsNotEmptyException;
import ir.sobhan.service.course.model.entity.Course;
import ir.sobhan.service.courseSection.model.entity.CourseSection;
import ir.sobhan.service.courseSection.model.entity.CourseSectionRegistration;
import ir.sobhan.service.courseSection.model.output.CourseSectionOutputDTO;
import ir.sobhan.service.courseSection.model.output.StudentOfSectionOutputDTO;
import ir.sobhan.service.term.model.entity.Term;
import ir.sobhan.service.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("course-sections")
@RequiredArgsConstructor
public class CourseSectionController {
    private final DBService<Term> termDB;
    private final SectionDBService sectionDB;
    private final DBService<Course> courseDB;
    private final InstructorDBService instructorDB;
    private final UserDBService userDB;
    private final RegistrationDBService registrationDB;
    private final StudentDBService studentDB;

    //LCRUD ------------------------------------------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<?> list(@RequestParam Long termId, @PathParam("page-number") Integer pageNumber,
                                  @PathParam("page-size") Integer pageSize) throws NotFoundException {
        termDB.throwIfNotExists(termId);

        List<CourseSection> sectionList = sectionDB.findAllByTerm_id(pageNumber, pageSize, termId);

        List<?> modelList = sectionList.stream().map(CourseSectionOutputDTO::new).collect(Collectors.toList());

        CollectionModel<?> collectionModel = CollectionModel.of(modelList);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping({"{sectionId}"})
    ResponseEntity<?> read(@PathVariable Long sectionId) throws Exception {
        CourseSection section = sectionDB.getById(sectionId);

        EntityModel<?> userEntityModel = toOutDTOModel(section);
        return ResponseEntity.ok(userEntityModel);
    }

    @PostMapping
    ResponseEntity<?> create(@RequestParam Long termId, @RequestParam Long courseId, Authentication authentication) throws Exception {
        User instructor = instructorDB.getByUsername(authentication.getName());

        CourseSection section = new CourseSection();

        Term term = termDB.getById(termId);
        Course course = courseDB.getById(courseId);

        section.setInstructor(instructor);
        section.setCourse(course);
        section.setTerm(term);

        sectionDB.save(section);

        EntityModel<?> entityModel = toOutDTOModel(section);
        return ResponseEntity.created(linkTo(methodOn(CourseSectionController.class).read(section.getId())).toUri()).body(entityModel);
    }

    @PutMapping("{sectionId}")
    ResponseEntity<?> update(@PathVariable Long sectionId, @RequestParam(required = false) Long termId, @RequestParam(required = false) Long courseId, @RequestParam(required = false) Long instructorId, Authentication authentication) throws Exception {

        CourseSection section = sectionDB.getById(sectionId);
        User user = userDB.getByUsername(authentication.getName());

        if (!user.isAdmin() && !user.getUsername().equals(section.getInstructor().getUsername()))
            throw new AccessDeniedException("only instructor of this course can do this operation");

        if (termId != null) {
            Term term = termDB.getById(termId);
            section.setTerm(term);
        }

        if (courseId != null) {
            Course course = courseDB.getById(courseId);
            section.setCourse(course);
        }

        if (instructorId != null) {
            User instructor = instructorDB.getById(instructorId);
            section.setInstructor(instructor);
        }

        sectionDB.save(section);
        EntityModel<?> entityModel = toOutDTOModel(section);
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("{sectionId}")
    ResponseEntity<?> delete(@PathVariable Long sectionId, Authentication authentication) throws AccessDeniedException, SectionIsNotEmptyException, NotFoundException {
        CourseSection section = sectionDB.getById(sectionId);
        User user = userDB.getByUsername(authentication.getName());

        if (!user.isAdmin() && !user.getUsername().equals(section.getInstructor().getUsername()))
            throw new AccessDeniedException("only instructor of this course can do this operation");

        if (!section.getRegistrationList().isEmpty())
            throw new SectionIsNotEmptyException();

        sectionDB.deleteById(sectionId);
        return ResponseEntity.ok().build();
    }

    protected EntityModel<CourseSectionOutputDTO> toOutDTOModel(CourseSection section) {
        CourseSectionOutputDTO outPutDTO = new CourseSectionOutputDTO(section);
        return EntityModel.of(outPutDTO);
    }
    //CRUD END ------------------------------------------------------------------------------------------------------

    @GetMapping("{sectionId}/students")
    public ResponseEntity<?> listStudents(@PathVariable Long sectionId) throws NotFoundException {
        CourseSection section = sectionDB.getById(sectionId);

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
        CourseSection section = sectionDB.getById(sectionId);
        User stu = studentDB.getByUsername(authentication.getName());

        CourseSectionRegistration registration = CourseSectionRegistration.builder().section(section).student(stu).build();

        section.getRegistrationList().add(registration);
        stu.getStudentInf().getRegistrationSet().add(registration);
        registrationDB.save(registration);

        EntityModel<?> entityModel = toOutDTOModel(section);
        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("{sectionId}/{studentId}/setGrade")
    ResponseEntity<?> setGrade(@PathVariable Long sectionId, @PathVariable Long studentId, @PathParam("grade") double grade) throws NotFoundException, BadInputException {
        CourseSectionRegistration registration = registrationDB.findBySection_IdAndStudent_Id(sectionId, studentId);

        registration.setScore(grade);

        registrationDB.save(registration);
        return ResponseEntity.ok().build();
    }
}