package ir.sobhan.service.courseSection.dao;

import ir.sobhan.service.courseSection.model.entity.CourseSectionRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<CourseSectionRegistration, Long> {
    Optional<CourseSectionRegistration> findBySection_IdAndStudent_Id(Long sectionId, Long stuId);

    List<CourseSectionRegistration> findAllBySection_Term_IdAndStudent_Id(Long sectionId, Long stuId);
}
