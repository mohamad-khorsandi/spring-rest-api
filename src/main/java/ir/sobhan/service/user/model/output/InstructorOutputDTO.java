package ir.sobhan.service.user.model.output;

import ir.sobhan.service.AbstractService.model.output.OutPutDTO;
import ir.sobhan.service.user.model.entity.User;

public class InstructorOutputDTO extends OutPutDTO<User> {
    public InstructorOutputDTO(User realObj) {
        super(realObj);
    }

    public Long id;
    public String username;
    public InstructorINFOutputDTO instructorInf;
    public String name;
    public String phone;
    public String nationalId;
    public Boolean active;
    public Boolean admin;
}
