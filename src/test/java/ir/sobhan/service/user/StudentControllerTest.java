package ir.sobhan.service.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.sobhan.service.AbstractService.DBGetter;
import ir.sobhan.service.courseSection.model.entity.CourseSection;
import ir.sobhan.service.courseSection.model.entity.CourseSectionRegistration;
import ir.sobhan.service.term.model.entity.Term;
import ir.sobhan.service.user.model.entity.StudentInf;
import ir.sobhan.service.user.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

class StudentControllerTest {

    @Test
    void totalGradesTest() throws Exception {
        Term term10 = Term.builder().id(10L).title("ds").build();
        Term term11 = Term.builder().id(11L).title("pa").build();

        Set<CourseSectionRegistration> registrationSet = new HashSet<>();
        registrationSet.add(makeRegisteration(term10, 12.6));
        registrationSet.add(makeRegisteration(term10, 18));
        registrationSet.add(makeRegisteration(term11, 11));
        registrationSet.add(makeRegisteration(term11, 9));

        StudentInf studentInf = Mockito.mock(StudentInf.class);
        Mockito.when(studentInf.getRegistrationSet()).thenReturn(registrationSet);

        User stu = Mockito.mock(User.class);
        Mockito.when(stu.getStudentInf()).thenReturn(studentInf);

        DBGetter get = Mockito.mock(DBGetter.class);
        Mockito.when(get.studentByUsername(Mockito.any())).thenReturn(stu);

        Authentication authentication = Mockito.mock(Authentication.class);

        StudentController studentController = Mockito.mock(StudentController.class);
        studentController.get = get;
        Mockito.doCallRealMethod().when(studentController).totalGrades(authentication);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();

        mockMvc.perform(get("/students/total-grades")
                .with(authentication(authentication))
        ).andDo(print());

//        ResponseEntity response = studentController.totalGrades(authentication);
//        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
//
//        String json = response.getBody().toString();
//        System.out.println(json);

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