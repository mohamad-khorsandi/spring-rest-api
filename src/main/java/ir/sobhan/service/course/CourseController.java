package ir.sobhan.service.course;

import ir.sobhan.business.DBService.DBService;
import ir.sobhan.service.AbstractService.LCRUD;
import ir.sobhan.service.course.model.entity.Course;
import ir.sobhan.service.course.model.input.CourseInputDTO;
import ir.sobhan.service.course.model.output.CourseOutputDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("courses")
public class CourseController extends LCRUD<Course, CourseInputDTO> {
    public CourseController(DBService<Course> dbService) {
        super(dbService, CourseOutputDTO.class);
    }
}