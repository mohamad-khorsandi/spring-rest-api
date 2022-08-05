package ir.sobhan.service.AbstractService;

import ir.sobhan.business.exception.NotFoundException;
import ir.sobhan.service.course.dao.CourseRepository;
import ir.sobhan.service.course.model.entity.Course;
import ir.sobhan.service.courseSection.dao.CourseSectionRepository;
import ir.sobhan.service.courseSection.model.entity.CourseSection;
import ir.sobhan.service.term.dao.TermRepository;
import ir.sobhan.service.term.model.entity.Term;
import ir.sobhan.service.user.dao.UserRepository;
import ir.sobhan.service.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DBGetter {
    final private CourseRepository courseRepository;
    final private TermRepository termRepository;
    final private UserRepository userRepository;
    final private CourseSectionRepository courseSectionRepository;

    public Term termById(Long id) throws NotFoundException {
        return termRepository.findById(id).orElseThrow(() -> new NotFoundException("term", id.toString()));

    }

    public User getInstructorById(Long id) throws NotFoundException {
        return userRepository.findById(id).filter(User::isInstructor).orElseThrow(() -> new NotFoundException("instructor", id.toString()));
    }

    public Course courseById(Long id) throws NotFoundException {
        return courseRepository.findById(id).orElseThrow(() -> new NotFoundException("course", id.toString()));
    }

    public User instructorByUsername(String username) throws NotFoundException {
        return userRepository.findByUsername(username).filter(User::isInstructor).orElseThrow(() -> new NotFoundException("instructor", username));
    }

    public User studentByUsername(String username) throws NotFoundException {
        return userRepository.findByUsername(username).filter(User::isStudent).orElseThrow(() -> new NotFoundException("student", username));
    }

    public User studentById(Long id) throws NotFoundException {
        return userRepository.findById(id).filter(User::isStudent).orElseThrow(() -> new NotFoundException("student", id.toString()));
    }

    public User userByUsername(String username) throws NotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("user", username));
    }

    public CourseSection sectionById(Long id) throws NotFoundException {
        return courseSectionRepository.findById(id).orElseThrow(() -> new NotFoundException("section", id.toString()));
    }
}
