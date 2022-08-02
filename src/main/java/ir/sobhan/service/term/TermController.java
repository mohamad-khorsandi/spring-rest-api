package ir.sobhan.service.term;

import ir.sobhan.service.AbstractService.LCRUD;
import ir.sobhan.service.term.dao.TermRepository;
import ir.sobhan.service.term.model.entity.Term;
import ir.sobhan.service.term.model.input.TermInputDTO;
import ir.sobhan.service.term.model.output.TermOutputDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("terms")
public class TermController extends LCRUD<Term, TermInputDTO> {
    public TermController(TermRepository repository) {
        super(repository, TermOutputDTO.class);
    }
}