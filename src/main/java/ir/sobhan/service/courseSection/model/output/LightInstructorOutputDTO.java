package ir.sobhan.service.courseSection.model.output;

import ir.sobhan.service.AbstractService.model.output.OutPutDTO;
import ir.sobhan.service.user.model.entity.User;

public class LightInstructorOutputDTO extends OutPutDTO<User> {
    public LightInstructorOutputDTO(User realObj) {
        super(realObj);
    }

    public Long id;
    public String username;
}
