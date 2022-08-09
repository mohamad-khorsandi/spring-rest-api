package ir.sobhan.service.user.model.input;

import ir.sobhan.service.AbstractService.model.input.InputDTO;
import ir.sobhan.service.user.model.entity.User;
import lombok.Setter;

@Setter
public class StudentInputDTO extends InputDTO<User> {
    public StudentInputDTO() {
        super(User.class);
    }

    public StudentINFInputDTO studentInf;
    public String username;
    public String password;
    public String name;
    public String phone;
    public String nationalId;

}
