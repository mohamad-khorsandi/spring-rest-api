package ir.sobhan.service.courseSection.model.output;

import ir.sobhan.service.AbstractService.model.output.OutPutDTO;
import ir.sobhan.service.courseSection.model.entity.CourseSectionRegistration;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class CourseSectionRegistrationOutputDTO extends OutPutDTO<CourseSectionRegistration> {
    public Double score;
    private final String title;

    public CourseSectionRegistrationOutputDTO(CourseSectionRegistration realObj) {
        super(realObj);
        this.title = realObj.getSection().getCourse().getTitle();
    }
}
