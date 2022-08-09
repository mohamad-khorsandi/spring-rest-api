package ir.sobhan.service.user.dao;

import ir.sobhan.service.user.model.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<User, Long> {

    List<User> findAllByStudent(PageRequest pageRequest, boolean isStudent);

    Optional<User> findByIdAndStudent(Long id, boolean isStudent);

    Optional<User> findByUsernameAndStudent(String username, boolean isStudent);
}
