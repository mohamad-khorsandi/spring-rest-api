package ir.sobhan.model.output.peopleEntities;

import ir.sobhan.model.entity.peopleEntities.AppUser;
import ir.sobhan.model.output.OutPutDTO;
import lombok.Getter;

@Getter
public class StudentOutputDTO extends OutPutDTO<AppUser> {
    public StudentOutputDTO(AppUser realObj) throws Exception {
        super(realObj);
    }
    public Long id;
    public String username;
    public StudentINFOutputDTO studentInf;
    public String name;
    public String phone;
    public String nationalId;
    public Boolean isActive;
    public Boolean isAdmin;
}
