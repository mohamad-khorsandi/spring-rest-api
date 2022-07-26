package ir.sobhan.model.output;

import ir.sobhan.model.entity.InstructorInf;

public class InstructorINFOutputDTO extends OutPutDTO<InstructorInf>{
    public InstructorINFOutputDTO(InstructorInf realObj) throws Exception {
        super(realObj);
    }
    public Long id;
    public InstructorInf.Rank rank;
}
