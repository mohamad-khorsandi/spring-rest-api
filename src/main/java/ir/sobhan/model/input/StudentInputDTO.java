package ir.sobhan.model.input;

import ir.sobhan.model.entity.User;
import lombok.Setter;

@Setter
public class StudentInputDTO extends InputDTO<User>{
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
