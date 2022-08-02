package ir.sobhan.service.course.dao;

import ir.sobhan.service.course.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
