package ir.sobhan.model.output.peopleEntities;

import ir.sobhan.model.entity.peopleEntities.InstructorInf;
import ir.sobhan.model.output.OutPutDTO;

public class InstructorINFOutputDTO extends OutPutDTO<InstructorInf> {
    public InstructorINFOutputDTO(InstructorInf realObj) throws Exception {
        super(realObj);
    }
    public Long id;
    public InstructorInf.Rank rank;
}
