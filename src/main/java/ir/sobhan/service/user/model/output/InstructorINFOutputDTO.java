package ir.sobhan.service.user.model.output;

import ir.sobhan.service.AbstractService.model.output.OutPutDTO;
import ir.sobhan.service.user.model.entity.InstructorInf;

public class InstructorINFOutputDTO extends OutPutDTO<InstructorInf> {
    public InstructorINFOutputDTO(InstructorInf realObj) {
        super(realObj);
    }

    public Long id;
    public InstructorInf.Rank rank;
}
