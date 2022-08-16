package ir.sobhan.business.SecurityService;

import lombok.Getter;

import java.util.Arrays;

import static ir.sobhan.business.SecurityService.Role.*;

@Getter
public enum RoleSet {
    SA(STUDENT, ADMIN), IA(INSTRUCTOR, ADMIN), SIA(STUDENT, INSTRUCTOR, ADMIN), I(INSTRUCTOR), A(ADMIN), S(STUDENT);

    private final String[] roles;

    RoleSet(Role... roleEnums) {
        roles = Arrays.stream(roleEnums).map(Role::getStr).toArray(String[]::new);
    }
}