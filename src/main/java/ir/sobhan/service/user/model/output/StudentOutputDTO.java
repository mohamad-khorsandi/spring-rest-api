package ir.sobhan.service.user.model.output;

import ir.sobhan.service.user.model.entity.User;
import ir.sobhan.service.AbstractService.model.output.OutPutDTO;
import lombok.Getter;

@Getter
public class StudentOutputDTO extends OutPutDTO<User> {
    public StudentOutputDTO(User realObj) throws Exception {
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
