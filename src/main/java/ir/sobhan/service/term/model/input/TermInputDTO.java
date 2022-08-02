package ir.sobhan.service.term.model.input;

import ir.sobhan.service.AbstractService.model.input.InputDTO;
import ir.sobhan.service.term.model.entity.Term;
import lombok.Setter;

@Setter
public class TermInputDTO extends InputDTO<Term> {
    public TermInputDTO() {
        super(Term.class);
    }

    public String title;
    public Boolean open;
}
