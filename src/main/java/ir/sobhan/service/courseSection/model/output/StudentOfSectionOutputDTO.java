package ir.sobhan.service.courseSection.model.output;

import ir.sobhan.service.AbstractService.model.output.OutPutDTO;
import ir.sobhan.service.user.model.entity.User;
import ir.sobhan.service.user.model.output.StudentINFOutputDTO;
import lombok.Getter;

@Getter
public class StudentOfSectionOutputDTO extends OutPutDTO<User> {
    public StudentOfSectionOutputDTO(User realObj, Double score) {
        super(realObj);
        this.grade = score;
    }

    public Long id;
    public String name;
    public StudentINFOutputDTO studentInf;
    private final Double grade;
}
