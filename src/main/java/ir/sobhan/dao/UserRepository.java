package ir.sobhan.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import ir.sobhan.model.entity.peopleEntities.*;
public interface UserRepository extends JpaRepository<User, Long> {
}
