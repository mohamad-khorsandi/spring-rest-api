package ir.sobhan.business.SecurityService;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Service;
import java.util.Map;
import static org.springframework.http.HttpMethod.*;

@Service
public class SecurityUtils {

    public void setAuthorities(HttpSecurity http, String endPoint, RoleSet C, RoleSet R, RoleSet U, RoleSet D) throws Exception {
        http.authorizeRequests()
                .antMatchers(POST, endPoint).hasAnyRole(C.getRoles())
                .antMatchers(GET, endPoint).hasAnyRole(R.getRoles())
                .antMatchers(PUT, endPoint).hasAnyRole(U.getRoles())
                .antMatchers(DELETE, endPoint).hasAnyRole(D.getRoles());
    }

    public void setAuthorities(HttpSecurity http, Map<String, RoleSet> accessMap) throws Exception {
        var requests = http.authorizeRequests();

        accessMap.forEach((endpoint, roleSet) -> requests.antMatchers(endpoint).hasAnyRole(roleSet.getRoles()));
    }
}
