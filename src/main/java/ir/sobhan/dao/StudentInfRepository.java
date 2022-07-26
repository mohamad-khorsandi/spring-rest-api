package ir.sobhan.dao;

import ir.sobhan.model.entity.StudentInf;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentInfRepository extends JpaRepository<StudentInf, Long> {
}
