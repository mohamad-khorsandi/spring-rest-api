package ir.sobhan.universityrestapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import ir.sobhan.service.AbstractService.DBGetter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)//todo aks
@ContextConfiguration
@WebAppConfiguration
class UniversityRestApiApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc MockMvc;
    private Integer sectionId;
    private final Integer studentId = 1;
    private final Integer insId = 7;
    static UsernamePasswordAuthenticationToken stuAuthentication;
    static UsernamePasswordAuthenticationToken insAuthentication;

    @BeforeAll
    public static void makeAuthentications(){
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INSTRUCTOR"));
        insAuthentication = new UsernamePasswordAuthenticationToken("ins", null, authorities);

        Collection<GrantedAuthority> authorities2 = new ArrayList<>();
        authorities2.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        stuAuthentication = new UsernamePasswordAuthenticationToken("stu1", null, authorities2);
    }

    @BeforeEach
    public void makeBeansOfApplication(){
        MockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    public void beansCreationTest() throws Exception {
        ServletContext servletContext = webApplicationContext.getServletContext();
        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(servletContext instanceof MockServletContext);
        Assertions.assertNotNull(webApplicationContext.getBean("DBGetter"));
        DBGetter get = (DBGetter)webApplicationContext.getBean("DBGetter");
        Assertions.assertEquals("stu1", get.studentById(1L).getUsername());
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
                .andExpect(jsonPath("$.instructor.id").value(insId))
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
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("\"12\"")
                )
                .andExpect(status().isOk());
    }

    public void viewGrades() throws Exception {
        MockMvc.perform(get("/students/{stuId}/grades", studentId)
                        .with(authentication(stuAuthentication))
                        .param("termId", String.valueOf(10))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.average").value(12))
                .andExpect(jsonPath("$.sections.content[0].score").value(12));
    }
}