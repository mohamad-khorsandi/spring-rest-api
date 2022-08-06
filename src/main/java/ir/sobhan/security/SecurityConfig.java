package ir.sobhan.security;

import lombok.RequiredArgsConstructor;

import static ir.sobhan.security.Role.*;
import static ir.sobhan.security.SecurityConfig.RoleSet.*;
import static org.springframework.http.HttpMethod.*;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    final private UserDetailsService userDetailsService;
    final private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Map<String, RoleSet> accessMap = new HashMap<>();
        accessMap.put("/course-sections/*/register", S);
        accessMap.put("/course-sections/*/students", IA);
        accessMap.put("/course-sections/*/*/setGrade", I);
        accessMap.put("/students/*/grades", S);
        accessMap.put("/students/total-grades", S);
        accessMap.put("/admin/active-user", A);

        http.csrf().disable();
        setAuthorities(http, accessMap);
        setAuthorities(http, "/students/**", A, SIA, A, A);
        setAuthorities(http, "/instructors/**", A, SIA, A, A);
        setAuthorities(http, "/terms/**", A, SIA, A, A);
        setAuthorities(http, "/courses/**", A, SIA, A, A);
        setAuthorities(http, "/course-sections/**", I, SIA, IA, IA);
        http.authorizeRequests().anyRequest().authenticated();
        http.formLogin();
    }

    static HttpMethod[] CRUDMethods = {POST, GET, PUT, DELETE};

    void setAuthorities(HttpSecurity http, String endPoint, RoleSet C, RoleSet R, RoleSet U, RoleSet D) throws Exception {
        RoleSet[] roleSets = {C, R, U, D};
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry lastConfig = http.authorizeRequests();
        for (int i = 0; i < 4; i++) {
            HttpMethod method = CRUDMethods[i];
            String[] strRoles = roleSets[i].roles;
            lastConfig = lastConfig.antMatchers(method, endPoint).hasAnyRole(strRoles);
        }
    }

    void setAuthorities(HttpSecurity http, Map<String, RoleSet> accessMap) throws Exception {
        AtomicReference<ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry> lastConfig = new AtomicReference<>(http.authorizeRequests());

        accessMap.forEach((endpoint, roleSet) -> {
                lastConfig.set(lastConfig.get().antMatchers(endpoint).hasAnyRole(roleSet.roles));
            }
        );
    }

    enum RoleSet{
        SA(STUDENT, ADMIN), IA(INSTRUCTOR, ADMIN), SIA(STUDENT, INSTRUCTOR, ADMIN), I(INSTRUCTOR), A(ADMIN), S(STUDENT);

        final String[] roles;

        RoleSet(Role ... roleEnums) {
            roles = new String[roleEnums.length];

            for (int i = 0; i < roleEnums.length; i++) {
                this.roles[i] = roleEnums[i].getStr();
            }
        }
    }
}
