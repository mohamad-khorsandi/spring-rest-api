package ir.sobhan.business.DBService;

import ir.sobhan.business.exception.NotFoundException;
import ir.sobhan.service.user.dao.UserRepository;
import ir.sobhan.service.user.model.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserDBService extends DBService<User> {
    public UserDBService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    final private UserRepository userRepository;

    public User getByUsername(String username) throws NotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("user", username));
    }
}
