package ir.sobhan.business.DBService;

import ir.sobhan.business.exception.NotFoundException;
import ir.sobhan.service.courseSection.dao.RegistrationRepository;
import ir.sobhan.service.courseSection.model.entity.CourseSectionRegistration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationDBService extends DBService<CourseSectionRegistration> {
    public RegistrationDBService(RegistrationRepository repository) {
        super(repository);
        this.repository = repository;
    }

    RegistrationRepository repository;

    public CourseSectionRegistration findBySection_IdAndStudent_Id(Long sectionId, Long stuId) throws NotFoundException {
        return repository.findBySection_IdAndStudent_Id(sectionId, stuId).orElseThrow(() -> new NotFoundException("Registration", "student id: " + stuId + " section id: " + sectionId));
    }

    public List<CourseSectionRegistration> findAllBySection_Term_IdAndStudent_Id(Long termId, Long stuId) {
        return repository.findAllBySection_Term_IdAndStudent_Id(termId, stuId);
    }
}
