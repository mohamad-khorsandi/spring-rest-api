package ir.sobhan.service.user;

import ir.sobhan.service.AbstractService.DBGetter;
import ir.sobhan.service.courseSection.model.entity.CourseSection;
import ir.sobhan.service.courseSection.model.entity.CourseSectionRegistration;
import ir.sobhan.service.term.model.entity.Term;
import ir.sobhan.service.user.dao.UserRepository;
import ir.sobhan.service.user.model.entity.StudentInf;
import ir.sobhan.service.user.model.entity.User;
import ir.sobhan.service.user.model.output.TermOfStudentOutputDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;

@SpringBootTest
@ContextConfiguration(classes = {StudentController.class})
class StudentControllerTest {
    @MockBean
    StudentInf studentInf;

    @MockBean
    DBGetter get;

    @MockBean
    User stu;

    @MockBean
    UserRepository repository;

    @MockBean
    Authentication authentication;

    @Autowired
    StudentController studentController;

    @Test
    void totalGradesTest() throws Exception {
        Term term10 = Term.builder().id(10L).title("ds").build();
        Term term11 = Term.builder().id(11L).title("pa").build();

        Set<CourseSectionRegistration> registrationSet = new HashSet<>();
        registrationSet.add(makeRegisteration(term10, 12.6));
        registrationSet.add(makeRegisteration(term10, 18));
        registrationSet.add(makeRegisteration(term11, 11));
        registrationSet.add(makeRegisteration(term11, 9));

        Mockito.when(studentInf.getRegistrationSet()).thenReturn(registrationSet);

        Mockito.when(stu.getStudentInf()).thenReturn(studentInf);

        Mockito.when(get.studentByUsername(Mockito.any())).thenReturn(stu);

        studentController.get = get;

        ResponseEntity<CollectionModel<TermOfStudentOutputDTO>> response = (ResponseEntity<CollectionModel<TermOfStudentOutputDTO>>) studentController.totalGrades(authentication);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        Collection<TermOfStudentOutputDTO> content = Objects.requireNonNull(response.getBody()).getContent();

        Assertions.assertEquals(2, content.size());

        content.forEach(term -> {
            if (term.getId().equals(term10.getId()))
                Assertions.assertEquals(15.3, term.getAve());
            else
                Assertions.assertEquals(10.0, term.getAve());
        });
    }

    CourseSectionRegistration makeRegisteration(Term term, double grade){
        CourseSection section = Mockito.mock(CourseSection.class);
        Mockito.when(section.getTerm()).thenReturn(term);

        CourseSectionRegistration registration = Mockito.mock(CourseSectionRegistration.class);
        Mockito.when(registration.getScore()).thenReturn(grade);
        Mockito.when(registration.getSection()).thenReturn(section);
        return registration;
    }
}