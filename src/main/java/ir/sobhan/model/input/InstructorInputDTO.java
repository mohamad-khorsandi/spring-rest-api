package ir.sobhan.model.input;

import ir.sobhan.model.entity.User;
import lombok.Setter;

@Setter
public class InstructorInputDTO extends InputDTO<User>{
    public InstructorInputDTO() {
        super(User.class);
    }

    public InstructorINFInputDTO instructorInf;
    public String username;
    public String password;
    public String name;
    public String phone;
    public String nationalId;
}
