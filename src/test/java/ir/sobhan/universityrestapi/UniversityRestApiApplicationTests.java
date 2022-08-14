package ir.sobhan.universityrestapi;

import com.jayway.jsonpath.JsonPath;
import ir.sobhan.business.DBService.UserDBService;
import ir.sobhan.business.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@RequiredArgsConstructor
@Slf4j
class UniversityRestApiApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc MockMvc;
    private Integer sectionId;
    private Integer studentId;
    private Integer instructorId;

    static UsernamePasswordAuthenticationToken stuAuthentication;
    static UsernamePasswordAuthenticationToken insAuthentication;

    @BeforeAll
    public static void makeAuthentications() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INSTRUCTOR"));
        insAuthentication = new UsernamePasswordAuthenticationToken("ins", null, authorities);

        Collection<GrantedAuthority> authorities2 = new ArrayList<>();
        authorities2.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        stuAuthentication = new UsernamePasswordAuthenticationToken("stu1", null, authorities2);
    }


    @BeforeEach
    public void makeBeansOfApplication() throws NotFoundException {
        MockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

        ServletContext servletContext = webApplicationContext.getServletContext();

        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(servletContext instanceof MockServletContext);
        Assertions.assertNotNull(webApplicationContext.getBean("userDBService"));

        UserDBService userDB = (UserDBService) webApplicationContext.getBean("userDBService");
        studentId = Math.toIntExact(userDB.getByUsername("stu1").getId());
        instructorId = Math.toIntExact(userDB.getByUsername("ins").getId());
    }

    @Test
    public void integrationTest() throws Exception {
        postSection();
        registerToSection();
        setGrades();
        viewGrades();
    }

    public void postSection() throws Exception {
        String response = MockMvc.perform(post("/course-sections")
                        .with(authentication(insAuthentication))
                        .param("termId", String.valueOf(10))
                        .param("courseId", String.valueOf(13))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.term.id").value(10))
                .andExpect(jsonPath("$.course.id").value(13))
                .andExpect(jsonPath("$.instructor.id").value(instructorId))
                .andReturn().getResponse().getContentAsString();

        this.sectionId = JsonPath.read(response, "$.id");
    }

    public void registerToSection() throws Exception {
        MockMvc.perform(put("/course-sections/{sectionId}/register", sectionId)
                        .with(authentication(stuAuthentication))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sectionId))
                .andExpect(jsonPath("$.studentCount").value(1));
    }

    public void setGrades() throws Exception {
        MockMvc.perform(put("/course-sections/{sectionId}/{studentId}/setGrade", sectionId, studentId)
                        .with(authentication(insAuthentication))
                        .param("grade", String.valueOf(12))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    public void viewGrades() throws Exception {
        MockMvc.perform(get("/students/{studentId}/grades", studentId)
                        .with(authentication(stuAuthentication))
                        .param("termId", String.valueOf(10)))
                .andDo(print())
                .andExpect(jsonPath("$.average").value(12.0))
                .andExpect(jsonPath("$.sections.content[0].score").value(12.0));
    }
}