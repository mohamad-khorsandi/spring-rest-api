package ir.sobhan.service.courseSection.dao;

import ir.sobhan.service.courseSection.model.entity.CourseSection;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseSectionRepository extends JpaRepository<CourseSection, Long> {
    List<CourseSection> findAllByTerm_Id(PageRequest page, Long termId);
}
