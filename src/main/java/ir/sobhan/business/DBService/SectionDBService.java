package ir.sobhan.business.DBService;

import ir.sobhan.service.courseSection.dao.CourseSectionRepository;
import ir.sobhan.service.courseSection.model.entity.CourseSection;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionDBService extends DBService<CourseSection> {
    public SectionDBService(CourseSectionRepository repository) {
        super(repository);
        this.repository = repository;
    }

    CourseSectionRepository repository;

    public List<CourseSection> findAllByTerm_id(int pageNumber, int pageSize, Long termId) {
        return repository.findAllByTerm_Id(PageRequest.of(pageNumber, pageSize), termId);
    }
}
