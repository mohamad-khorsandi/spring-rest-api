package ir.sobhan.service.user;

import ir.sobhan.business.Pair;
import ir.sobhan.business.exception.NotFoundException;
import ir.sobhan.service.AbstractService.DBGetter;
import ir.sobhan.service.AbstractService.LCRUD;
import ir.sobhan.service.courseSection.model.entity.CourseSectionRegistration;
import ir.sobhan.service.courseSection.model.output.CourseSectionRegistrationOutputDTO;
import ir.sobhan.service.term.model.entity.Term;
import ir.sobhan.service.user.dao.UserRepository;
import ir.sobhan.service.user.model.entity.User;
import ir.sobhan.service.user.model.input.StudentInputDTO;
import ir.sobhan.service.user.model.output.StudentOutputDTO;
import ir.sobhan.service.user.model.output.TermOfStudentOutputDTO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("students")
@Slf4j
@Setter
public class StudentController extends LCRUD<User, StudentInputDTO> {
    public StudentController(UserRepository repository, DBGetter get) {
        super(repository, StudentOutputDTO.class, User::isStudent, (user -> user.setStudent(true)));
        this.get = get;
    }
    DBGetter get;

    @GetMapping({"{stuId}/grades"})
    ResponseEntity<?> showGrades(@PathVariable Long stuId, @RequestParam(name = "termId") Long termId) throws NotFoundException {
        User student = get.studentById(stuId);

        Map<String, Object> outputMap = new HashMap<>();

        List<CourseSectionRegistrationOutputDTO> registrationsOfTerm = student.getStudentInf().getRegistrationSet().stream()
                .filter(registration -> registration.getSection().getTerm().getId().equals(termId))
                .map(registration -> {
                    try {
                        return new CourseSectionRegistrationOutputDTO(registration);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        double sum = 0;
        for (CourseSectionRegistrationOutputDTO registration : registrationsOfTerm){
            sum += registration.getScore();
        }
        double total = registrationsOfTerm.size();
        double ave = sum / total;

        CollectionModel<CourseSectionRegistrationOutputDTO> collectionModel = CollectionModel.of(registrationsOfTerm);

        outputMap.put("average", ave);
        outputMap.put("sections", collectionModel);

        return ResponseEntity.ok(outputMap);
    }

    @GetMapping({"total-grades"})
    ResponseEntity<?> totalGrades(Authentication authentication) throws NotFoundException {
        User stu = get.studentByUsername(authentication.getName());

        Map<Term, Pair<Double, Integer>> aveMap = new HashMap<>();
        stu.getStudentInf().getRegistrationSet()
                .forEach(registration -> {
                        Term term = registration.getSection().getTerm();
                        Pair<Double, Integer> lastPair;
                        if (aveMap.get(term) != null) {
                            lastPair = aveMap.get(term);
                            aveMap.put(term, new Pair<>(lastPair.getKey() + registration.getScore(), lastPair.getVal() + 1));
                        }else {
                            aveMap.put(term, new Pair<>(registration.getScore(), 1));
                        }
                });

        List<TermOfStudentOutputDTO> termList = new ArrayList<>();

        aveMap.forEach((term, pair) -> {
            try {
                termList.add(new TermOfStudentOutputDTO(term, pair.getKey(), pair.getVal()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        CollectionModel<TermOfStudentOutputDTO> collectionModel = CollectionModel.of(termList);

        return ResponseEntity.ok(collectionModel);
    }
}