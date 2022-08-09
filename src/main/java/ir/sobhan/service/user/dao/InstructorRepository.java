package ir.sobhan.service.user.dao;

import ir.sobhan.service.user.model.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InstructorRepository extends JpaRepository<User, Long> {

    List<User> findAllByInstructor(PageRequest pageRequest, boolean isInstructor);

    Optional<User> findByIdAndInstructor(Long id, boolean isInstructor);

    Optional<User> findByUsernameAndInstructor(String username, boolean isInstructor);
}
