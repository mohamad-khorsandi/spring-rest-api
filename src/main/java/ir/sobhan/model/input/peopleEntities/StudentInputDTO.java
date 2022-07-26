package ir.sobhan.model.input.peopleEntities;

import ir.sobhan.model.entity.peopleEntities.User;
import ir.sobhan.model.input.InputDTO;
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
