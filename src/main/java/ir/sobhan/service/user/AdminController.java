package ir.sobhan.service.user;

import ir.sobhan.business.exception.NotFoundException;
import ir.sobhan.service.AbstractService.DBGetter;
import ir.sobhan.service.user.dao.UserRepository;
import ir.sobhan.service.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("admin/activeUsers")
@RequiredArgsConstructor
public class AdminController {
    private final DBGetter get;
    private final UserRepository userRepository;
    @PutMapping
    ResponseEntity<?> setActive(@RequestParam(value = "username") String username) throws NotFoundException {
        User user = get.userByUsername(username);
        user.setActive(true);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
