package ir.sobhan.security;

import lombok.Getter;

@Getter
public enum Role {
    STUDENT, ADMIN, INSTRUCTOR;

    Role() {
        ROLE_str = "ROLE_" + this;
    }

    private final String ROLE_str;

    String getStr(){
        return this.toString();
    }
}