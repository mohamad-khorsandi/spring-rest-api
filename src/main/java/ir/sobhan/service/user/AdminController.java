package ir.sobhan.service.user;

import ir.sobhan.business.exception.EntityNotFoundException;
import ir.sobhan.service.user.dao.UserRepository;
import ir.sobhan.service.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserRepository userRepository;

    @PutMapping
    void setActive(@PathParam(value = "username") String username) throws EntityNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(username));
        user.setActive(true);
    }
}
