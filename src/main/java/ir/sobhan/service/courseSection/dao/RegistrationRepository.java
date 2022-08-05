package ir.sobhan.service.courseSection.dao;

import ir.sobhan.service.courseSection.model.entity.CourseSectionRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<CourseSectionRegistration, Long> {

}
