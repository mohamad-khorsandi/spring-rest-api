package ir.sobhan.business.DBService;

import ir.sobhan.business.exception.NotFoundException;
import ir.sobhan.service.user.dao.InstructorRepository;
import ir.sobhan.service.user.model.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorDBService extends DBService<User> {
    public InstructorDBService(InstructorRepository instructorRepository) {
        super(instructorRepository);
        this.instructorRepository = instructorRepository;
    }

    final private InstructorRepository instructorRepository;

    public List<User> getList(int pageNumber, int pageSize) {
        return instructorRepository.findAllByInstructor(PageRequest.of(pageNumber, pageSize), true);
    }

    public User getByUsername(String username) throws NotFoundException {
        return instructorRepository.findByUsernameAndInstructor(username, true).orElseThrow(() -> new NotFoundException("instructor", username));
    }

    @Override
    public User getById(Long id) throws NotFoundException {
        return instructorRepository.findByIdAndInstructor(id, true).orElseThrow(() -> new NotFoundException("instructor", id));
    }
}
