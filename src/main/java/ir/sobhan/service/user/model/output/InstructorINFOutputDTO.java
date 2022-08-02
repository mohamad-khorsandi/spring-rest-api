package ir.sobhan.service.user.model.output;

import ir.sobhan.service.user.model.entity.InstructorInf;
import ir.sobhan.service.AbstractService.model.output.OutPutDTO;

public class InstructorINFOutputDTO extends OutPutDTO<InstructorInf> {
    public InstructorINFOutputDTO(InstructorInf realObj) throws Exception {
        super(realObj);
    }
    public Long id;
    public InstructorInf.Rank rank;
}
