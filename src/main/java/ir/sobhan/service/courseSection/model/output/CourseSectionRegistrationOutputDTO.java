package ir.sobhan.service.courseSection.model.output;
import ir.sobhan.service.AbstractService.model.output.OutPutDTO;
import ir.sobhan.service.courseSection.model.entity.CourseSection;
import ir.sobhan.service.courseSection.model.entity.CourseSectionRegistration;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class CourseSectionRegistrationOutputDTO extends OutPutDTO<CourseSectionRegistration> {
    public CourseSectionRegistrationOutputDTO(CourseSectionRegistration realObj) throws Exception {
        super(realObj);
        this.title = realObj.getSection().getCourse().getTitle();
    }
    public Double score;
    private final String title;
}
