package ir.sobhan.service.user.model.input;

import ir.sobhan.service.AbstractService.model.input.InputDTO;
import ir.sobhan.service.user.model.entity.User;
import lombok.Setter;

@Setter
public class InstructorInputDTO extends InputDTO<User> {
    public InstructorINFInputDTO instructorInf;
    public String username;
    public String password;
    public String name;
    public String phone;
    public String nationalId;

    public InstructorInputDTO() {
        super(User.class);
    }
}
