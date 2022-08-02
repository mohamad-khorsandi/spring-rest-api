package ir.sobhan.service.course.model.input;

import ir.sobhan.service.AbstractService.model.input.InputDTO;
import ir.sobhan.service.course.model.entity.Course;
import lombok.Setter;

@Setter
public class CourseInputDTO extends InputDTO<Course> {
    public CourseInputDTO() {
        super(Course.class);
    }

    public String title;
    public Integer units;
}
