package ir.sobhan.service.user.model.output;

import ir.sobhan.service.AbstractService.model.output.OutPutDTO;
import ir.sobhan.service.user.model.entity.User;
import lombok.Getter;

@Getter
public class StudentOutputDTO extends OutPutDTO<User> {
    public Long id;
    public String username;
    public StudentINFOutputDTO studentInf;
    public String name;
    public String phone;
    public String nationalId;
    public Boolean active;
    public Boolean admin;

    public StudentOutputDTO(User realObj) {
        super(realObj);
    }
}
