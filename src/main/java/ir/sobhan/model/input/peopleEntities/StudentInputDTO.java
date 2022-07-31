package ir.sobhan.model.input.peopleEntities;

import ir.sobhan.model.entity.peopleEntities.AppUser;
import ir.sobhan.model.input.InputDTO;
import lombok.Setter;

@Setter
public class StudentInputDTO extends InputDTO<AppUser> {
    public StudentInputDTO() {
        super(AppUser.class);
    }
    public StudentINFInputDTO studentInf;
    public String username;
    public String password;
    public String name;
    public String phone;
    public String nationalId;

}
