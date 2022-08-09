package ir.sobhan.business.DBService;

import ir.sobhan.business.exception.NotFoundException;
import ir.sobhan.service.user.dao.StudentRepository;
import ir.sobhan.service.user.model.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentDBService extends DBService<User> {
    public StudentDBService(StudentRepository studentRepository) {
        super(studentRepository);
        this.studentRepository = studentRepository;
    }

    final private StudentRepository studentRepository;

    @Override
    public List<User> getList(int pageNumber, int pageSize) {
        return studentRepository.findAllByStudent(PageRequest.of(pageNumber, pageSize), true);
    }

    public User getByUsername(String username) throws NotFoundException {
        return studentRepository.findByUsernameAndStudent(username, true).orElseThrow(() -> new NotFoundException("student", username));
    }

    @Override
    public User getById(Long id) throws NotFoundException {
        return studentRepository.findByIdAndStudent(id, true).orElseThrow(() -> new NotFoundException("student", id));
    }
}
