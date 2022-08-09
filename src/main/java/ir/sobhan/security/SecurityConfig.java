package ir.sobhan.security;

import ir.sobhan.business.SecurityService.SecurityUtils;
import lombok.RequiredArgsConstructor;
import static ir.sobhan.security.RoleSet.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    final private UserDetailsService userDetailsService;
    final private PasswordEncoder passwordEncoder;
    final private SecurityUtils utils;

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
        http.authorizeRequests().antMatchers("/signin/**").permitAll();
        utils.setAuthorities(http, accessMap);
        utils.setAuthorities(http, "/students/**", A, SIA, A, A);
        utils.setAuthorities(http, "/instructors/**", A, SIA, A, A);
        utils.setAuthorities(http, "/terms/**", A, SIA, A, A);
        utils.setAuthorities(http, "/courses/**", A, SIA, A, A);
        utils.setAuthorities(http, "/course-sections/**", I, SIA, IA, IA);
        http.authorizeRequests().anyRequest().authenticated();
        http.formLogin();
    }
}
