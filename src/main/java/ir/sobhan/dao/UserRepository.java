package ir.sobhan.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import ir.sobhan.model.entity.peopleEntities.*;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
}
