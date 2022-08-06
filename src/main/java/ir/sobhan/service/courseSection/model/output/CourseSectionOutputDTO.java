package ir.sobhan.service.courseSection.model.output;

import ir.sobhan.service.AbstractService.model.output.OutPutDTO;
import ir.sobhan.service.course.model.entity.Course;
import ir.sobhan.service.courseSection.model.entity.CourseSection;
import ir.sobhan.service.term.model.output.TermOutputDTO;
import lombok.Getter;

@Getter
public class CourseSectionOutputDTO extends OutPutDTO<CourseSection> {
    public CourseSectionOutputDTO(CourseSection realObj) throws Exception {
        super(realObj);

        if (realObj.getRegistrationList() != null)
            studentCount = realObj.getRegistrationList().size();
    }

    public Long id;
    private Integer studentCount;
    public TermOutputDTO term;
    public Course course;
    public LightInstructorOutputDTO instructor;
}