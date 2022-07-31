package ir.sobhan.model.output.peopleEntities;

import ir.sobhan.model.entity.peopleEntities.AppUser;
import ir.sobhan.model.output.OutPutDTO;

public class InstructorOutputDTO extends OutPutDTO<AppUser> {
    public InstructorOutputDTO(AppUser realObj) throws Exception {
        super(realObj);
    }
    public Long id;
    public String username;
    public InstructorINFOutputDTO instructorInf;
    public String name;
    public String phone;
    public String nationalId;
    public Boolean isActive;
    public Boolean isAdmin;
}
