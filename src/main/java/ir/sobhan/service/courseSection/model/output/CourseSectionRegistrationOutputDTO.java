package ir.sobhan.service.courseSection.model.output;
import ir.sobhan.service.AbstractService.model.output.OutPutDTO;
import ir.sobhan.service.courseSection.model.entity.CourseSectionRegistration;
import lombok.Getter;

@Getter
public class CourseSectionRegistrationOutputDTO extends OutPutDTO<CourseSectionRegistration> {
    public CourseSectionRegistrationOutputDTO(CourseSectionRegistration realObj) throws Exception {
        super(realObj);
    }

}
