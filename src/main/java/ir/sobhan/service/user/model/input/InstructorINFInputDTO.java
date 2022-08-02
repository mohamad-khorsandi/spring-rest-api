package ir.sobhan.service.user.model.input;

import ir.sobhan.service.user.model.entity.InstructorInf;
import ir.sobhan.service.AbstractService.model.input.InputDTO;
import lombok.Setter;

@Setter
public class InstructorINFInputDTO extends InputDTO<InstructorInf> {
    public InstructorINFInputDTO() {
        super(InstructorInf.class);
    }

    public InstructorInf.Rank rank;
}
