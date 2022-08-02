package ir.sobhan.service.user.model.input;

import ir.sobhan.service.user.model.entity.User;
import ir.sobhan.service.AbstractService.model.input.InputDTO;
import lombok.Setter;

@Setter
public class InstructorInputDTO extends InputDTO<User> {
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
