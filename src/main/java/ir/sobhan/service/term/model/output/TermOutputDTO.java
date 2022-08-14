package ir.sobhan.service.term.model.output;

import ir.sobhan.service.AbstractService.model.output.OutPutDTO;
import ir.sobhan.service.term.model.entity.Term;
import lombok.Getter;

@Getter
public class TermOutputDTO extends OutPutDTO<Term> {
    public Long id;
    public String title;
    public Boolean open;

    public TermOutputDTO(Term realObj) {
        super(realObj);
    }
}
