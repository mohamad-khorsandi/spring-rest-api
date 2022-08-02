package ir.sobhan.service.term.dao;

import ir.sobhan.service.term.model.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Long> {

}
