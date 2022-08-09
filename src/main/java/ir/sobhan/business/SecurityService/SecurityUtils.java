package ir.sobhan.business.SecurityService;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.http.HttpMethod.*;

@Service
public class SecurityUtils {
    static HttpMethod[] CRUDMethods = {POST, GET, PUT, DELETE};

    public void setAuthorities(HttpSecurity http, String endPoint, RoleSet C, RoleSet R, RoleSet U, RoleSet D) throws Exception {
        RoleSet[] roleSets = {C, R, U, D};
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry lastConfig = http.authorizeRequests();
        for (int i = 0; i < 4; i++) {
            HttpMethod method = CRUDMethods[i];
            String[] strRoles = roleSets[i].getRoles();
            lastConfig = lastConfig.antMatchers(method, endPoint).hasAnyRole(strRoles);
        }
    }

    public void setAuthorities(HttpSecurity http, Map<String, RoleSet> accessMap) throws Exception {
        AtomicReference<ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry> lastConfig = new AtomicReference<>(http.authorizeRequests());

        accessMap.forEach((endpoint, roleSet) -> lastConfig.set(lastConfig.get().antMatchers(endpoint).hasAnyRole(roleSet.getRoles()))
        );
    }


}
