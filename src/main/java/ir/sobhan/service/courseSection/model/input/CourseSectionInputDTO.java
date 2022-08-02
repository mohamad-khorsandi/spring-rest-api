package ir.sobhan.service.courseSection.model.input;

import ir.sobhan.service.AbstractService.model.input.InputDTO;
import ir.sobhan.service.courseSection.model.entity.CourseSection;
import lombok.*;

@Setter
public class CourseSectionInputDTO extends InputDTO<CourseSection> {
    public CourseSectionInputDTO() {
        super(CourseSection.class);
    }

}
