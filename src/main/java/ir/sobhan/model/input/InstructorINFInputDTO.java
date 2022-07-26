package ir.sobhan.model.input;

import ir.sobhan.model.entity.InstructorInf;
import lombok.Setter;

@Setter
public class InstructorINFInputDTO extends InputDTO<InstructorInf>{
    public InstructorINFInputDTO() {
        super(InstructorInf.class);
    }

    public InstructorInf.Rank rank;
}
