package ir.sobhan.service.user.model.output;

import ir.sobhan.service.AbstractService.model.output.OutPutDTO;
import ir.sobhan.service.term.model.entity.Term;
import ir.sobhan.service.user.model.entity.User;
import lombok.Getter;

@Getter
public class TermOfStudentOutputDTO extends OutPutDTO<Term> {
    public TermOfStudentOutputDTO(Term realObj, double sum, double count) throws Exception {
        super(realObj);

        this.ave = sum / count;
    }

    public Long id;
    public String title;
    private Double ave;
}