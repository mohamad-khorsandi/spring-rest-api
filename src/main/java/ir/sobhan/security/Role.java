package ir.sobhan.security;

public enum Role {
    STUDENT, ADMIN, INSTRUCTOR;

    final private String str;

    Role(){
        this.str = "ROLE_" + this;
    }

    @Override
    public String toString() {
        return str;
    }
}
