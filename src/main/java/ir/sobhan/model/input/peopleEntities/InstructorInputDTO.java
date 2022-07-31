package ir.sobhan.model.input.peopleEntities;

import ir.sobhan.model.entity.peopleEntities.AppUser;
import ir.sobhan.model.input.InputDTO;
import lombok.Setter;

@Setter
public class InstructorInputDTO extends InputDTO<AppUser> {
    public InstructorInputDTO() {
        super(AppUser.class);
    }

    public InstructorINFInputDTO instructorInf;
    public String username;
    public String password;
    public String name;
    public String phone;
    public String nationalId;
}
