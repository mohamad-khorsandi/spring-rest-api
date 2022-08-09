package ir.sobhan.service.user;

import ir.sobhan.business.DBService.UserDBService;
import ir.sobhan.business.exception.BadInputException;
import ir.sobhan.business.exception.NotFoundException;
import ir.sobhan.service.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/active-user")
@RequiredArgsConstructor
public class AdminController {
    UserDBService db;

    @PutMapping
    ResponseEntity<?> setActive(@RequestParam(value = "username") String username) throws NotFoundException, BadInputException {
        User user = db.getByUsername(username);
        user.setActive(true);
        db.save(user);
        return ResponseEntity.ok().build();
    }
}
