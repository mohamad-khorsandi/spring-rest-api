package ir.sobhan.service.user;

import ir.sobhan.business.DBService.RegistrationDBService;
import ir.sobhan.business.DBService.StudentDBService;
import ir.sobhan.business.Utils.Pair;
import ir.sobhan.business.exception.NotFoundException;
import ir.sobhan.service.AbstractService.LCRUD;
import ir.sobhan.service.courseSection.model.entity.CourseSectionRegistration;
import ir.sobhan.service.courseSection.model.output.CourseSectionRegistrationOutputDTO;
import ir.sobhan.service.term.model.entity.Term;
import ir.sobhan.service.user.model.entity.User;
import ir.sobhan.service.user.model.input.StudentInputDTO;
import ir.sobhan.service.user.model.output.StudentOutputDTO;
import ir.sobhan.service.user.model.output.TermOfStudentOutputDTO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
    public StudentController(StudentDBService dbService, StudentDBService db, RegistrationDBService registrationDBService) {
        super(dbService, StudentOutputDTO.class, (user -> {
            user.setStudent(true);
            user.setActive(true);
        }));
        this.db = db;
        this.registrationDBService = registrationDBService;
    }

    StudentDBService db;
    final RegistrationDBService registrationDBService;

    @GetMapping({"{studentId}/grades"})
    ResponseEntity<?> showGrades(@PathVariable Long studentId, @RequestParam(name = "termId") Long termId) {
        Map<String, Object> outputMap = new HashMap<>();

        List<CourseSectionRegistration> registrationList = registrationDBService.findAllBySection_Term_IdAndStudent_Id(termId, studentId);

        List<CourseSectionRegistrationOutputDTO> registrationDTOList = registrationList.stream().map(CourseSectionRegistrationOutputDTO::new).collect(Collectors.toList());

        double sum = 0;
        for (CourseSectionRegistrationOutputDTO registration : registrationDTOList)
            sum += registration.getScore();

        double total = registrationDTOList.size();
        double ave = sum / total;

        CollectionModel<CourseSectionRegistrationOutputDTO> collectionModel = CollectionModel.of(registrationDTOList);

        outputMap.put("average", ave);
        outputMap.put("sections", collectionModel);

        return ResponseEntity.ok(outputMap);
    }

    @GetMapping({"total-grades"})
    ResponseEntity<?> totalGrades(Authentication authentication) throws NotFoundException {
        User stu = db.getByUsername(authentication.getName());

        Map<Term, Pair<Double, Integer>> aveMap = fillAveMap(stu);

        List<TermOfStudentOutputDTO> termList = new ArrayList<>();

        aveMap.forEach((term, pair) -> termList.add(new TermOfStudentOutputDTO(term, pair.getKey(), pair.getVal())));

        CollectionModel<TermOfStudentOutputDTO> collectionModel = CollectionModel.of(termList);

        return ResponseEntity.ok(collectionModel);
    }

    Map<Term, Pair<Double, Integer>> fillAveMap(User stu) {
        Map<Term, Pair<Double, Integer>> aveMap = new HashMap<>();
        stu.getStudentInf().getRegistrationSet()
                .forEach(registration -> {
                    Term term = registration.getSection().getTerm();
                    Pair<Double, Integer> lastPair;
                    if (aveMap.get(term) != null) {
                        lastPair = aveMap.get(term);
                        aveMap.put(term, new Pair<>(lastPair.getKey() + registration.getScore(), lastPair.getVal() + 1));
                    } else {
                        aveMap.put(term, new Pair<>(registration.getScore(), 1));
                    }
                });
        return aveMap;
    }
}