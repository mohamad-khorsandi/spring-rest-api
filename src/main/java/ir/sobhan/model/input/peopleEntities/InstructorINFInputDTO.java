package ir.sobhan.model.input.peopleEntities;

import ir.sobhan.model.entity.peopleEntities.InstructorInf;
import ir.sobhan.model.input.InputDTO;
import lombok.Setter;

@Setter
public class InstructorINFInputDTO extends InputDTO<InstructorInf> {
    public InstructorINFInputDTO() {
        super(InstructorInf.class);
    }

    public InstructorInf.Rank rank;
}
