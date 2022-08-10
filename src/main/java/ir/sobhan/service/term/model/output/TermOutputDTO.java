package ir.sobhan.service.term.model.output;

import ir.sobhan.service.AbstractService.model.output.OutPutDTO;
import ir.sobhan.service.term.model.entity.Term;
import lombok.Getter;

@Getter
public class TermOutputDTO extends OutPutDTO<Term> {
    public TermOutputDTO(Term realObj) {
        super(realObj);
    }

    public Long id;
    public String title;
    public Boolean open;
}
