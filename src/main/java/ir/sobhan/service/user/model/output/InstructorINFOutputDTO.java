package ir.sobhan.service.user.model.output;

import ir.sobhan.service.AbstractService.model.output.OutPutDTO;
import ir.sobhan.service.user.model.entity.InstructorInf;

public class InstructorINFOutputDTO extends OutPutDTO<InstructorInf> {
    public Long id;
    public InstructorInf.Rank rank;

    public InstructorINFOutputDTO(InstructorInf realObj) {
        super(realObj);
    }
}
